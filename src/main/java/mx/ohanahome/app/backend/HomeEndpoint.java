package mx.ohanahome.app.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.user.Home;
import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.Permission;
import mx.ohanahome.app.backend.entity.user.Role;
import mx.ohanahome.app.backend.entity.user.User;
import mx.ohanahome.app.backend.entity.user.UserRole;
import mx.ohanahome.app.backend.model.HomePackage;
import mx.ohanahome.app.backend.util.Constants;
import mx.ohanahome.app.backend.util.EMFInvitation;
import mx.ohanahome.app.backend.util.EMFProduct;
import mx.ohanahome.app.backend.util.EMFUser;
import mx.ohanahome.app.backend.util.MOHException;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "homeApi",
        version = "v1",
        resource = "home",
        namespace = @ApiNamespace(
                ownerDomain = "backend.app.ohanahome.mx",
                ownerName = "backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class HomeEndpoint {

    private static final int INSERT_HOME = 0;
    private static final int UPDATE_HOME = 1;
    private static final int GET_HOME = 2;

    private static final Logger logger = Logger.getLogger(HomeEndpoint.class.getName());

    /**
     * This method gets the <code>Home</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Home</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getHome")
    public Home getHome(@Named("id") long id, Identify identify) throws MOHException{

        Home home;
        EntityManager manager = EMFUser.get().createEntityManager();
        try {
            HomePackage homePackage = new HomePackage();
            homePackage.setIdentify(identify);
            //todo drop this function
            Status status;
            status = validateFields(homePackage, GET_HOME);
            if (status != Status.OK) throw new MOHException(status.getMessage(), status.getCode());


            if (identify == null)
                throw new MOHException(Status.AUTH_ERROR.getMessage(), MOHException.STATUS_AUTH_ERROR);
            status = verifyIdentity(identify, manager);
            if (status != Status.OK) throw new MOHException(status.getMessage(), status.getCode());

            identify = (Identify) status.getResponse();


            home = manager.find(Home.class, id);

            //Set<UserRole> users = home.getUserRoles();
           // if (!users.contains(identify.getUser()))
              //  throw new MOHException("Auth Error", MOHException.STATUS_AUTH_ERROR);

            logger.info("Calling getHome method");
            if (home == null)
                throw new MOHException(Status.HOME_NOT_FOUND.getMessage(), MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);
        }finally {
            manager.close();
        }

        return home;
    }


    @ApiMethod(name = "findHome",path = "myhome")
    public Home findHome(@Named("id") long id, Identify identify) throws MOHException{

        EntityManager manager = EMFUser.get().createEntityManager();
        Home home;

        try {
            HomePackage homePackage = new HomePackage();
            homePackage.setIdentify(identify);
            //todo drop this function
            Status status;
            status = validateFields(homePackage, GET_HOME);
            if (status != Status.OK) throw new MOHException(status.getMessage(), status.getCode());


            if (identify == null)
                throw new MOHException(Status.AUTH_ERROR.getMessage(), MOHException.STATUS_AUTH_ERROR);
            status = verifyIdentity(identify, manager);
            if (status != Status.OK)
                throw new MOHException(status.getMessage(), status.getCode());

            identify = (Identify) status.getResponse();



            home = manager.find(Home.class, id);

            if(home==null) throw new MOHException("Home not found",MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);

            Set<UserRole> users = home.getUserRoles();
            String debug="";

            boolean flag = false;
            for(UserRole ur: users){
                flag=flag|| ur.getUser().equals(identify.getUser());
                debug+=ur.getUser().getUser_name()+",";
            }
            if (!flag)
                throw new MOHException("Auth Error "+debug, MOHException.STATUS_AUTH_ERROR);

            logger.info("Calling getHome method");

        }finally {
            manager.close();
        }


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

        Home home=null;
        EntityManager manager = EMFUser.get().createEntityManager();
        EntityManager invitationManager = EMFInvitation.get().createEntityManager();
        EntityManager productManager = EMFProduct.get().createEntityManager();


        try {


            Identify identify = homePackage.getIdentify();

            Status status;
            status = validateFields(homePackage, INSERT_HOME);
            if (status != Status.OK) throw new MOHException(status.getMessage(), status.getCode());


            if (identify == null)
                throw new MOHException(Status.AUTH_ERROR.getMessage(), MOHException.STATUS_AUTH_ERROR);
            status = verifyIdentity(identify, manager);
            if (status != Status.OK)
                throw new MOHException(status.getMessage(), status.getCode());

            identify = (Identify) status.getResponse();

            home = homePackage.getHome();


            Role role = new Role(Constants.CRole.ADMIN_ROLE);
            Set<Permission> ps = Permission.getAdminPermissions();
            Set<Permission> permissions = new HashSet<>();
            for (Permission p : ps) {
                Permission permission = manager.find(Permission.class, p.getId_permission());
                if (permission != null) permissions.add(permission);
            }


            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();

            User user = identify.getUser();

            home.setModification_date(new Date());
            home.setCreation_date(new Date());
            home.setCreator_id(user.getId_user());


            role.addPermissions(permissions);

            UserRole userRole = new UserRole();
            userRole.setModification_date(new Date());
            userRole.setStart_date(new Date());
            userRole.setUser(user);
            userRole.setRole(role);

            home.addUserRole(userRole);
            manager.persist(home);

            manager.flush();
            EntityTransaction transaction1 = invitationManager.getTransaction();
            EntityTransaction transaction2 = productManager.getTransaction();
            try {
                transaction1.begin();
                mx.ohanahome.app.backend.entity.invitation.Home home1 = new mx.ohanahome.app.backend.entity.invitation.Home(home);
                invitationManager.persist(home1);

                try {
                    transaction2.begin();
                    mx.ohanahome.app.backend.entity.product.Home home2 = new mx.ohanahome.app.backend.entity.product.Home(home);
                    productManager.persist(home2);
                    transaction2.commit();
                }catch (Exception e){
                    logger.log(Level.WARNING,"2:"+e.getMessage(),e.getCause());
                    transaction1.setRollbackOnly();
                    transaction.setRollbackOnly();
                    if(transaction2.isActive())
                        transaction2.rollback();
                }

                transaction1.commit();
            }catch (Exception e){
                e.printStackTrace();

                logger.log(Level.WARNING,"1:"+e.getMessage(),e.getCause());
                transaction.setRollbackOnly();
                if(transaction1.isActive())
                    transaction1.rollback();

            }

            transaction.commit();
        }finally {
            if(manager.getTransaction().isActive())
                manager.getTransaction().rollback();
            if(invitationManager.getTransaction().isActive())
                invitationManager.getTransaction().rollback();
            manager.close();
            invitationManager.close();
            productManager.close();
        }

        return home;
    }

    @ApiMethod(name = "updateHome")
    public Home updateHome(HomePackage homePackage) throws MOHException{

        Home home;
        EntityManager manager = EMFUser.get().createEntityManager();
        EntityManager invitationManager = EMFInvitation.get().createEntityManager();
        EntityManager productManager = EMFProduct.get().createEntityManager();

        try {
            Identify identify = homePackage.getIdentify();

            //todo permission?
            Status status;
            status = validateFields(homePackage, UPDATE_HOME);
            if (status != Status.OK) throw new MOHException(status.getMessage(), status.getCode());


            if (identify == null)
                throw new MOHException(Status.AUTH_ERROR.getMessage(), MOHException.STATUS_AUTH_ERROR);
            status = verifyIdentity(identify, manager);
            if (status != Status.OK)
                throw new MOHException(status.getMessage(), status.getCode());


            home = manager.find(Home.class, homePackage.getHome().getId_home());
            if (home == null)
                throw new MOHException(Status.HOME_NOT_FOUND.getMessage(), MOHException.STATUS_OBJECT_NOT_FOUND);

            EntityTransaction transaction = manager.getTransaction();
            transaction.begin();
            home.mergeValues(homePackage.getHome());
            home.setModification_date(new Date());

            manager.persist(home);

            EntityTransaction transaction1 = invitationManager.getTransaction();
            EntityTransaction transaction2 = productManager.getTransaction();
            manager.flush();
            try {
                transaction1.begin();
                mx.ohanahome.app.backend.entity.invitation.Home home1 = new mx.ohanahome.app.backend.entity.invitation.Home(home);
                invitationManager.persist(home1);

                try {
                    transaction2.begin();
                    mx.ohanahome.app.backend.entity.product.Home home2 = new mx.ohanahome.app.backend.entity.product.Home(home);
                    productManager.persist(home2);
                    transaction2.commit();
                }catch (Exception e){
                    logger.log(Level.WARNING,"2:"+e.getMessage(),e.getCause());
                    transaction1.setRollbackOnly();
                    transaction.setRollbackOnly();
                    if(transaction2.isActive())
                        transaction2.rollback();
                }

                transaction1.commit();
            }catch (Exception e){
                e.printStackTrace();

                logger.log(Level.WARNING, "1:" + e.getMessage(), e.getCause());
                transaction.setRollbackOnly();
                if(transaction1.isActive())
                    transaction1.rollback();

            }

            transaction.commit();
        }finally {
            manager.close();
            invitationManager.close();
            productManager.close();
        }

        return home;

    }

    private Status validateFields(HomePackage homePackage, int flag){
        if(false){
            return Status.NOT_ENOUGH_DATA;
        }
        return Status.OK;
    }
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