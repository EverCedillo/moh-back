package mx.ohanahome.app.backend;


import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.util.List;
import java.util.logging.Logger;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.Permission;
import mx.ohanahome.app.backend.entity.user.Role;
import mx.ohanahome.app.backend.entity.user.User;
import mx.ohanahome.app.backend.model.PermissionPackage;
import mx.ohanahome.app.backend.util.Constants;
import mx.ohanahome.app.backend.util.EMFUser;
import mx.ohanahome.app.backend.util.MOHException;

/**
 * An endpoint class we are exposing
 */
@Api(
        name = "permissionApi",
        version = "v1",
        resource = "permission",
        namespace = @ApiNamespace(
                ownerDomain = "backend.app.ohanahome.mx",
                ownerName = "backend.app.ohanahome.mx",
                packagePath = ""
        )
)
public class PermissionEndpoint {

    private static final Logger logger = Logger.getLogger(PermissionEndpoint.class.getName());

    /**
     * This method gets the <code>Permission</code> object associated with the specified <code>id</code>.
     *
     * @param id The id of the object to be returned.
     * @return The <code>Permission</code> associated with <code>id</code>.
     */
    @ApiMethod(name = "getPermission")
    public Permission getPermission(@Named("id") Long id) {
        // TODO: Implement this function
        logger.info("Calling getPermission method");
        return null;
    }

    /**
     * This inserts a new <code>Permission</code> object.
     *
     * @param permissionPackage The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "grantPermission", httpMethod = ApiMethod.HttpMethod.POST)
    public Role grantPermission(PermissionPackage permissionPackage)throws MOHException{
        // TODO: Implement this function
        Status status;
        status=validateFields(permissionPackage);
        Role role;
        if(status!=Status.STATUS_OK) throw new MOHException(status.getMessage(),status.getCode());
        EntityManager manager = EMFUser.get().createEntityManager();
        try {
            status = verifyIdentities(permissionPackage, manager, Constants.CPermission.Admin.GRANT_PERMISSION);
            if (status != Status.STATUS_OK)
                throw new MOHException(status.getMessage(), status.getCode());

            role = (Role) status.getResponse();

            manager.getTransaction().begin();
            role.addPermissions(permissionPackage.getPermissions());
            manager.persist(role);
        }finally {
            manager.close();
        }

        logger.info("Calling insertPermission method");
        return role;
    }

    @ApiMethod(name = "revokePermission", httpMethod = ApiMethod.HttpMethod.DELETE)
    public Role revokePermission(PermissionPackage permissionPackage) throws MOHException{
        Status status;
        Role role;
        status=validateFields(permissionPackage);
        if(status!=Status.STATUS_OK) throw new MOHException(status.getMessage(),status.getCode());
        EntityManager manager = EMFUser.get().createEntityManager();

        //Todo if i remove an item from a collection it is removed from the database too?

        try {

            status = verifyIdentities(permissionPackage, manager, Constants.CPermission.Admin.REVOKE_PERMISSION);
            if (status != Status.STATUS_OK)
                throw new MOHException(status.getMessage(), status.getCode());

            role = (Role) status.getResponse();


            manager.getTransaction().begin();
            role.removePermissions(permissionPackage.getPermissions());
            manager.persist(role);
            manager.getTransaction().commit();

        }finally {
            manager.close();
        }
        return role;
    }

    Status verifyIdentities(PermissionPackage permissionPackage,EntityManager manager, Permission permission){
        Identify whoGrant=permissionPackage.getGrants();
        Identify recipient= permissionPackage.getRecipient();

        User user = whoGrant.getUser();

        permission=manager.find(Permission.class,permission.getId_permission());


        TypedQuery<Role> query = manager.createNamedQuery("UserRole.findRole", Role.class);
        query.setParameter(Constants.CUserRole.HOME, permissionPackage.getHome());
        query.setParameter(Constants.CUserRole.USER, user);
        List<Role> roles = query.getResultList();
        Role role= roles.isEmpty()?null:roles.get(0);
        if(role==null) return Status.STATUS_AUTH_ERROR;
        if(!role.getPermissions().contains(permission)) return Status.STATUS_OBJECT_NOT_ACCESSIBLE;


        TypedQuery<Identify> query1=manager.createQuery("Identify.verifyIdentity", Identify.class);
        query1.setParameter(Constants.CIdentity.ID_ADAPTER,recipient.getId_adapter());
        query1.setParameter(Constants.CIdentity.ADAPTER,recipient.getAdapter());
        List<Identify> list = query1.getResultList();
        user=list.isEmpty()?null:list.get(0).getUser();

        if(user==null) return Status.STATUS_OBJECT_NOT_ACCESSIBLE;
        query = manager.createNamedQuery("UserRole.findRole",Role.class);
        query.setParameter(Constants.CUserRole.USER,user);
        query.setParameter(Constants.CUserRole.HOME, permissionPackage.getHome());
        List<Role> roleList= query.getResultList();
        Role role1 = roleList.isEmpty()?null:roleList.get(0);
        if(role1== null) return Status.STATUS_OBJECT_NOT_ACCESSIBLE;
        return Status.STATUS_OK.withResponse(role);
    }

    Status validateFields(PermissionPackage permissionPackage){
        if(false){
            return Status.STATUS_NOT_ENOUGH_DATA;
        }
        return Status.STATUS_OK;
    }
    enum Status{
        STATUS_OK("Ok",-1),
        STATUS_NOT_ENOUGH_DATA("Missing fields", MOHException.STATUS_NOT_ENOUGH_DATA),
        STATUS_AUTH_ERROR("Auth error", MOHException.STATUS_AUTH_ERROR),
        STATUS_OBJECT_NOT_ACCESSIBLE("The user does not have permission to access this or the object is not accesible",MOHException.STATUS_OBJECT_NOT_ACCESSIBLE);

        Status(String message, int code) {
            this.message = message;
            this.code = code;
        }
//fdqcpTu5_EM:APA91bFmxQ30ZkICfpK8h1ZxBT8QarwibG4ar4j5W3dTnaVQ1S_EYDBwG3MVbJIMkm-_NfUXDf2Qp823bkUFx52rdD8nglqOzYbL0n2lrDMdukHoq-YdVMvJOkI3Tpoh8NMvVTe7ez0c
//eR-AhGKEh74:APA91bFs1U_dqcaU6ZeRIUvfmm1DRqxck1z7jIrbntmJozi1H4Y2hVjsOXb29IuyQy1VyurbWSJS9ZTg9CTQKdw9KrC_mpoR0Omj9QWbpKVtGkLvj-dQr6SDsMkNa2ZV0y6WlbvHSl6B
        String message;
        int code;
        private Object response;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public Status withResponse(Object object){
            this.response=object;
            return this;
        }

        public Object getResponse() {
            return response;
        }
    }
}