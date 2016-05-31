package mx.ohanahome.app.backend.entity.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Created by brenda on 5/1/16.
 */
@Table(name = "TOH_ORDER_STATUS")
@Entity
public class OrderStatus {

    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_order_status")
    private long  id_order_status;


    @Temporal(TemporalType.TIMESTAMP)
    Date date_order_status;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_order")
    private Order order_status;

    String status_name;

    public OrderStatus(String status_name){
        this.status_name = status_name;

    }

    public long getId_order_status() {
        return id_order_status;
    }

    public void setId_order_status(long id_order_status) {
        this.id_order_status = id_order_status;
    }

    public Date getDate_order_status() {
        return date_order_status;
    }

    public void setDate_order_status(Date date_order_status) {
        this.date_order_status = date_order_status;
    }

    public Order getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Order order_status) {
        this.order_status = order_status;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
