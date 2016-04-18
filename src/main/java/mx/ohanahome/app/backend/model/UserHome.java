package mx.ohanahome.app.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by brenda on 4/3/16.
 */
@Table(name = "TOH_USER_HOME")
@Entity
public class UserHome {
    @Id
    long id_user_home;
    long id_user;
    long id_home;

    public void setId_user_home(long id_user_home) {
        this.id_user_home = id_user_home;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public void setId_home(long id_home) {
        this.id_home = id_home;
    }

    public long getId_user_home() {
        return id_user_home;
    }

    public long getId_user() {
        return id_user;
    }

    public long getId_home() {
        return id_home;
    }

    public UserHome(){

    }
}
