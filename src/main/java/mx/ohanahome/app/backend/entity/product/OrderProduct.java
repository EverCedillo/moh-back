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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.ohanahome.app.backend.entity.user.*;

/**
 * Created by brenda on 5/1/16
 */

@NamedQueries(
        {@NamedQuery(name = "OrderProduct.getProductsByCustomerAndOrder", query = "select op from OrderProduct op where id_customer = :id_customer and id_order = :id_order"),
        @NamedQuery(name = "OrderProduct.removeOrderProduct", query = "delete from OrderProduct op where op.id_order_product = :id_order_product"),
        @NamedQuery(name = "OrderProduct.removeOPByCustomerNOrder", query = "delete from OrderProduct op where op.id_customer =:id_customer and op.id_order = :id_order")}
)
@Table(name = "TOH_ORDER_PRODUCT")
@Entity
public class OrderProduct {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_order_product")
    private long id_order_product;


    @OneToOne
    @JoinColumn(name = "id_product")
    Product product;

    private long id_customer;
    private long id_order;



    @OneToMany(mappedBy="orderProduct", fetch = FetchType.EAGER)
    private Set<Shipment> shipments;


    Integer quantity;
    double price;

    String product_type;
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

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    public long getId_order() {
        return id_order;
    }

    public void setId_order(long id_order) {
        this.id_order = id_order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProduct_type() {
        return product_type;
    }

    public void setProduct_type(String product_type) {
        this.product_type = product_type;
    }

    public OrderProduct(){}
}
