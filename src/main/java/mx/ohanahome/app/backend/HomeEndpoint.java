package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;


import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.user.Home;
import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.Permission;
import mx.ohanahome.app.backend.entity.user.Role;
import mx.ohanahome.app.backend.entity.user.User;
import mx.ohanahome.app.backend.entity.user.UserRole;
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
        EntityManager manager = connection.getEntityManagerFactory(Constants.DB.USER_DATABASE).createEntityManager();
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

    //todo: if the role changes my permissions change as well, so insert new role,drop the existent, modify the name and permission?

    @ApiMethod(name = "linkUser", path = "myHome",httpMethod = ApiMethod.HttpMethod.POST)
    public Home linkUser(HomePackage homePackage) throws MOHException{
        Identify identify=homePackage.getIdentify();

        //todo invitation? permission?

        Status status;
        status=validateFields(homePackage);
        if(status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());

        DbConnection connection  = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory(Constants.DB.USER_DATABASE).createEntityManager();

        if(identify==null) throw new MOHException(Status.AUTH_ERROR.getMessage(),MOHException.STATUS_AUTH_ERROR);
        status = verifyIdentity(identify, manager);
        if(status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());

        identify=(Identify)status.getResponse();

        Home home = manager.find(Home.class, homePackage.getHome().getId_home());
        User user=identify.getUser();
        Set<User> users=home.getUsers();
        for(User u: users)
            if(u.getId_user()==user.getId_user()) throw new MOHException(Status.HOME_NOT_ACCESSIBLE.getMessage(),MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);

        manager.getTransaction().begin();
        UserRole userRole = new UserRole();
        Role role = new Role(Constants.CRole.NORMAL_ROLE);
        role.addPermissions(Permission.getNormalPermissions());

        userRole.setStart_date(new Date());
        userRole.setModification_date(new Date());

        userRole.setRole(role);
        userRole.setUser(user);
        userRole.setHome(home);

        user.addHome(home);
        manager.persist(user);
        manager.persist(role);

        manager.persist(userRole);
        manager.getTransaction().commit();
        manager.close();

        return home;
    }
    /**
     * This inserts a new <code>Home</code> object.
     *
     * @param homePackage The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertHome",httpMethod = ApiMethod.HttpMethod.POST)
    public Home insertHome(HomePackage homePackage) throws MOHException{


        Identify identify=homePackage.getIdentify();

        Status status;
        status=validateFields(homePackage);
        if(status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());

        DbConnection connection  = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory(Constants.DB.USER_DATABASE).createEntityManager();

        if(identify==null) throw new MOHException(Status.AUTH_ERROR.getMessage(),MOHException.STATUS_AUTH_ERROR);
        status = verifyIdentity(identify, manager);
        if(status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());

        identify=(Identify)status.getResponse();
        Home home=homePackage.getHome();




        manager.getTransaction().begin();

        User user=identify.getUser();

        home.setModification_date(new Date());
        home.setCreation_date(new Date());
        home.setCreator_id(user.getId_user());
        user.addHome(home);
        manager.persist(user);

        Role role = new Role(Constants.CRole.ADMIN_ROLE);
        role.addPermissions(Permission.getAdminPermissions());
        manager.persist(role);

        UserRole userRole = new UserRole();
        userRole.setModification_date(new Date());
        userRole.setStart_date(new Date());
        userRole.setHome(home);
        userRole.setUser(user);
        userRole.setRole(role);

        manager.persist(userRole);

        manager.getTransaction().commit();
        manager.close();
        logger.info("Calling insertHome method");
        return home;
    }

    @ApiMethod(name = "updateHome")
    public Home updateHome(HomePackage homePackage) throws MOHException{
        Identify identify = homePackage.getIdentify();

        //todo permission?
        Status status;
        status=validateFields(homePackage);
        if(status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());

        DbConnection connection = new DbConnection();
        EntityManager manager = connection.getEntityManagerFactory(Constants.DB.USER_DATABASE).createEntityManager();

        if(identify==null) throw new MOHException(Status.AUTH_ERROR.getMessage(),MOHException.STATUS_AUTH_ERROR);
        status=verifyIdentity(identify,manager);
        if (status!=Status.OK) throw new MOHException(status.getMessage(),status.getCode());

        Home home = manager.find(Home.class,homePackage.getHome().getId_home());
        if(home==null) throw new MOHException(Status.HOME_NOT_FOUND.getMessage(), MOHException.STATUS_OBJECT_NOT_FOUND);

        manager.getTransaction().begin();
        home.mergeValues(homePackage.getHome());
        home.setModification_date(new Date());

        manager.persist(home);
        manager.getTransaction().commit();
        manager.close();

        return home;

    }

    private Status validateFields(HomePackage homePackage){
        if(false){
            return Status.NOT_ENOUGH_DATA;
        }
        return Status.OK;
    }
    private Status verifyIdentity(Identify identify, EntityManager manager){



        TypedQuery<Identify> query = manager.createNamedQuery("Identify.verifyIdentity", Identify.class);



        query.setParameter(Constants.CIdentity.ID_ADAPTER,identify.getId_adapter());
        query.setParameter(Constants.CIdentity.ADAPTER,identify.getAdapter());
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