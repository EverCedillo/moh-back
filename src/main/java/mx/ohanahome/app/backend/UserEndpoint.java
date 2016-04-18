package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.appengine.repackaged.com.google.api.client.util.DateTime;

import java.sql.Date;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;

import mx.ohanahome.app.backend.model.Identify;
import mx.ohanahome.app.backend.model.User;
import mx.ohanahome.app.backend.util.DbConnection;

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
     * @param user The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertUser")
    public User insertUser(User user,
                           @Named("idAdapter") String id_adapter,
                           @Named("adapter")String adapter,
                           @Named("email_")String email_,
                           @Named("creationDate") Date creationDate,
                           @Named("modificationDate")Date modificationDate) {
        DbConnection connection = new DbConnection();


        Identify identify = new Identify(id_adapter,adapter,email_,creationDate,modificationDate);

        EntityManager manager =connection.getEntityManagerFactory(DbConnection.USER_DATABASE).createEntityManager();

        manager.getTransaction().begin();
        manager.persist(identify);
        user.setIdentify(identify);
        user.setCreation_date(new java.util.Date());
        user.setModification_date(new java.util.Date());
        manager.persist(user);
        manager.getTransaction().commit();
        manager.close();

        logger.info("Calling insertUser method");
        return user;
    }
}