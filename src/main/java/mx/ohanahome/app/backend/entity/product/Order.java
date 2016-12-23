package mx.ohanahome.app.backend.entity.product;

import com.google.api.server.spi.response.CollectionResponse;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Date;
import java.util.SortedSet;

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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * Created by brenda on 5/1/16
 */
@NamedQueries(
        {
                @NamedQuery(name = "Order.getOrdersByHome", query = "select o from Order o where o.id_home = :id_home")
        }
)
@Table(name = "TOH_ORDER")
@Entity
public class Order {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_order")
    private  long id_order;


    private long id_home;


    String order_name;

    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;



    /*
    @OneToMany(mappedBy="shipment_order", fetch = FetchType.EAGER)
    private Set<Shipment> shipments;
    *


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name= "TOH_ORDER_PRODUCT",
            joinColumns=@JoinColumn(name="id_order"),
            inverseJoinColumns=@JoinColumn(name="product"))*

    @OneToMany(mappedBy="order", fetch = FetchType.EAGER)
    private Set<OrderProduct> products;*/

    @OneToMany(mappedBy="order_status", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrderStatus> orderStatuses;


    @OneToMany(mappedBy="order",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<CustomerOrder> customerOrders;


    /*
    @OneToMany(mappedBy="order",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts;*/




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


    /*
    public Set<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void addOrderProduct(Collection<OrderProduct> products){
        if(orderProducts==null)
            orderProducts = new HashSet<>();
        orderProducts.addAll(products);
    }
    public void setOrderProducts(Set<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }
    */


    public void addCustomerOrder(Collection<CustomerOrder> customers){
        if(customerOrders==null)
            customerOrders = new HashSet<>();
        customerOrders.addAll(customers);
    }


    public Set<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void addOrderStatus(OrderStatus orderStatus){
        if(orderStatuses==null)
            orderStatuses=new HashSet<>();
        orderStatuses.add(orderStatus);
    }

    public long getId_home() {
        return id_home;
    }

    public void setId_home(long id_home) {
        this.id_home = id_home;
    }

    public Set<OrderStatus> getOrderStatuses() {
        return orderStatuses;
    }


}

