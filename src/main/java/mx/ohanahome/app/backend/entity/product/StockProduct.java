package mx.ohanahome.app.backend.entity.product;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Created by ever on 11/10/16
 */
@NamedQueries(
        {
                @NamedQuery(name = "StockProduct.getDetails" , query = "select sd from StockDetail sd where sd.id_stock_product = :id_stock_product order by sd.stock_date desc")
        }
)
@Table(name = "TOH_STOCK_PRODUCT")
@Entity
public class StockProduct {

    @GeneratedValue(generator = "increment")
    @Id
    long id_stock_product;

    long id_customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_stock")
    Stock stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product")
    Product product;

    int stock_quantity;

    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;


    String product_type;

    @Transient
    String type;

    public StockProduct(){}


    public long getId_stock_product() {
        return id_stock_product;
    }

    public long getId_customer() {
        return id_customer;
    }

    public Stock getStock() {
        return stock;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    @Transient
    public String getType() {
        return type;
    }

    public void setId_stock_product(long id_stock_product) {
        this.id_stock_product = id_stock_product;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }


    public Product getProduct() {
        return product;
    }

    @Transient
    public void setType(String type) {
        this.type = type;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }
}
