package mx.ohanahome.app.backend.entity.user;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 4/3/16.
 */
@NamedQuery(name = "UserRole.findRole", query = "Select u from Role u, UserRole r where u=r.role and r.home=:home and r.user=:user")
@Table(name = "TOH_USER_ROLE")
@Entity
public class UserRole {

    @GeneratedValue(generator = "increment")
    @Id
    private long id_user_role;


    @Temporal(TemporalType.TIMESTAMP)
    Date start_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date end_date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_role")
    private Role role;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_home")
    private Home home;




    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnore
    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public long getId_user_role() {
        return id_user_role;
    }

    public void setId_user_role(long id_user_role) {
        this.id_user_role = id_user_role;
    }


    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }


    public UserRole(){}
}
