package mx.ohanahome.app.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Created by brenda on 4/17/16.
 */
@Table(name = "TOH_ROLE")
@Entity
public class Role {
    @Id
    @Column(name="id_role")
    private long id_role;

    @ManyToMany(mappedBy="roles")
    private List<User> users;

    @ManyToMany
    @JoinTable(
            name="Role_Permission",
            joinColumns=@JoinColumn(name="id_role", referencedColumnName="id_role"),
            inverseJoinColumns=@JoinColumn(name="id_permission", referencedColumnName="id_permission"))
    private List<Permission> permissions;


    String role_name;

    public long getId_role() {
        return id_role;
    }

    public void setId_role(long id_role) {
        this.id_role = id_role;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Role(){}
}

