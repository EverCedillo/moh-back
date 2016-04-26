package mx.ohanahome.app.backend.entity.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by brenda on 4/17/16.
 */
@Table(name = "TOH_ROLE_PERMISSION")
@Entity
public class RolePermission {

    @Id
    private long id_role_permission;

    private long id_permission ;
    private long id_role;


    public long getId_role_permission() {
        return id_role_permission;
    }

    public void setId_role_permission(long id_role_permission) {
        this.id_role_permission = id_role_permission;
    }

    public long getId_permission() {
        return id_permission;
    }

    public void setId_permission(long id_permission) {
        this.id_permission = id_permission;
    }

    public long getId_role() {
        return id_role;
    }

    public void setId_role(long id_role) {
        this.id_role = id_role;
    }

    public RolePermission(){}

    @Override
    public boolean equals(Object obj) {
        return obj instanceof RolePermission && ((RolePermission)obj).getId_role_permission()==this.getId_role_permission();
    }
}
