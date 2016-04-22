package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;

import mx.ohanahome.app.backend.entity.Home;
import mx.ohanahome.app.backend.util.DbConnection;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "homeApi",
        version = "v1",
        resource = "home",
        namespace = @ApiNamespace(
                ownerDomain = "entity.backend.app.ohanahome.mx",
                ownerName = "entity.backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class HomeEndpoint {

    private static final Logger logger = Logger.getLogger(HomeEndpoint.class.getName());

    /**
     * This method gets the <code>Home</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Home</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getHome")
    public Home getHome(@Named("id") Long id) {
        // TODO: Implement this function
        DbConnection connection = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory(DbConnection.USER_DATABASE).createEntityManager();

        Home home;

        home=manager.find(Home.class,id);
        logger.info("Calling getHome method");
        return home;
    }

    /**
     * This inserts a new <code>Home</code> object.
     *
     * @param home The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertHome")
    public Home insertHome(Home home) {
        // TODO: Implement this function
        logger.info("Calling insertHome method");
        return home;
    }
}