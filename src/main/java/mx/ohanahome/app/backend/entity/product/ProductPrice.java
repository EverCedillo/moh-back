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
public class ProductPrice implements Comparable{
    @Id
    private long id_product_price;

    @Temporal(TemporalType.TIMESTAMP)
    Date date_from;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_product")
    private Product product;

    Double price;
    Double discount;


    public ProductPrice(Double price, Double discount){
        this.price = price;
        this.discount = discount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date date_from) {
        this.date_from = date_from;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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


    @Override
    public int compareTo(Object o) {
        ProductPrice p;
        if(!( o instanceof ProductPrice))
            return 0;
        p=(ProductPrice)o;
        if(p.getId_product_price()==this.getId_product_price())
            return 0;
        if(p.getId_product_price()>this.getId_product_price())
            return 1;
        return -1;
    }
}
