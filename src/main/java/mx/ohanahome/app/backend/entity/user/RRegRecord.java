package mx.ohanahome.app.backend.entity.user;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by ever on 22/12/16.
 */

@Table(name = "TOH_REGISTRATION_DEVICE")
@Entity
public class RRegRecord {

    @GeneratedValue(generator = "increment")
    @Id
    long id_token;



    long id_user;




    String token;


    public long getId_token() {
        return id_token;
    }

    public void setId_token(long id_token) {
        this.id_token = id_token;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public RRegRecord(){}



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
