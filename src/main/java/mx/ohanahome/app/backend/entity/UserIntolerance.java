package mx.ohanahome.app.backend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by brenda on 4/3/16.
 */
@Table(name = "TOH_USER_INTOLERANCE")
@Entity
public class UserIntolerance {
    @Id
    long id_user_intolerance;
    long id_intolerance;
    long id_user;
    String creation_date;

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public void setModification_date(String modification_date) {
        this.modification_date = modification_date;
    }

    String modification_date;

    public String getCreation_date() {
        return creation_date;
    }

    public String getModification_date() {
        return modification_date;
    }

    public void setId_user_intolerance(long id_user_intolerance) {
        this.id_user_intolerance = id_user_intolerance;
    }

    public void setId_intolerance(long id_intolerance) {
        this.id_intolerance = id_intolerance;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }


    public long getId_user_intolerance() {
        return id_user_intolerance;
    }

    public long getId_intolerance() {
        return id_intolerance;
    }

    public long getId_user() {
        return id_user;
    }
    public UserIntolerance(){}

}
