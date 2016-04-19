package mx.ohanahome.app.backend.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 4/3/16.
 */
@Table(name = "TOH_USER_ILLNESS")
@Entity
public class UserIllness {
    @Id
    private long id_user_illness;
    @Id
    private long id_user;
    @Id
    private long id_illness;

    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public void setId_user_illness(long id_user_illness) {
        this.id_user_illness = id_user_illness;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public void setId_illness(long id_illness) {
        this.id_illness = id_illness;
    }


    public long getId_user_illness() {
        return id_user_illness;
    }

    public long getId_user() {
        return id_user;
    }

    public long getId_illness() {
        return id_illness;
    }

    public UserIllness(){}

}
