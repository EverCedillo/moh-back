package mx.ohanahome.app.backend.entity.user;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import static mx.ohanahome.app.backend.util.Constants.CPermission.Admin.*;
import static mx.ohanahome.app.backend.util.Constants.CPermission.Normal.*;

/**
 * Created by brenda on 4/17/16.
 */
@Table(name = "TOH_PERMISSION")
@Entity
public class Permission {

    @Id
    @Column(name = "id_permission")
    private long id_permission;

    @ManyToMany(mappedBy="permissions")
    private Set<Role> roles;

    String permission_name;

    public Permission (String permission_name){
        this.permission_name = permission_name;

    }



    public void setId_permission(long id_permission) {
        this.id_permission = id_permission;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    public long getId_permission() {
        return id_permission;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public Permission (){
    }

    public Permission(long id_permission, String permission_name) {
        this.id_permission = id_permission;
        this.permission_name = permission_name;
    }

    @JsonIgnore
    public static Set<Permission> getNormalPermissions(){
        Set<Permission> permissions = new HashSet<>();
        permissions.add(SEND_INVITATION_PERMISSION);
        permissions.add(ADD_PAYMENT_PERMISSION);
        permissions.add(CREATE_ORDER_PERMISSION);
        permissions.add(MODIFY_ORDER_PERMISSION);
        permissions.add(INVITE_GUEST_SHOP_PERMISSION);
        permissions.add(ASIGN_PRODUCT_PERMISSION);
        permissions.add(REMOVE_GUEST_SHOP_PERMISSION);
        permissions.add(ADD_STOCK_ITEM_PERMISSION);
        permissions.add(UPDATE_STOCK_ITEM_PERMISSION);
        return permissions;
    }

    @JsonIgnore
    public static Set<Permission> getAdminPermissions(){
        Set<Permission> permissions = new HashSet<>();
        permissions.add(MODIFY_HOME_PERMISSION);
        permissions.add(ADD_ADMIN_PERMISSION);
        permissions.add(REMOVE_ADMIN_PERMISSION);
        permissions.add(QUIT_AS_ADMIN_PERMISSION);
        permissions.add(ADD_GUEST_PERMISSION);
        permissions.add(DELETE_HOME_PERMISSION);
        permissions.add(GRANT_PERMISSION);
        permissions.add(REVOKE_PERMISSION);

        //Normal
        permissions.add(SEND_INVITATION_PERMISSION);
        permissions.add(ADD_PAYMENT_PERMISSION);
        permissions.add(CREATE_ORDER_PERMISSION);
        permissions.add(MODIFY_ORDER_PERMISSION);
        permissions.add(INVITE_GUEST_SHOP_PERMISSION);
        permissions.add(ASIGN_PRODUCT_PERMISSION);
        permissions.add(REMOVE_GUEST_SHOP_PERMISSION);
        permissions.add(ADD_STOCK_ITEM_PERMISSION);
        permissions.add(UPDATE_STOCK_ITEM_PERMISSION);
        return permissions;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Permission && ((Permission)obj).getId_permission()==this.getId_permission();
    }
}

