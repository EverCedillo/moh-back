package mx.ohanahome.app.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by brenda on 4/3/16.
 */
@Table(name = "TOH_USER_ILLNESS")
@Entity
public class UserIllness {
    @Id
    long id_user_illness;
    long id_user;
    long id_illness;
    String creation_date;
    String modification_date;

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public void setModification_date(String modification_date) {
        this.modification_date = modification_date;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getModification_date() {
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
