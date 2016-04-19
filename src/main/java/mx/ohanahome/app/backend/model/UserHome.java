package mx.ohanahome.app.backend.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 4/3/16.
 */
@Table(name = "TOH_USER_HOME")
@Entity
public class UserHome {
    @Id
    private long id_user_home;
    @Id
    private long id_user;
    @Id
    private long id_home;

    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;

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

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public UserHome(){

    }

}
