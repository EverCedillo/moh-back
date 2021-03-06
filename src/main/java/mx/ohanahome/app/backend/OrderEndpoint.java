package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.product.Customer;
import mx.ohanahome.app.backend.entity.product.CustomerOrder;
import mx.ohanahome.app.backend.entity.product.Order;
import mx.ohanahome.app.backend.entity.product.OrderProduct;
import mx.ohanahome.app.backend.entity.product.OrderStatus;
import mx.ohanahome.app.backend.entity.product.Product;
import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.RegistrationRecord;
import mx.ohanahome.app.backend.entity.user.User;
import mx.ohanahome.app.backend.model.NotificationPackage;
import mx.ohanahome.app.backend.model.OrderPackage;
import mx.ohanahome.app.backend.util.Constants;
import mx.ohanahome.app.backend.util.EMFProduct;
import mx.ohanahome.app.backend.util.EMFUser;
import mx.ohanahome.app.backend.util.MOHException;

import static mx.ohanahome.app.backend.util.Constants.COrderProduct.*;
import static mx.ohanahome.app.backend.util.Constants.CProduct.*;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "orderApi",
        version = "v1",
        resource = "order",
        namespace = @ApiNamespace(
                ownerDomain = "product.entity.backend.app.ohanahome.mx",
                ownerName = "product.entity.backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class OrderEndpoint {

    private static final Logger logger = Logger.getLogger(OrderEndpoint.class.getName());

    /**
     * This method gets the <code>Order</code> object associated with the specified <code>id</code>.
     *
     * @param orderPackage The id of the object to be returned.
     * @return The <code>Order</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getOrder", httpMethod = ApiMethod.HttpMethod.POST)
    public Order getOrder(OrderPackage orderPackage) throws MOHException{

        EntityManager productManager = EMFProduct.get().createEntityManager();
        EntityManager userManager = EMFUser.get().createEntityManager();
        Order order = orderPackage.getOrder();
        Identify identify = orderPackage.getIdentify();
        Status status;
        try {
            status = verifyIdentity(identify, userManager);
            if(status!=Status.USER_ALREADY_EXISTS) throw new MOHException(status.getMessage(),status.getCode());
            identify = (Identify) status.getResponse();

            order = productManager.find(Order.class,order.getId_order());
            for(CustomerOrder co: order.getCustomerOrders())
                if(co.getId_customer()==identify.getUser().getId_user())
                    return order;

            throw new MOHException("The user is not in the order", MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);

        }finally {
            productManager.close();
        }


    }

    /**
     * This inserts a new <code>Order</code> object.
     *
     * @param orderPackage The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertOrder", httpMethod = ApiMethod.HttpMethod.PUT)
    public Order insertOrder(OrderPackage orderPackage) throws MOHException{

        Status status;
        EntityManager productManager = EMFProduct.get().createEntityManager();
        EntityManager userManager = EMFUser.get().createEntityManager();

        Order order= new Order("");
        Identify identify = orderPackage.getIdentify();
        User user;

        EntityTransaction transaction = productManager.getTransaction();
        try {
            status = verifyIdentity(identify, userManager);
            if (status != Status.USER_ALREADY_EXISTS)
                throw new MOHException(status.getMessage(), status.getCode());
            identify = (Identify) status.getResponse();
            user = identify.getUser();


            List<Customer> customerList = orderPackage.getCustomerList();
            Set<User> users = new HashSet<>();
            List<CustomerOrder> customerOrderList = new ArrayList<>();

            order = orderPackage.getOrder();
            order.setCreation_date(new Date());

            //order.addOrderProduct(orderProductList);

            OrderStatus orderStatus = new OrderStatus(Constants.COrderStatus.PENDING_ORDER);
            orderStatus.setDate_order_status(new Date());
            orderStatus.setOrder_status(order);
            order.addOrderStatus(orderStatus);


            CustomerOrder customer = new CustomerOrder(Constants.CCustomerOrder.ACCEPTED_ORDER, Constants.CCustomerOrder.ADMIN_ROL);
            customer.setId_customer(orderPackage.getOwner().getId_customer());
            customer.setStatus_date(new Date());
            customerOrderList.add(customer);

            for (Customer c : customerList) {
                CustomerOrder customerOrder = new CustomerOrder(Constants.CCustomerOrder.PENDING_ORDER, Constants.CCustomerOrder.NORMAL_ROL);
                customerOrder.setStatus_date(new Date());
                customerOrder.setId_customer(c.getId_customer());
                customerOrderList.add(customerOrder);


                User user1 = userManager.find(User.class, c.getId_customer());
                logger.log(Level.INFO, "Id " + c.getId_customer() + "was " + (user1 == null ? "null" : "success"));
                if (user1 != null)
                    users.add(user1);
            }

            order.addCustomerOrder(customerOrderList);

            transaction.begin();
            productManager.persist(order);
            productManager.flush();


            for (User u : users){
                if(u.getId_user()!=user.getId_user())
                    for (RegistrationRecord r : u.getRecords()) {
                        Formatter formatter = new Formatter();
                        NotificationPackage notificationPackage = new NotificationPackage();
                        HashMap<String, String> extras = new HashMap<>();
                        extras.put("extra", String.valueOf(order.getId_order()));
                        notificationPackage.setExtras(extras);
                        notificationPackage.setRegistrationRecord(r);
                        try {
                            String message = formatter.format(Constants.COrder.ORDER_INVITATION_MSG, user.getUser_name(),order.getOrder_name()).toString();
                            new MessagingEndpoint().sendMessage(message, notificationPackage, Constants.CMessaging.ORDER_INVITATION_TOPIC);
                        } catch (IOException e) {
                            e.printStackTrace();
                            logger.log(Level.WARNING, e.getMessage(), e.getCause());
                        }

                    }
            }

            transaction.commit();

        }
        finally {
            if(transaction.isActive())
                transaction.rollback();
            productManager.close();
            userManager.close();
        }

        logger.info("Calling insertOrder method");
        return order;
    }


    @ApiMethod(name = "pushProduct" , path = "order/products", httpMethod = ApiMethod.HttpMethod.PUT)
    public OrderProduct insertProduct(OrderPackage orderPackage) throws MOHException{
        Status status;
        EntityManager productManager = EMFProduct.get().createEntityManager();
        EntityManager userManager = EMFUser.get().createEntityManager();

        Order order= orderPackage.getOrder();
        Identify identify = orderPackage.getIdentify();
        User user;
        OrderProduct orderProduct;

        // TODO: 26/07/16 am I in the Order? the order status?

        EntityTransaction transaction = productManager.getTransaction();
        try {
            status = verifyIdentity(identify, userManager);
            if (status != Status.USER_ALREADY_EXISTS)
                throw new MOHException(status.getMessage(), status.getCode());
            identify = (Identify) status.getResponse();
            user = identify.getUser();


            orderProduct = orderPackage.getOrderProduct();
            transaction.begin();
            logger.log(Level.INFO, "order prod:" + orderProduct.getId_order_product());
            if(orderProduct.getId_order_product()!=0)
                orderProduct = productManager.find(OrderProduct.class, orderProduct.getId_order_product());


            if(orderProduct==null)
                orderProduct = orderPackage.getOrderProduct();

            orderProduct.setId_customer(orderPackage.getOrderProduct().getId_customer());
            orderProduct.setPrice(orderPackage.getOrderProduct().getPrice());
            orderProduct.setQuantity(orderPackage.getOrderProduct().getQuantity());

            Product product = productManager.find(Product.class, orderProduct.getProduct().getId_product());
            orderProduct.setProduct(product);
            productManager.persist(orderProduct);

            productManager.flush();
            TypedQuery<CustomerOrder> query = productManager.createNamedQuery("CustomerOrder.getCustomerOrderByOrder", CustomerOrder.class);
            query.setParameter(1, order.getId_order());
            List<CustomerOrder> customerOrderList = query.getResultList();
            for (CustomerOrder co: customerOrderList){
                if(co.getOrder_status().equals(Constants.CCustomerOrder.ACCEPTED_ORDER)&&co.getId_customer()!=user.getId_user()){
                    TypedQuery<RegistrationRecord> q = userManager.createNamedQuery("RegistrationRecord.getRecordsByUser", RegistrationRecord.class);
                    q.setParameter(1,co.getId_customer());
                    List<RegistrationRecord> rr  = q.getResultList();


                    HashMap<String,String> extras = new HashMap<>();

                    extras.put(ID_ORDER_PRODUCT,String.valueOf(orderProduct.getId_order_product()));
                    extras.put(ID_ORDER,String.valueOf(order.getId_order()));
                    extras.put(ID_CUSTOMER,String.valueOf(orderProduct.getId_customer()));
                    extras.put(PRICE,String . valueOf(product.getProduct_prices().first().getPrice()));
                    extras.put(QUANTITY, String . valueOf(orderProduct.getQuantity()));
                    extras.put(PRODUCT_TYPE,orderProduct.getProduct_type());

                    extras.put(Constants.COrderProduct.ID_PRODUCT,String .valueOf(product.getId_product()));
                    extras.put(PRODUCT_NAME,product.getProduct_name());
                    extras.put(ORDER_QUANTITY,String .valueOf(product.getOrder_quantity()));
                    extras.put(CATEGORY,String .valueOf(product.getCategory()));
                    extras.put(SUB_CATEGORY,String .valueOf(product.getSub_category()));
                    extras.put(DEPTO,String .valueOf(product.getDepto()));
                    extras.put(AMOUNT, String .valueOf(product.getAmount()));
                    extras.put(UNIT,String .valueOf(product.getUnit()));
                    extras.put(BRAND, String .valueOf(product.getBrand()));
                    extras.put(PRODUCT_NO, String .valueOf(product.getProduct_no()));
                    extras.put(IMAGE,product.getImage());


                    for(RegistrationRecord r:rr){
                        NotificationPackage notificationPackage = new NotificationPackage();
                        notificationPackage.setRegistrationRecord(r);
                        notificationPackage.setExtras(extras);

                        try {
                            new MessagingEndpoint().sendMessage(
                                    String.valueOf(orderProduct.getId_order_product()),
                                    notificationPackage,
                                    Constants.CMessaging.ORDER_PUSH_PRODUCT_TOPIC);
                        }catch (IOException e){
                            logger.log(Level.WARNING,e.getMessage(),e.getCause());
                        }
                    }
                }
            }

            logger.log(Level.INFO, "order prod final:" + orderProduct.getId_order_product());

            transaction.commit();
            return orderProduct;

        }finally {
            if(transaction.isActive())
                transaction.rollback();
            productManager.close();
            userManager.close();
        }
    }

    @ApiMethod(path = "order/products", httpMethod = ApiMethod.HttpMethod.POST)
    public CollectionResponse<OrderProduct> pullProduct(OrderPackage orderPackage) throws MOHException{

        Status status;
        EntityManager productManager = EMFProduct.get().createEntityManager();
        EntityManager userManager = EMFUser.get().createEntityManager();

        Identify identify = orderPackage.getIdentify();

        Order order = orderPackage.getOrder();
        // TODO: 26/07/16 am I in the Order?


        try {
            status = verifyIdentity(identify, userManager);
            if (status != Status.USER_ALREADY_EXISTS)
                throw new MOHException(status.getMessage(), status.getCode());
            identify = (Identify) status.getResponse();
            User user = identify.getUser();

            CustomerOrder customerOrder = orderPackage.getGuest();


            TypedQuery<OrderProduct> query = productManager.createNamedQuery("OrderProduct.getProductsByCustomerAndOrder", OrderProduct.class);
            query.setParameter(ID_CUSTOMER,customerOrder.getId_customer());
            query.setParameter(ID_ORDER,order.getId_order());

            List<OrderProduct> orderProductList = query.getResultList();


            return CollectionResponse.<OrderProduct>builder().setItems(orderProductList).build();

        }finally {
            productManager.close();
            userManager.close();
        }

    }

    @ApiMethod(path = "order/customer", httpMethod = ApiMethod.HttpMethod.POST)
    public void resolveCustomerOrder(OrderPackage orderPackage) throws MOHException{
        Status status;
        EntityManager productManager = EMFProduct.get().createEntityManager();
        EntityManager userManager = EMFUser.get().createEntityManager();

        Order order= orderPackage.getOrder();
        Identify identify = orderPackage.getIdentify();
        User user;

        EntityTransaction transaction = productManager.getTransaction();
        try {
            status = verifyIdentity(identify, userManager);
            if (status != Status.USER_ALREADY_EXISTS)
                throw new MOHException(status.getMessage(), status.getCode());
            identify = (Identify) status.getResponse();
            user = identify.getUser();

            CustomerOrder customerOrder = orderPackage.getGuest();

            transaction.begin();

            TypedQuery<OrderStatus> query = productManager.createNamedQuery("OrderStatus.getStatusesByOrder",OrderStatus.class);
            query.setParameter(1,order.getId_order());
            List<OrderStatus> statuses = query.getResultList();
            if(statuses.isEmpty())
                logger.log(Level.WARNING,"Not found status");
            boolean flag=false;
            for(OrderStatus os : statuses)
                flag=os.getStatus_name().equals(Constants.COrderStatus.CANCELED_ORDER)
                        ||os.getStatus_name().equals(Constants.COrderStatus.SENT_ORDER)
                        ||os.getStatus_name().equals(Constants.COrderStatus.COMPLETE_ORDER);

            if(flag)
                throw new MOHException("Order has finished or has been canceled", MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);

            TypedQuery<CustomerOrder> query1 = productManager.createNamedQuery("CustomerOrder.getCOByOrderAndCustomer", CustomerOrder.class);
            query1.setParameter(1,order.getId_order());
            query1.setParameter(2,customerOrder.getId_customer());
            customerOrder = query1.getResultList().get(0);
            if(customerOrder==null) throw new  MOHException("The customer is not invited", MOHException.STATUS_AUTH_ERROR);

            customerOrder.setOrder_status(orderPackage.getGuest().getOrder_status());

            productManager.persist(customerOrder);

            productManager.flush();

            if(customerOrder.getOrder_status().equals(Constants.CCustomerOrder.ACCEPTED_ORDER)) {
                TypedQuery<CustomerOrder> query2 = productManager.createNamedQuery("CustomerOrder.getCustomerOrderByOrder", CustomerOrder.class);
                query2.setParameter(1, order.getId_order());
                List<CustomerOrder> customerOrderList = query2.getResultList();
                for (CustomerOrder co : customerOrderList) {
                    if (co.getOrder_status().equals(Constants.CCustomerOrder.ACCEPTED_ORDER) && co.getId_customer() != user.getId_user()) {
                        TypedQuery<RegistrationRecord> q = userManager.createNamedQuery("RegistrationRecord.getRecordsByUser", RegistrationRecord.class);
                        q.setParameter(1, co.getId_customer());
                        List<RegistrationRecord> rr = q.getResultList();
                        for (RegistrationRecord r : rr) {
                            NotificationPackage notificationPackage = new NotificationPackage();
                            notificationPackage.setRegistrationRecord(r);
                            HashMap<String,String> extras = new HashMap<>();
                            extras.put("extra",String.valueOf(order.getId_order()));
                            notificationPackage.setExtras(extras);
                            try {
                                new MessagingEndpoint().sendMessage(
                                        String.valueOf(customerOrder.getId_customer()),
                                        notificationPackage,
                                        Constants.CMessaging.ORDER_PUSH_CUSTOMER_TOPIC);
                            } catch (IOException e) {
                                logger.log(Level.WARNING, e.getMessage(), e.getCause());
                            }
                        }
                    }
                }
            }

            transaction.commit();

        }finally {
            if(transaction.isActive())
                transaction.rollback();
            productManager.close();
            userManager.close();
        }
    }

    @ApiMethod(path = "order/customer")
    public void deleteCustomer(OrderPackage orderPackage) throws  MOHException{
        EntityManager userManager = EMFUser.get().createEntityManager();
        EntityManager productManager = EMFProduct.get().createEntityManager();

        EntityTransaction transaction = productManager.getTransaction();

        Order order = orderPackage.getOrder();

        // TODO: 24/09/16 verify admin
        Status status;
        Identify identify;
        try {
            status = verifyIdentity(orderPackage.getIdentify(),userManager);
            if(status!=Status.USER_ALREADY_EXISTS) throw new MOHException(status.getMessage(),status.getCode());
            identify = (Identify)status.getResponse();
            User user = identify.getUser();
            transaction.begin();



            CustomerOrder customerOrder = orderPackage.getGuest();
            TypedQuery<CustomerOrder> query1 = productManager.createNamedQuery("CustomerOrder.getCOByOrderAndCustomer", CustomerOrder.class);
            query1.setParameter(1,order.getId_order());
            query1.setParameter(2, customerOrder.getId_customer());
            customerOrder = query1.getResultList().get(0);

            if(customerOrder==null) throw new MOHException("Object not found",MOHException.STATUS_OBJECT_NOT_FOUND);

            if(customerOrder.getId_customer()!=user.getId_user()) throw new MOHException("The user does not have permission to delete", MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);

            Query query = productManager.createNamedQuery("OrderProduct.removeOPByCustomerNOrder");
            query.setParameter(Constants.COrderProduct.ID_ORDER, order.getId_order());
            query.setParameter(Constants.COrderProduct.ID_CUSTOMER, customerOrder.getId_customer());
            query.executeUpdate();
            productManager.remove(customerOrder);

            transaction.commit();
        }catch (Exception e){
            logger.log(Level.WARNING,e.getMessage(),e.getCause());
        }
    }

    @ApiMethod(path = "order/orderStatus", httpMethod = ApiMethod.HttpMethod.POST)
    public void insertStatus(OrderPackage orderPackage) throws MOHException{
        EntityManager productManager = EMFProduct.get().createEntityManager();
        EntityManager userManager = EMFUser.get().createEntityManager();
        Status status;

        Identify identify = orderPackage.getIdentify();
        try {
            status = verifyIdentity(identify,userManager);
            if(status!=Status.USER_ALREADY_EXISTS) throw new MOHException(status.getMessage(),status.getCode());
            identify = (Identify)status.getResponse();
            User user = identify.getUser();
            OrderStatus orderStatus = orderPackage.getOrderStatus();
            Order order = orderPackage.getOrder();

            order = productManager.find(Order.class, order.getId_order());
            if(order==null) throw new MOHException("The order does not exist", MOHException.STATUS_OBJECT_NOT_FOUND);

            productManager.getTransaction().begin();
            order.addOrderStatus(orderStatus);
            orderStatus.setDate_order_status(new Date());
            productManager.persist(orderStatus);

            if(orderStatus.getStatus_name().equals(Constants.COrderStatus.COMPLETE_ORDER)||orderStatus.getStatus_name().equals(Constants.COrderStatus.SENT_ORDER)||orderStatus.getStatus_name().equals(Constants.COrderStatus.CANCELED_ORDER)){

                if(orderStatus.getStatus_name().equals(Constants.COrderStatus.COMPLETE_ORDER)){
                    if(orderPackage.getProductList()!=null)
                        for(OrderProduct op:orderPackage.getProductList()) {
                            Query query = productManager.createNamedQuery("OrderProduct.removeOrderProduct");
                            query.setParameter(Constants.COrderProduct.ID_ORDER_PRODUCT,op.getId_order_product());
                            query.executeUpdate();
                        }
                }

                List<OrderProduct> orderProductList = orderPackage.getProductList();
                for (CustomerOrder co : order.getCustomerOrders()) {
                    if (co.getOrder_status().equals(Constants.CCustomerOrder.ACCEPTED_ORDER) && co.getId_customer() != user.getId_user()) {
                        TypedQuery<RegistrationRecord> q = userManager.createNamedQuery("RegistrationRecord.getRecordsByUser", RegistrationRecord.class);
                        q.setParameter(1, co.getId_customer());
                        List<RegistrationRecord> rr = q.getResultList();
                        for (RegistrationRecord r : rr) {
                            NotificationPackage notificationPackage = new NotificationPackage();
                            notificationPackage.setRegistrationRecord(r);
                            HashMap<String,String> extras = new HashMap<>();
                            extras.put(Constants.COrderStatus.STATUS_NAME,orderStatus.getStatus_name());
                            extras.put(Constants.COrder.ID_ORDER, String.valueOf(order.getId_order()));
                            extras.put(
                                    Constants.COrderStatus.FETCH_ORDER_PRODUCT_FLAG,
                                    String.valueOf(co.getOrder_status().equals(
                                                    Constants.CCustomerOrder.ACCEPTED_ORDER) &&
                                                    orderProductList != null &&
                                                    !orderProductList.isEmpty()
                                    ));
                            extras.put(Constants.COrderStatus.OP_COUNT, String.valueOf(orderPackage.getProductList().size()));
                            if(orderProductList!=null)
                                for(int i =0; i<orderProductList.size();i++)
                                    extras.put(String .valueOf(i),String.valueOf(orderProductList.get(i).getId_order_product()));
                            notificationPackage.setExtras(extras);
                            try {
                                new MessagingEndpoint().sendMessage(
                                        Constants.CMessaging.DEFAULT_MESSAGE,
                                        notificationPackage,
                                        Constants.CMessaging.ORDER_PUSH_STATUS_TOPIC);
                            } catch (IOException e) {
                                logger.log(Level.WARNING, e.getMessage(), e.getCause());
                            }
                        }
                    }
                }

            }


            productManager.getTransaction().commit();

        }finally {
            if(productManager.getTransaction().isActive())
                productManager.getTransaction().rollback();
            if(userManager.getTransaction().isActive())
                userManager.getTransaction().rollback();
            productManager.close();
            userManager.close();
        }

    }

    @ApiMethod(path = "user/order", httpMethod = ApiMethod.HttpMethod.POST)
    public CollectionResponse<Order> getOrders(OrderPackage orderPackage) throws MOHException{
        EntityManager userManager = EMFUser.get().createEntityManager();
        EntityManager productManager = EMFProduct.get().createEntityManager();
        Status status;
        User user;
        try{
            logger.log(Level.INFO,orderPackage.getOrder().getId_home()+":home");
            status = verifyIdentity(orderPackage.getIdentify(),userManager);
            if(status!=Status.USER_ALREADY_EXISTS) throw new MOHException(status.getMessage(),status.getCode());
            user = ((Identify)status.getResponse()).getUser();
            orderPackage.getOrder().getId_home();
            TypedQuery<Order> query = productManager.createNamedQuery("Order.getOrdersByHome",Order.class);
            query.setParameter(Constants.COrder.ID_HOME,orderPackage.getOrder().getId_home());
            List<Order> orders = query.getResultList();
            if(orders==null) throw new MOHException("Object not found", MOHException.STATUS_OBJECT_NOT_FOUND);
            ArrayList<Order> os = new ArrayList<>();
            boolean f = false;

            for(Order o: orders){
                for(CustomerOrder co: o.getCustomerOrders())
                    f = f || co.getId_customer() == user.getId_user();

                if(f)os.add(o);
                f=false;
            }

            return CollectionResponse.<Order>builder().setItems(os).build();
        }


        finally {
            userManager.close();
            productManager.close();
        }
    }

    private Status verifyIdentity(Identify identify, EntityManager manager){

        TypedQuery<Identify> query = manager.createNamedQuery("Identify.verifyIdentity", Identify.class);

        query.setParameter(Constants.CIdentity.ID_ADAPTER,identify.getId_adapter());
        query.setParameter(Constants.CIdentity.ADAPTER,identify.getAdapter());
        List<Identify> ids = query.getResultList();
        Identify ident = ids.isEmpty()?null:ids.get(0);
        if(ident!=null) return Status.USER_ALREADY_EXISTS.withResponse(ident);
        else return Status.OK;
    }

    enum Status{
        WRONG_USER(MOHException.STATUS_OBJECT_NOT_FOUND,"User not found"),
        USER_ALREADY_EXISTS(MOHException.STATUS_OBJECT_NOT_ACCESSIBLE,"User already exists"),
        NOT_ENOUGH_DATA(MOHException.STATUS_NOT_ENOUGH_DATA,"Missing fields"),
        OK(3,"Status OK");

        private int code;
        private String message;
        private Object response;


        Status(int code, String message, Object response) {
            this.code = code;
            this.message = message;
            this.response = response;
        }

        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }
        public Status withResponse(Object response){
            this.response= response;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public Object getResponse() {
            return response;
        }
    }
}