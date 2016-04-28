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

import static mx.ohanahome.app.backend.util.Constants.CPermission.*;

/**
 * Created by brenda on 4/17/16.
 */
@Table(name = "TOH_PERMISSION")
@Entity
public class Permission {
    @GeneratedValue(generator = "increment")
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

    @JsonIgnore
    public static Set<Permission> getNormalPermissions(){
        Set<Permission> permissions = new HashSet<>();
        permissions.add(new Permission(SHOP_PERMISSION));
        return permissions;
    }

    @JsonIgnore
    public static Set<Permission> getAdminPermissions(){
        Set<Permission> permissions = new HashSet<>();
        permissions.add(new Permission(GRANT_PERMISSION));
        permissions.add(new Permission(REVOKE_PERMISSION));
        return permissions;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Permission && ((Permission)obj).getPermission_name().equals(this.getPermission_name());
    }
}
