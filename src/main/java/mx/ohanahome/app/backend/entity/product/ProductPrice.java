package mx.ohanahome.app.backend.entity.product;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.ohanahome.app.backend.entity.user.Home;

/**
 * Created by brenda on 4/27/16.
 */
@Table(name = "TOH_PRODUCT_PRICE")
@Entity
public class ProductPrice {
    @Id
    private long id_product_price;

    @Temporal(TemporalType.TIMESTAMP)
    Date date_from;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_product")
    private Product product_prices;

    double price;
    double discount;


    public ProductPrice(double price, double discount){
        this.price = price;
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getId_product_price() {
        return id_product_price;
    }

    public void setId_product_price(long id_product_price) {
        this.id_product_price = id_product_price;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ProductPrice && ((ProductPrice)obj).getId_product_price()==this.getId_product_price();
    }


}
