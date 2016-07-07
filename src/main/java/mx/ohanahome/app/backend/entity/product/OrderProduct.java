package mx.ohanahome.app.backend.entity.product;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 5/1/16.
 */

@Table(name = "TOH_ORDER_PRODUCT")
@Entity
public class OrderProduct {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_order_product")
    private long id_order_product;

    private long id_order;
    private long id_product;


    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;


    @OneToMany(mappedBy="shipment_order", fetch = FetchType.EAGER)
    private Set<Shipment> shipments;


    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_customer")
    private Customer customer_product;

    @OneToMany(mappedBy="orderProduct")
    private Set<Shipment> shippments ;


    Integer quantity;
    double price;
    public OrderProduct (Integer quantity, double price){
        this.quantity = quantity;
        this.price = price;
    }

    public long getId_order_product() {
        return id_order_product;
    }

    public void setId_order_product(long id_order_product) {
        this.id_order_product = id_order_product;
    }

    public long getId_order() {
        return id_order;
    }

    public void setId_order(long id_order) {
        this.id_order = id_order;
    }

    public long getId_product() {
        return id_product;
    }

    public void setId_product(long id_product) {
        this.id_product = id_product;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
