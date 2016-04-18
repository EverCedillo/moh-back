package mx.ohanahome.app.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Created by brenda on 4/3/16.
 */
@Table(name = "TOH_STORE")
@Entity
public class Store {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_store")
    private long id_store;

    @ManyToMany(mappedBy= "stores")
    private List<Home>homes;

    String store_name;
    String address;
    String aditional_informat;
    String modification_date;

    public void setId_store(long id_store) {
        this.id_store = id_store;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAditional_informat(String aditional_informat) {
        this.aditional_informat = aditional_informat;
    }

    public void setModification_date(String modification_date) {
        this.modification_date = modification_date;
    }

    public long getId_store() {
        return id_store;
    }

    public String getStore_name() {
        return store_name;
    }

    public String getAddress() {
        return address;
    }

    public String getAditional_informat() {
        return aditional_informat;
    }

    public String getModification_date() {
        return modification_date;
    }

    public Store (){}
}
