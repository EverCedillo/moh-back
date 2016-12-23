package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NamedQuery;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.product.Product;
import mx.ohanahome.app.backend.entity.product.Stock;
import mx.ohanahome.app.backend.entity.product.StockDetail;
import mx.ohanahome.app.backend.entity.product.StockProduct;
import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.RRegRecord;
import mx.ohanahome.app.backend.entity.user.RegistrationRecord;
import mx.ohanahome.app.backend.entity.user.User;
import mx.ohanahome.app.backend.model.InvitationPackage;
import mx.ohanahome.app.backend.model.NotificationPackage;
import mx.ohanahome.app.backend.model.StockPackage;
import mx.ohanahome.app.backend.util.Constants;
import mx.ohanahome.app.backend.util.EMFProduct;
import mx.ohanahome.app.backend.util.EMFUser;
import mx.ohanahome.app.backend.util.MOHException;

import static mx.ohanahome.app.backend.util.Constants.CProduct.AMOUNT;
import static mx.ohanahome.app.backend.util.Constants.CProduct.BRAND;
import static mx.ohanahome.app.backend.util.Constants.CProduct.CATEGORY;
import static mx.ohanahome.app.backend.util.Constants.CProduct.DEPTO;
import static mx.ohanahome.app.backend.util.Constants.CProduct.IMAGE;
import static mx.ohanahome.app.backend.util.Constants.CProduct.ORDER_QUANTITY;
import static mx.ohanahome.app.backend.util.Constants.CProduct.PRODUCT_NAME;
import static mx.ohanahome.app.backend.util.Constants.CProduct.PRODUCT_NO;
import static mx.ohanahome.app.backend.util.Constants.CProduct.SUB_CATEGORY;
import static mx.ohanahome.app.backend.util.Constants.CProduct.UNIT;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "stockApi",
        version = "v1",
        resource = "stock",
        namespace = @ApiNamespace(
                ownerDomain = "product.entity.backend.app.ohanahome.mx",
                ownerName = "product.entity.backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class StockEndpoint {

    private static final Logger logger = Logger.getLogger(StockEndpoint.class.getName());

    /**
     * This method gets the <code>Stock</code> object associated with the specified <code>id</code>.
     *
     * @return The <code>Stock</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getStock", httpMethod = ApiMethod.HttpMethod.POST)
    public CollectionResponse<StockProduct> getStock(StockPackage stockPackage) throws MOHException{

        EntityManager userManager = EMFUser.get().createEntityManager();
        EntityManager productManager = EMFProduct.get().createEntityManager();

        Status status;
        try{
            status = verifyIdentity(stockPackage, userManager);
            if(status!=Status.USER_ALREADY_EXISTS) throw new MOHException(status.getMessage(),status.getCode());
            Identify identify =(Identify) status.getResponse();

            Stock stock = stockPackage.getStock();
            TypedQuery<StockProduct> query = productManager.createNamedQuery("Stock.getItems",StockProduct.class);
            query.setParameter(Constants.CStock.ID_STOCK, stock.getId_stock());
            List<StockProduct> stockProductList = query.getResultList();
            if(stockProductList==null) throw new MOHException("Object not found",MOHException.STATUS_OBJECT_NOT_FOUND);

            for(StockProduct sp : stockProductList){
                TypedQuery<StockDetail> query1 = productManager.createNamedQuery("StockProduct.getDetails",StockDetail.class);
                query1.setParameter(Constants.CStockDetail.ID_STOCK_PRODUCT,sp.getId_stock_product());
                List<StockDetail> details = query1.getResultList();
                if(details==null||details.isEmpty()) break;
                sp.setType(details.get(0).getMovement_stock());

            }

            return CollectionResponse.<StockProduct>builder().setItems(stockProductList).build();
        }

        finally {
            userManager.close();
            productManager.close();
        }
    }

    /**
     * This inserts a new <code>Stock</code> object.
     *
     * @return The object to be added.
     */
    @ApiMethod(name = "insertStockItem", path = "stock/item", httpMethod = ApiMethod.HttpMethod.PUT)
    public StockProduct insertStockItem(StockPackage stockPackage) throws MOHException{
        EntityManager userManager = EMFUser.get().createEntityManager();
        EntityManager productManager = EMFProduct.get().createEntityManager();
        Status status;
        EntityTransaction transaction = productManager.getTransaction();
        User user;
        Product product;
        HashMap<String,String> extras = new HashMap<>();
        try {
            status = verifyIdentity(stockPackage,userManager);
            if(status!=Status.USER_ALREADY_EXISTS) throw new MOHException(status.getMessage(),status.getCode());
            user = ((Identify)status.getResponse()).getUser();

            logger.log(Level.INFO,user.getEmail());

            StockProduct stockProduct = stockPackage.getItem();
            StockDetail stockDetail = stockPackage.getItemDetails();
            Stock stock = stockPackage.getStock();

            product = stockProduct.getProduct();
            product = productManager.find(Product.class,product.getId_product());

            transaction.begin();
            if(stockPackage.isUpdating()){
                StockProduct sp = productManager.find(StockProduct.class,stockProduct.getId_stock_product());
                if(sp==null) throw new MOHException("Object not found", MOHException.STATUS_OBJECT_NOT_FOUND);

                stockDetail.setQuantity(sp.getStock_quantity()-stockDetail.getQuantity());
                sp.setId_customer(stockProduct.getId_customer());
                sp.setStock_quantity(stockProduct.getStock_quantity());
                sp.setModification_date(new Date());

                productManager.persist(sp);



                stockProduct = sp;
            }else {



                stock = productManager.find(Stock.class, stock.getId_stock());

                if(product==null)throw new MOHException("Wrong product, object not found",MOHException.STATUS_NOT_ENOUGH_DATA);
                stockProduct.setProduct(product);
                stockProduct.setStock(stock);
                stockProduct.setModification_date(new Date());
                productManager.persist(stockProduct);




            }
            productManager.flush();


            stockDetail.setId_stock_product(stockProduct.getId_stock_product());
            stockDetail.setStock_date(new Date());
            productManager.persist(stockDetail);

            TypedQuery<RRegRecord> query = userManager.createNamedQuery("Home.getUsersTokens", RRegRecord.class);
            query.setParameter(1,stock.getId_stock());
            List<RRegRecord> records = query.getResultList();




            extras.put(Constants.CStockDetail.MOVEMENT_STOCK,stockDetail.getMovement_stock());
            extras.put(Constants.CStockProduct.STOCK_QUANTITY, String.valueOf(stockProduct.getStock_quantity()));
            extras.put(Constants.CStockProduct.ID_PRODUCT,String .valueOf(stockProduct));
            extras.put(Constants.CStockProduct.ID_CUSTOMER,String.valueOf(stockProduct.getId_customer()));
            extras.put(Constants.CStockProduct.PRODUCT_TYPE,stockProduct.getProduct_type());
            extras.put(Constants.CStockProduct.ID_HOME, String.valueOf(stock.getId_stock()));
            extras.put(Constants.CStockProduct.ID_STOCK_PRODUCT,String.valueOf(stockProduct.getId_stock_product()));


            extras.put(Constants.COrderProduct.ID_PRODUCT, String.valueOf(product.getId_product()));
            extras.put(PRODUCT_NAME, product.getProduct_name());
            extras.put(ORDER_QUANTITY, String.valueOf(product.getOrder_quantity()));
            extras.put(CATEGORY, String.valueOf(product.getCategory()));
            extras.put(SUB_CATEGORY, String.valueOf(product.getSub_category()));
            extras.put(DEPTO, String.valueOf(product.getDepto()));
            extras.put(AMOUNT, String.valueOf(product.getAmount()));
            extras.put(UNIT, String.valueOf(product.getUnit()));
            extras.put(BRAND, String.valueOf(product.getBrand()));
            extras.put(PRODUCT_NO, String.valueOf(product.getProduct_no()));
            extras.put(IMAGE, product.getImage());

            

            for (RRegRecord r :
                    records)    {

                logger.log(Level.INFO,user.getId_user()+"::"+r.getId_user());
                if(user.getId_user()!=r.getId_user()) {

                    logger.log(Level.INFO,user.getId_user()+"::"+r.getId_user());
                    NotificationPackage notificationPackage = new NotificationPackage();
                    RegistrationRecord rr = new RegistrationRecord();
                    rr.setToken(r.getToken());
                    notificationPackage.setRegistrationRecord(rr);
                    notificationPackage.setExtras(extras);

                    try {
                        new MessagingEndpoint().sendMessage(
                                String.valueOf(stockProduct.getId_stock_product()),
                                notificationPackage,
                                Constants.CMessaging.STOCK_ITEM_PUSH_TOPIC);
                    } catch (IOException e) {
                        logger.log(Level.WARNING, e.getMessage(), e.getCause());
                    }
                }

            }

            stockProduct.setType(stockDetail.getMovement_stock());



            transaction.commit();
            return  stockProduct;

        }


        finally {
            userManager.close();
            if(transaction.isActive())
                transaction.rollback();
            productManager.close();
        }

    }

    @ApiMethod(name = "getStockItem", path = "stock/item", httpMethod = ApiMethod.HttpMethod.POST)
    public StockProduct getStockItem(StockPackage stockPackage) throws MOHException{
        EntityManager userManager = EMFUser.get().createEntityManager();
        EntityManager productManager = EMFProduct.get().createEntityManager();

        Status status;
        try {
            status = verifyIdentity(stockPackage, userManager);
            if (status != Status.USER_ALREADY_EXISTS)
                throw new MOHException(status.getMessage(), status.getCode());
            Identify identify = (Identify) status.getResponse();

            StockProduct stockProduct = stockPackage.getItem();
            stockProduct = productManager.find(StockProduct.class, stockProduct.getId_stock_product());
            if(stockProduct==null) throw new MOHException("Object not found", MOHException.STATUS_OBJECT_NOT_FOUND);


            TypedQuery<StockDetail> query1 = productManager.createNamedQuery("StockProduct.getDetails",StockDetail.class);
            query1.setParameter(Constants.CStockDetail.ID_STOCK_PRODUCT, stockProduct.getId_stock_product());
            List<StockDetail> details = query1.getResultList();
            if(details==null||details.isEmpty()) throw new MOHException("Object not consistent", MOHException.STATUS_NOT_ENOUGH_DATA);
            stockProduct.setType(details.get(0).getMovement_stock());

            return stockProduct;
        }

        catch (Exception e){
            logger.log(Level.WARNING,e.getMessage(),e.getCause());
            return null;
        }

        finally {
            productManager.close();
            userManager.close();
        }
    }

    @ApiMethod(name = "insertStockItemDetail", path = "stock/item/detail")
    public StockDetail insertStockItemDetail(StockPackage stockPackage) throws MOHException{
        EntityManager userManager = EMFUser.get().createEntityManager();
        EntityManager productManager = EMFProduct.get().createEntityManager();
        Status status;
        EntityTransaction transaction = productManager.getTransaction();
        try {
            status = verifyIdentity(stockPackage, userManager);
            if (status != Status.USER_ALREADY_EXISTS)
                throw new MOHException(status.getMessage(), status.getCode());

            StockDetail stockDetail = stockPackage.getItemDetails();

            transaction.begin();
            stockDetail.setStock_date(new Date());
            productManager.persist(stockDetail);
            transaction.commit();
            return stockDetail;
        }

        catch (Exception e){
            transaction.rollback();
            logger.log(Level.WARNING,e.getMessage(),e.getCause());
            return null;
        }
        finally {
            if(transaction.isActive())
                transaction.rollback();
            userManager.close();
            productManager.close();
        }

    }

    @ApiMethod

    private Status verifyIdentity(StockPackage stockPackage, EntityManager manager){

        TypedQuery<Identify> query = manager.createNamedQuery("Identify.verifyIdentity",Identify.class);
        Identify id = stockPackage.getIdentify();
        query.setParameter(Constants.CIdentity.ADAPTER,id.getAdapter());
        query.setParameter(Constants.CIdentity.ID_ADAPTER,id.getId_adapter());

        List<Identify> ids=query.getResultList();
        id=ids.isEmpty()?null:ids.get(0);

        if(id==null) return Status.WRONG_USER;
        return Status.USER_ALREADY_EXISTS.withResponse(id);
    }
    private Status validateFields(InvitationPackage invitationPackage){

        if(false)
        {
            return Status.NOT_ENOUGH_DATA;
        }
        return Status.OK;
    }

    enum Status{
        WRONG_USER(MOHException.STATUS_OBJECT_NOT_FOUND,"User not found"),
        USER_ALREADY_EXISTS(MOHException.STATUS_OBJECT_NOT_ACCESSIBLE,"User already exists"),
        NOT_ENOUGH_DATA(MOHException.STATUS_NOT_ENOUGH_DATA,"Missing fields"),
        OK(3,"Status OK");

        private int code;
        private String message;
        private Object response;



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