package mx.ohanahome.app.backend.entity.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by brenda on 4/27/16.
 */

@Table(name = "TOH_STORE_PRODUCT")
@Entity
public class StoreProduct {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name = "id_store_product")
    private long id_store_product;

    private long id_product;
    private  long id_store;

    public long getId_store_product() {
        return id_store_product;
    }

    public void setId_store_product(long id_store_product) {
        this.id_store_product = id_store_product;
    }

    public long getId_product() {
        return id_product;
    }

    public void setId_product(long id_product) {
        this.id_product = id_product;
    }

    public long getId_store() {
        return id_store;
    }

    public void setId_store(long id_store) {
        this.id_store = id_store;
    }

    public StoreProduct(){

    }
}
