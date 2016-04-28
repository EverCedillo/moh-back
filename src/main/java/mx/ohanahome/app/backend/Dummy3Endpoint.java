package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;

import mx.ohanahome.app.backend.entity.Dummy3;
import mx.ohanahome.app.backend.util.DbConnection;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "dummy3Api",
        version = "v1",
        resource = "dummy3",
        namespace = @ApiNamespace(
                ownerDomain = "model.backend.app.ohanahome.mx",
                ownerName = "model.backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class Dummy3Endpoint {

    private static final Logger logger = Logger.getLogger(Dummy3Endpoint.class.getName());

    /**
     * This method gets the <code>Dummy3</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Dummy3</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getDummy3")
    public Dummy3 getDummy3(@Named("id") Long id) {

        Dummy3 dummy3;
        DbConnection connection = new DbConnection();

        EntityManager manager=connection.getEntityManagerFactory("test_gae").createEntityManager();

        dummy3 = manager.find(Dummy3.class,id);

        logger.info("Calling getDummy3 method");
        return dummy3;
    }

    /**
     * This inserts a new <code>Dummy3</code> object.
     *

     * @return The object to be added.
     */
    @ApiMethod(name = "insertDummy3")
    public Dummy3 insertDummy3(@Named("name") String name) {

        Dummy3 dummy3 = new Dummy3();
        DbConnection connection = new DbConnection();

        dummy3.setDate(null);

        EntityManager manager=connection.getEntityManagerFactory("test_gae").createEntityManager();
        manager.getTransaction().begin();
        manager.persist(dummy3);
        manager.getTransaction().commit();
        manager.close();
        logger.info("Calling insertDummy3 method");
        return dummy3;
    }
}