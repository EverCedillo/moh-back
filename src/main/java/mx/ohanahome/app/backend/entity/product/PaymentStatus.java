
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
@Table(name = "TOH_PAYMENT_STATUS")
@Entity
public class PaymentStatus {

    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_payment_status")
    private long  id_payment_status;


    @Temporal(TemporalType.TIMESTAMP)
    Date date_payment_status;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_order")
    private Order payment_status;

    String status_name;

    public PaymentStatus(String status_name){
        this.status_name = status_name;

    }

    public long getId_payment_status() {
        return id_payment_status;
    }

    public void setId_payment_status(long id_order_status) {
        this.id_payment_status = id_order_status;
    }

    public Date getDate_payment_status() {
        return date_payment_status;
    }

    public void setDate_payment_status(Date date_payment_status) {
        this.date_payment_status = date_payment_status;
    }

    public Order getPayment_status() {
        return payment_status
    }

    public void setPayment_status(Order order_status) {
        this.order_status = order_status;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
