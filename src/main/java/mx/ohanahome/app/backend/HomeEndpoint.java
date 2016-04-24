package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;


import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.Home;
import mx.ohanahome.app.backend.entity.Identify;
import mx.ohanahome.app.backend.entity.User;
import mx.ohanahome.app.backend.model.HomePackage;
import mx.ohanahome.app.backend.util.Constants;
import mx.ohanahome.app.backend.util.DbConnection;
import mx.ohanahome.app.backend.util.MOHException;

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
    public Home getHome(@Named("id") long id) throws MOHException{

       /* Identify identify=homePackage.getIdentify();
        //todo drop this function
        Status status;
        status=validateFields(homePackage);
        if(status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());
*/
        DbConnection connection  = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory(Constants.USER_DATABASE).createEntityManager();
/*
        if(identify==null) throw new MOHException(Status.AUTH_ERROR.getMessage(),MOHException.STATUS_AUTH_ERROR);
        status = verifyIdentity(identify, manager);
        if(status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());*/

        Home home=manager.find(Home.class, id);
        logger.info("Calling getHome method");
        if(home==null) throw new MOHException(Status.HOME_NOT_FOUND.getMessage(),MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);
        manager.close();
        return home;
    }

    /**
     * This inserts a new <code>Home</code> object.
     *
     * @param homePackage The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertHome")
    public Home insertHome(HomePackage homePackage) throws MOHException{
        // TODO: Implement this function

        Identify identify=homePackage.getIdentify();

        Status status;
        status=validateFields(homePackage);
        if(status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());

        DbConnection connection  = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory(Constants.USER_DATABASE).createEntityManager();

        if(identify==null) throw new MOHException(Status.AUTH_ERROR.getMessage(),MOHException.STATUS_AUTH_ERROR);
        status = verifyIdentity(identify, manager);
        if(status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());

        identify=(Identify)status.getResponse();
        Home home=homePackage.getHome();


        manager.getTransaction().begin();
        home.setModification_date(new Date());
        home.setCreation_date(new Date());
        User user=identify.getUser();
        user.addHome(home);
        manager.persist(user);
        manager.getTransaction().commit();
        manager.close();
        logger.info("Calling insertHome method");
        return home;
    }

    private Status validateFields(HomePackage homePackage){
        if(false){
            return Status.NOT_ENOUGH_DATA;
        }
        return Status.OK;
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
        if(ident!=null) return Status.OK.withResponse(ident);
        else return Status.AUTH_ERROR;
    }
    enum Status{
        HOME_NOT_FOUND(MOHException.STATUS_OBJECT_NOT_ACCESSIBLE,"User already exists"),
        NOT_ENOUGH_DATA(MOHException.STATUS_NOT_ENOUGH_DATA,"Missing fields"),
        AUTH_ERROR(MOHException.STATUS_AUTH_ERROR,"Auth error"),
        OK(-1,"Status OK");

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