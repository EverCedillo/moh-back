package mx.ohanahome.app.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    private List<Role> roles;

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

}
