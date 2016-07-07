package mx.ohanahome.app.backend.entity.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 5/1/16.
 */
@Table(name = "TOH_PAYMENT")
@Entity
public class Payment {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name = "id_payment")
    private  long id_payment;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_shipment")
    private Shipment shipment_payment;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_customer")
    private Customer customer_payment;

    @Temporal(TemporalType.TIMESTAMP)
    Date payment_date;



    double payment_amount;
    String payment_method;

    public Payment (String payment_method, double payment_amount){
        this.payment_method = payment_method;
        this.payment_amount = payment_amount;
    }

    public long getId_payment() {
        return id_payment;
    }

    public void setId_payment(long id_payment) {
        this.id_payment = id_payment;
    }

    public Date getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(Date payment_date) {
        this.payment_date = payment_date;
    }

    public double getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(double payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }
}
