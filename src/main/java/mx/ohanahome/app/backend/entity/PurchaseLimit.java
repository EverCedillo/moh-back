package mx.ohanahome.app.backend.entity;


import java.util.Date;
import java.util.List;

import javax.annotation.Generated;

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
 * Created by brenda on 4/3/16.
 */
@Table(name = "TOH_PURCHASE_LIMIT")
@Entity
public class PurchaseLimit {
    @GeneratedValue(generator = "increment")
    @Id
    private long id_purchase_limit;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="id_user")
    private User user;

    double start_amount;
    double actual_amount;
    String notification;
    String period_type;
    String renovation;

    @Temporal(TemporalType.TIMESTAMP)
    Date end_limit_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date start_limit_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;

    public PurchaseLimit(double start_amount, double actual_amount, String notification, String period_type, String renovation){
        this.start_amount = start_amount;
        this.actual_amount = actual_amount;
        this. notification = notification;
        this.period_type = period_type;
        this.renovation = renovation;
    }

    public void setId_purchase_limit(long id_purchase_limit) {
        this.id_purchase_limit = id_purchase_limit;
    }



    public void setStart_amount(double start_amount) {
        this.start_amount = start_amount;
    }

    public void setStart_limit_date(Date start_limit_date) {
        this.start_limit_date = start_limit_date;
    }

    public void setEnd_limit_date(Date end_limit_date) {
        this.end_limit_date = end_limit_date;
    }

    public void setActual_amount(double actual_amount) {
        this.actual_amount = actual_amount;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public void setPeriod_type(String period_type) {
        this.period_type = period_type;
    }

    public long getId_purchase_limit() {
        return id_purchase_limit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getStart_amount() {
        return start_amount;
    }

    public Date getStart_limit_date() {
        return start_limit_date;
    }

    public Date getEnd_limit_date() {
        return end_limit_date;
    }

    public double getActual_amount() {
        return actual_amount;
    }

    public String getNotification() {
        return notification;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public String getPeriod_type() {
        return period_type;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }

    public String getRenovation() {
        return renovation;
    }

    public void setRenovation(String renovation) {
        this.renovation = renovation;
    }

    public PurchaseLimit(){}
}
