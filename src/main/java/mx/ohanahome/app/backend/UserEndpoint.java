package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.sql.Date;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;

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
     * @param loginPackage The <code>LoginPackage</code> containing the user info.
     * @return The <code>User</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getUser")
    public User getUser(LoginPackage loginPackage, @Named("requestType")int flag) throws MOHException{

        User user=null;
        DbConnection connection = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory(DbConnection.USER_DATABASE).createEntityManager();
        switch (flag){
            case FIND_BY_ID:
                long id=loginPackage.getUser().getId_user();
                user = manager.find(User.class,id);
                break;
            case FIND_BY_IDENTITY:
                MOHQuery<User> query = new MOHQuery<>(manager);
                user = query.select(User.class, Constants.TOH_USER.ID_IDENTIFY+"="+loginPackage.getIdentify().getId_identify());
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
        EntityManager manager = connection.getEntityManagerFactory(DbConnection.USER_DATABASE).createEntityManager();
        Status status;

        status = validateFields(loginPackage);
        if (status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());
        status = verifyIdentity(loginPackage.getIdentify(), manager);
        if(status!=Status.USER_ALREADY_EXISTS) throw new MOHException(status.getMessage(),status.getCode());

        User user;
        user = getUser(loginPackage,FIND_BY_ID);
        Identify identify = manager.find(Identify.class,user.getIdentify().getId_identify());

        manager.getTransaction().begin();
        identify.updateIdentify(loginPackage.getIdentify());
        user.updateValues(loginPackage.getUser());
        user.setIdentify(identify);
        user.setModification_date(new java.util.Date());

        manager.persist(user);
        manager.getTransaction().commit();

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
        DbConnection connection = new DbConnection();

        EntityManager manager =connection.getEntityManagerFactory(DbConnection.USER_DATABASE).createEntityManager();

        Status status;
        Identify identify = loginPackage.getIdentify();

        status = validateFields(loginPackage);
        if(status!=Status.OK)
            throw new MOHException(status.getMessage(),status.getCode());
        status = verifyIdentity(identify, manager);
        if (status!=Status.OK)
            throw new MOHException(status.getMessage(),status.getCode());


        User user = loginPackage.getUser();



        manager.getTransaction().begin();
        manager.persist(identify);
        user.setIdentify(identify);
        user.setCreation_date(new java.util.Date());
        user.setModification_date(new java.util.Date());
        manager.persist(user);
        manager.getTransaction().commit();
        manager.close();

        return user;
    }

    private Status verifyIdentity(Identify identify, EntityManager manager){
        MOHQuery<User> query = new MOHQuery<>(manager);
        String where = Constants.TOH_USER.ID_ADAPTER +"="+ identify.getId_adapter()+" AND "+ Constants.TOH_USER.ADAPTER+"="+identify.getAdapter();
        User user = query.select(User.class,where);
        if(user==null) return Status.USER_ALREADY_EXISTS;
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

        Status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

}