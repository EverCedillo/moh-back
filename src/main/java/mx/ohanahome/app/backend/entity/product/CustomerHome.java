package mx.ohanahome.app.backend.entity.product;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 5/1/16.
 */
@Table(name = "TOH_CUSTOMER_HOME")
@Entity
public class CustomerHome {

    @GeneratedValue(generator = "increment")
    @Column(name="id_customer_home")
    @Id
    private  long id_customer_home;

    
    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date = new Date();

    private long id_user;
    private long id_customer;

    public long getId_customer_home() {
        return id_customer_home;
    }

    public void setId_customer_home(long id_customer_home) {
        this.id_customer_home = id_customer_home;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }
}
