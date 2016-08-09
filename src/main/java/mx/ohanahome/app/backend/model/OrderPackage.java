package mx.ohanahome.app.backend.model;

import java.util.List;

import mx.ohanahome.app.backend.entity.product.Customer;
import mx.ohanahome.app.backend.entity.product.CustomerOrder;
import mx.ohanahome.app.backend.entity.product.Order;
import mx.ohanahome.app.backend.entity.product.OrderProduct;
import mx.ohanahome.app.backend.entity.user.Identify;

/**
 * Created by ever on 12/07/16.
 */
public class OrderPackage {
    Identify identify;
    Order order;
    OrderProduct orderProduct;
    List<Customer> customerList;
    Customer owner;
    CustomerOrder guest;

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public Identify getIdentify() {
        return identify;
    }

    public void setIdentify(Identify identify) {
        this.identify = identify;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public CustomerOrder getGuest() {
        return guest;
    }

    public void setGuest(CustomerOrder guest) {
        this.guest = guest;
    }
}
