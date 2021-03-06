package mx.ohanahome.app.backend.entity.user;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


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
    private Set<Home>homes;

    String store_name;
    String address;
    String aditional_information;

    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;

    public Store (String store_name, String address){
        this.store_name = store_name;
        this.address = address;
    }


    public void setId_store(long id_store) {
        this.id_store = id_store;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAditional_information(String aditional_information) {
        this.aditional_information = aditional_information;
    }

    public void setModification_date(Date modification_date) {
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

    public String getAditional_information() {
        return aditional_information;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public Store (){}

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Store && ((Store)obj).getId_store()==this.getId_store();
    }
}
