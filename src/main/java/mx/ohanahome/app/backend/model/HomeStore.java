package mx.ohanahome.app.backend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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

    public HomeStore(){}
}
