package mx.ohanahome.app.backend.entity.product;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import mx.ohanahome.app.backend.entity.user.Home;

/**
 * Created by brenda on 4/27/16.
 */

@Table(name = "TOH_STORE")
@Entity
public class Store {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name = "id_store")
    private long id_store;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name= "TOH_STORE_PRODUCT",
            joinColumns=@JoinColumn(name="id_store"),
            inverseJoinColumns=@JoinColumn(name="id_product"))
    private Set<Product> products;

    @OneToMany(mappedBy="stores")
    private Set<Branch> branches ;


    String store_name;
    String aditional_information;


    public Store(String store_name){
        this.store_name= store_name;
    }

    public long getId_store() {
        return id_store;
    }

    public void setId_store(long id_store) {
        this.id_store = id_store;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getAditional_information() {
        return aditional_information;
    }

    public void setAditional_information(String aditional_information) {
        this.aditional_information = aditional_information;
    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof Store && ((Store)obj).getId_store()==this.getId_store();
    }
}
