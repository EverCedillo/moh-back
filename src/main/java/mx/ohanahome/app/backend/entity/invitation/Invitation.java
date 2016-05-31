package mx.ohanahome.app.backend.entity.invitation;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by ever on 29/04/16.
 */
@Entity
@Table(name = "TOH_INVITATION")
public class Invitation {

    @Id
    @GeneratedValue (generator = "increment")
    private long id_invitation;


    String email_to;
    String status;

    @Temporal(value = TemporalType.TIMESTAMP)
    Date expiration_date;

    @ManyToOne(fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_home")
    Home home;

    String sender_name;

    public Invitation(){}



    public String getEmail_to() {
        return email_to;
    }

    public void setEmail_to(String email_to) {
        this.email_to = email_to;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(Date expiration_date) {
        this.expiration_date = expiration_date;
    }


    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public long getId_invitation() {
        return id_invitation;
    }
}
