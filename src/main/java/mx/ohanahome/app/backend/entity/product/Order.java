package mx.ohanahome.app.backend.entity.product;

import java.util.List;
import java.util.Set;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * Created by brenda on 5/1/16.
 */
@Table(name = "TOH_ORDER")
@Entity
public class Order {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_order")
    private  long id_order;


    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_home")
    private Home home_order;


    String order_name;

    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;



    /*
    @OneToMany(mappedBy="shipment_order", fetch = FetchType.EAGER)
    private Set<Shipment> shipments;
    */

    @OneToMany(mappedBy="order_status", fetch = FetchType.EAGER)
    private Set<OrderStatus> orderStatuses;


    @OneToMany(mappedBy="order",fetch = FetchType.EAGER)
    private Set<CustomerHome> customerOrders;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name= "TOH_ORDER_PRODUCT",
            joinColumns=@JoinColumn(name="id_order"),
            inverseJoinColumns=@JoinColumn(name="product"))
    private Set<Product> products;


    @OneToMany(mappedBy="order",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts;




    public Order(String order_name){
        this.order_name = order_name;

    }

    public long getId_order() {
        return id_order;
    }

    public void setId_order(long id_order) {
        this.id_order = id_order;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }


    public String getOrder_name() {
        return order_name;
    }

    public void setOrder_name(String order_name) {
        this.order_name = order_name;
    }

    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void addOrderProduct(List<OrderProduct> products){
        orderProducts.addAll(products);
    }

    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
}
