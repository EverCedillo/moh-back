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
@Table(name = "TOH_HOME_STORE")
@Entity
public class HomeStore {
    @Id
    long id_home_store;
    long id_home;
    long id_store;

    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;

    public void setId_home_store(long id_home_store) {
        this.id_home_store = id_home_store;
    }

    public void setId_home(long id_home) {
        this.id_home = id_home;
    }

    public void setId_store(long id_store) {
        this.id_store = id_store;
    }

    public long getId_home_store() {
        return id_home_store;
    }

    public long getId_home() {
        return id_home;
    }

    public long getId_store() {
        return id_store;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public HomeStore(){}
}
