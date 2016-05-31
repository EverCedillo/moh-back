package mx.ohanahome.app.backend.entity.user;

;import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "TOH_REGISTRATION_DEVICE")
@Entity
public class RegistrationRecord {

    @GeneratedValue(generator = "increment")
    @Id
    long id_token;



    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_user")
    User user;


    String token;


    public long getId_token() {
        return id_token;
    }

    public void setId_token(long id_token) {
        this.id_token = id_token;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public RegistrationRecord(){}

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}