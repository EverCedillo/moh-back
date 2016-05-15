package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.user.Illness;
import mx.ohanahome.app.backend.entity.user.Intolerance;
import mx.ohanahome.app.backend.util.EMFUser;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "infoApi",
        version = "v1",
        resource = "intolerance",
        namespace = @ApiNamespace(
                ownerDomain = "backend.app.ohanahome.mx",
                ownerName = "backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class InfoEndpoint {

    private static final Logger logger = Logger.getLogger(InfoEndpoint.class.getName());

    /**
     * This method gets the <code>Intolerance</code> object associated with the specified <code>id</code>.
     *
     * @return The <code>Intolerance</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "listIntolerance", path = "intolerance")
    public CollectionResponse<Intolerance> getIntolerance() {
        EntityManager userManager = EMFUser.get().createEntityManager();

        TypedQuery<Intolerance> query = userManager.createQuery("select i from Intolerance i", Intolerance.class);

        List<Intolerance> list = query.getResultList();

        return CollectionResponse.<Intolerance>builder().setItems(list).build();
    }

    /**
     * This inserts a new <code>Intolerance</code> object.
     *
     * @param intolerance The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertIntolerance")
    public Intolerance insertIntolerance(Intolerance intolerance) {
        // TODO: Implement this function
        logger.info("Calling insertIntolerance method");
        return intolerance;
    }

    @ApiMethod(name = "listIllness", path = "illness")
    public CollectionResponse<Illness> listIllness(){
        EntityManager userManager = EMFUser.get().createEntityManager();

        TypedQuery<Illness> query = userManager.createQuery("select i from Illness i", Illness.class);

        List<Illness> list = query.getResultList();

        return CollectionResponse.<Illness>builder().setItems(list).build();
    }
}