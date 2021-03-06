package mx.ohanahome.app.backend.entity.user;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by brenda on 4/17/16.
 */
@Table(name = "TOH_ROLE")
@Entity
public class Role {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_role")
    private long id_role;

    @OneToMany(mappedBy="role", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    @ManyToMany( fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name="TOH_ROLE_PERMISSION",
            joinColumns=@JoinColumn(name="id_role", referencedColumnName="id_role"),
            inverseJoinColumns=@JoinColumn(name="id_permission", referencedColumnName="id_permission"))
    private Set<Permission> permissions;


    String role_name;


    public Role(String role_name) {
        this.role_name = role_name;
    }

    public long getId_role() {
        return id_role;
    }

    public void setId_role(long id_role) {
        this.id_role = id_role;
    }



    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Role(){}


    @JsonIgnore
    public void addUserRole(UserRole userRole){
        if(userRoles==null)
            userRoles=new HashSet<>();
        userRoles.add(userRole);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Role && ((Role)obj).getId_role()==this.getId_role();
    }



    @JsonIgnore
    public void addPermissions(Collection<Permission> permission){
        if(permissions==null)permissions=new HashSet<>();
        permissions.addAll(permission);
    }

    @JsonIgnore
    public void removePermissions(Collection<Permission> permissions){
        this.permissions.removeAll(permissions);
    }


}

