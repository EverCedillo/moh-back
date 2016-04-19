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

    /**
     * This method gets the <code>User</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>User</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getUser")
    public User getUser(@Named("id") Long id) {
        // TODO: Implement this function
        DbConnection connection = new DbConnection();

        EntityManager manager = connection.getEntityManagerFactory(DbConnection.USER_DATABASE).createEntityManager();
        User user = manager.find(User.class, id);
        manager.close();
        logger.info("Calling getUser method");
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

        Identify identify = loginPackage.getIdentify();
        Status status =verifyIdentity(identify, manager);
        if (status!=Status.OK)
            throw new MOHException(status.name());


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

    enum Status{
        WRONG_USER,USER_ALREADY_EXISTS,NOT_ENOUGH_DATA,OK;
    }
}