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

import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.Illness;
import mx.ohanahome.app.backend.entity.user.Intolerance;
import mx.ohanahome.app.backend.entity.user.User;
import mx.ohanahome.app.backend.model.AddInfoPackage;
import mx.ohanahome.app.backend.util.Constants;
import mx.ohanahome.app.backend.util.EMFUser;
import mx.ohanahome.app.backend.util.MOHException;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "infoApi",
        version = "v1",
        resource = "info",
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

    @ApiMethod(path = "me/info" , httpMethod = ApiMethod.HttpMethod.POST)
    public void deleteInfo(AddInfoPackage infoPackage) throws MOHException{
        EntityManager userManager = EMFUser.get().createEntityManager();
        Status status;

        try {
            status = verifyIdentity(infoPackage.getIdentify(), userManager);
            if (status != Status.OK) throw new MOHException(status.getMessage(), status.getCode());
            Identify identify = (Identify) status.getResponse();

            userManager.getTransaction().begin();

            User user = identify.getUser();

            if(infoPackage.getIllness()!=null){
                Illness illness = userManager.find(Illness.class,infoPackage.getIllness().getId_illness());
                user.deleteIllness(illness);
            }
            if(infoPackage.getIntolerance()!=null){
                Intolerance intolerance = userManager.find(Intolerance.class,infoPackage.getIntolerance().getId_intolerance());
                user.deleteIntolerance(intolerance);
            }
            userManager.persist(user);

            userManager.getTransaction().commit();
        }finally {
            userManager.close();
        }
    }

    @ApiMethod(path = "me/info" , httpMethod = ApiMethod.HttpMethod.PUT)
    public void addInfo(AddInfoPackage infoPackage) throws MOHException{
        EntityManager userManager = EMFUser.get().createEntityManager();
        Status status;

        try {
            status = verifyIdentity(infoPackage.getIdentify(), userManager);
            if (status != Status.OK) throw new MOHException(status.getMessage(), status.getCode());
            Identify identify = (Identify) status.getResponse();

            userManager.getTransaction().begin();

            User user = identify.getUser();
            if (infoPackage.getIllness() != null) {
                Illness illness = userManager.find(Illness.class, infoPackage.getIllness().getId_illness());
                user.addIllness(illness);
            }
            if (infoPackage.getIntolerance() != null) {
                Intolerance intolerance = userManager.find(Intolerance.class, infoPackage.getIntolerance().getId_intolerance());
                user.addIntolerance(intolerance);
            }

            userManager.persist(user);

            userManager.getTransaction().commit();
        }finally {
            userManager.close();
        }

    }
    /*
    @ApiMethod(name = "linkIllness", path = "user/illness")
    public Illness linkIllness(AddInfoPackage infoPackage) t{
        EntityManager userManager = EMFUser.get().createEntityManager();
        Status status;
        status=verifyIdentity(infoPackage.getIdentify(), userManager);
        if(status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());
    }

*/
    private Status verifyIdentity(Identify identify, EntityManager manager){

        TypedQuery<Identify> query = manager.createNamedQuery("Identify.verifyIdentity", Identify.class);


        query.setParameter(Constants.CIdentity.ID_ADAPTER,identify.getId_adapter());
        query.setParameter(Constants.CIdentity.ADAPTER, identify.getAdapter());

        List<Identify> ids = query.getResultList();

        Identify ident = ids.isEmpty()?null:ids.get(0);
        if(ident!=null) return Status.OK.withResponse(ident);
        else return Status.AUTH_ERROR;
    }
    enum Status{
        HOME_NOT_FOUND(MOHException.STATUS_OBJECT_NOT_FOUND,"Home not found"),
        HOME_NOT_ACCESSIBLE(MOHException.STATUS_OBJECT_NOT_ACCESSIBLE,"Can not update or retrieve the object"),
        NOT_ENOUGH_DATA(MOHException.STATUS_NOT_ENOUGH_DATA,"Missing fields"),
        AUTH_ERROR(MOHException.STATUS_AUTH_ERROR,"Auth error"),
        OK(-1,"Status OK");

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