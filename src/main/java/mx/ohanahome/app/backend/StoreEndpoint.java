package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.logging.Logger;

import javax.inject.Named;

import mx.ohanahome.app.backend.entity.user.Store;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "storeApi",
        version = "v1",
        resource = "store",
        namespace = @ApiNamespace(
                ownerDomain = "user.entity.backend.app.ohanahome.mx",
                ownerName = "user.entity.backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class StoreEndpoint {

    private static final Logger logger = Logger.getLogger(StoreEndpoint.class.getName());

    /**
     * This method gets the <code>Store</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Store</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getStore")
    public Store getStore(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getStore method");
        return null;
    }

    /**
     * This inserts a new <code>Store</code> object.
     *
     * @param store The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertStore")
    public Store insertStore(Store store) {
        // TODO: Implement this function
        logger.info("Calling insertStore method");
        return store;
    }
}