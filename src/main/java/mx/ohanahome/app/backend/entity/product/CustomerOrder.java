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

import mx.ohanahome.app.backend.entity.user.*;
/**
 * Created by brenda on 7/13/16.
 */



@Table(name = "TOH_CUSTOMER_ORDER")
@Entity
public class CustomerOrder {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_customer_order")
    private long id_customer_order;


    @Temporal(TemporalType.TIMESTAMP)
    Date status_date;

    String order_status;
    String customer_rol;

    public CustomerOrder (String order_status, String customer_rol) {
        this.order_status = order_status;
        this.customer_rol= customer_rol;
    }

    public long getId_customer_order() {
        return id_customer_order;
    }

    public void setId_customer_order(long id_customer_order) {
        this.id_customer_order = id_customer_order;
    }

    public Date getStatus_date() {
        return status_date;
    }

    public void setStatus_date(Date status_date) {
        this.status_date = status_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getCustomer_rol() {
        return customer_rol;
    }

    public void setCustomer_rol(String customer_rol) {
        this.customer_rol = customer_rol;
    }
}
