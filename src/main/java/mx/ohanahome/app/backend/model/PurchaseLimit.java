package mx.ohanahome.app.backend.model;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by brenda on 4/3/16.
 */
@Table(name = "TOH_PURCHASE_LIMIT")
@Entity
public class PurchaseLimit {
    @GeneratedValue(generator = "increment")
    @Id
    private long id_purchase_limit;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="id_purchase_limit")
    private User purchase_limit;

    long id_user;
    double start_amount;
    String start_limit_date;
    String end_limit_date;
    double actual_amount;
    String notification;
    String creation_date;
    String period_type;

    public void setId_purchase_limit(long id_purchase_limit) {
        this.id_purchase_limit = id_purchase_limit;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public void setStart_amount(double start_amount) {
        this.start_amount = start_amount;
    }

    public void setStart_limit_date(String start_limit_date) {
        this.start_limit_date = start_limit_date;
    }

    public void setEnd_limit_date(String end_limit_date) {
        this.end_limit_date = end_limit_date;
    }

    public void setActual_amount(double actual_amount) {
        this.actual_amount = actual_amount;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public void setPeriod_type(String period_type) {
        this.period_type = period_type;
    }

    public long getId_purchase_limit() {
        return id_purchase_limit;
    }

    public long getId_user() {
        return id_user;
    }

    public double getStart_amount() {
        return start_amount;
    }

    public String getStart_limit_date() {
        return start_limit_date;
    }

    public String getEnd_limit_date() {
        return end_limit_date;
    }

    public double getActual_amount() {
        return actual_amount;
    }

    public String getNotification() {
        return notification;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getPeriod_type() {
        return period_type;
    }

    public PurchaseLimit(){}
}
