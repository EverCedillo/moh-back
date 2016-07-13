package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.sun.javafx.binding.StringFormatter;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.product.Customer;
import mx.ohanahome.app.backend.entity.product.Order;
import mx.ohanahome.app.backend.entity.product.OrderProduct;
import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.RegistrationRecord;
import mx.ohanahome.app.backend.entity.user.User;
import mx.ohanahome.app.backend.model.OrderPackage;

import mx.ohanahome.app.backend.util.Constants;
import mx.ohanahome.app.backend.util.EMFProduct;
import mx.ohanahome.app.backend.util.EMFUser;
import mx.ohanahome.app.backend.util.MOHException;

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
     * @param id The id of the object to be returned.
     * @return The <code>Order</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getOrder")
    public Order getOrder(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getOrder method");
        return null;
    }

    /**
     * This inserts a new <code>Order</code> object.
     *
     * @param orderPackage The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertOrder")
    public Order insertOrder(OrderPackage orderPackage) throws MOHException{
        // TODO: Implement this function
        Status status;
        EntityManager productManager = EMFProduct.get().createEntityManager();
        EntityManager userManager = EMFUser.get().createEntityManager();

        Order order= new Order("");
        Identify identify = orderPackage.getIdentify();
        User user;

        EntityTransaction transaction = productManager.getTransaction();
        try {
            status = verifyIdentity(identify,userManager);
            if(status!=Status.USER_ALREADY_EXISTS) throw new MOHException(status.getMessage(),status.getCode());
            identify = (Identify)status.getResponse();
            user=identify.getUser();

            List<OrderProduct> orderProductList = orderPackage.getOrderProductList();
            Set<OrderProduct> productSet = new HashSet<>();
            Set<Customer> customers = new HashSet<>();
            Set<User> users = new HashSet<>();

            order = orderPackage.getOrder();
            order.setCreation_date(new Date());



            productSet.addAll(orderProductList);

            for(OrderProduct or: productSet){
                or.setCreation_date(new Date());
                customers.add(or.getCustomer_product());
            }

            for(Customer customer: customers){
                customer = productManager.find(Customer.class,customer.getId_customer());
                users.add(userManager.find(User.class,customer.getId_customer()));
            }

            order.setOrderProducts(productSet);

            transaction.begin();
            productManager.persist(order);
            productManager.flush();

            try {
                for (User u : users)
                    for (RegistrationRecord r : u.getRecords()) {
                        String message = StringFormatter.format(Constants.COrder.ORDER_INVITATION_MSG, user.getUser_name()).getValue();
                        new MessagingEndpoint().sendMessage(message, r, Constants.CMessaging.ORDER_INVITATION_TOPIC, String.valueOf(order.getId_order()));
                    }
            }catch (IOException e){
                logger.log(Level.WARNING,e.getMessage(),e.getCause());

            }
            transaction.commit();

        }catch (Exception e){
            logger.log(Level.WARNING,e.getMessage(),e.getCause());
        }
        finally {
            productManager.close();
            userManager.close();
        }

        logger.info("Calling insertOrder method");
        return order;
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