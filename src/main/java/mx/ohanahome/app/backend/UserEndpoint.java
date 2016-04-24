package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.beans.Expression;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Parameter;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.Identify;
import mx.ohanahome.app.backend.entity.User;
import mx.ohanahome.app.backend.model.LoginPackage;
import mx.ohanahome.app.backend.util.Constants;
import mx.ohanahome.app.backend.util.DbConnection;
import mx.ohanahome.app.backend.util.MOHException;
import mx.ohanahome.app.backend.util.MOHQuery;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "userApi",
        version = "v1",
        resource = "user",
        namespace = @ApiNamespace(
                ownerDomain = "model.backend.app.ohanahome.mx",
                ownerName = "model.backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class UserEndpoint {

    private static final Logger logger = Logger.getLogger(UserEndpoint.class.getName());

    public static final int FIND_BY_ID = 0;
    public static final int FIND_BY_IDENTITY = 1;

    /**
     * This method gets the <code>User</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the <code>User</code> or <code>Identify</code> object.
     * @return The <code>User</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getUser")
    public User getUser(@Named("id")long id, @Named("requestType")int flag) throws MOHException{

        User user=null;
        DbConnection connection = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory(Constants.USER_DATABASE).createEntityManager();
        switch (flag){
            case FIND_BY_ID:
                user = manager.find(User.class,id);
                break;
            case FIND_BY_IDENTITY:
                Identify identify = manager.find(Identify.class,id);
                user=identify.getUser();
                break;
        }
        if(user==null) throw new MOHException(Status.WRONG_USER.getMessage(),Status.WRONG_USER.getCode());

        manager.close();

        return user;
    }

    /**
     * This update a existent <code>User</code> object
     *
     * @param  loginPackage Package with <code>User</code> and <code>Identify</code> object to be updated.
     * @return The object updated
     */
    @ApiMethod(name = "updateUser")
    public User updateUser (LoginPackage loginPackage)throws MOHException{
        DbConnection connection = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory(Constants.USER_DATABASE).createEntityManager();
        Status status;

        status = validateFields(loginPackage);
        if (status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());
        status = verifyIdentity(loginPackage.getIdentify(), manager);
        if(status!=Status.USER_ALREADY_EXISTS) throw new MOHException(status.getMessage(),status.getCode());
        Identify identify = (Identify)status.getResponse();
        User user =identify.getUser();
        manager.getTransaction().begin();
        user.mergeValues(loginPackage.getUser());
        user.setModification_date(new java.util.Date());

        manager.persist(user);
        manager.getTransaction().commit();
        manager.close();
        return user;
    }

    /**
     * This inserts a new <code>User</code> object.
     *
     * @param loginPackage Package with <code>User</code> and <code>Identify</code> object.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertUser")
    public User insertUser(LoginPackage loginPackage) throws MOHException{


        Status status;
        Identify identify = loginPackage.getIdentify();

        status = validateFields(loginPackage);
        if(status!=Status.OK)
            throw new MOHException(status.getMessage(),status.getCode());

        DbConnection connection = new DbConnection();
        EntityManager manager =connection.getEntityManagerFactory(Constants.USER_DATABASE).createEntityManager();

        status = verifyIdentity(identify, manager);
        if (status!=Status.OK)
            throw new MOHException(status.getMessage(),status.getCode());


        User user = loginPackage.getUser();



        manager.getTransaction().begin();

        user.setCreation_date(new java.util.Date());
        user.setModification_date(new java.util.Date());
        user.setBirthday(new java.util.Date());
        manager.persist(user);

        identify.setCreation_date(new java.util.Date());
        identify.setModification_date(new java.util.Date());
        identify.setUser(user);
        manager.persist(identify);

        manager.getTransaction().commit();
        manager.close();

        return user;
    }

    private Status verifyIdentity(Identify identify, EntityManager manager){

        String q = "select "+ Constants.UNIVERSAL_ALIAS+ " from Identify "+ Constants.UNIVERSAL_ALIAS+ " where "+
                Constants.TOH_USER.ID_ADAPTER.name +"=?1 AND "+
                Constants.TOH_USER.ADAPTER.name+"=?2";



        TypedQuery<Identify> query = manager.createQuery(q,Identify.class);



        query.setParameter(1,identify.getId_adapter());
        query.setParameter(2,identify.getAdapter());
        List<Identify> ids = query.getResultList();
        Identify ident = ids.isEmpty()?null:ids.get(0);
        if(ident!=null) return Status.USER_ALREADY_EXISTS.withResponse(ident);
        else return Status.OK;
    }

    private Status validateFields(LoginPackage loginPackage){
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