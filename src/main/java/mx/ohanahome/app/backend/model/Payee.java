package mx.ohanahome.app.backend.model;
//import com.googlecode.objectify.annotation.Entity;
//import com.googlecode.objectify.annotation.Id;

import java.util.List;
import java.util.Date;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 4/3/16.
 */
@Table(name = "TOH_PAYEE")
@Entity
public class Payee {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_payee")
    private long id_payee;

    @ManyToMany(mappedBy="payees")
    private List<User> users;

    @Id
    private long id_payee_received;
    @Id
    private long id_payee_provided;

    @Temporal(TemporalType.TIMESTAMP)
    Date start_payee_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date end_payee_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;

    public Payee( long id_payee_received,long id_payee_provided) {
        this.id_payee_provided = id_payee_provided;
        this.id_payee_provided = id_payee_provided;

    }


    public void setId_payee(long id_payee) {
        this.id_payee = id_payee;
    }

    public void setId_payee_received(long id_payee_received) {
        this.id_payee_received = id_payee_received;
    }

    public void setId_payee_provided(long id_payee_provided) {
        this.id_payee_provided = id_payee_provided;
    }

    public void setStart_payee_date(Date start_payee_date) {
        this.start_payee_date = start_payee_date;
    }

    public void setEnd_payee_date(Date end_payee_date) {
        this.end_payee_date = end_payee_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }

    public long getId_payee() {
        return id_payee;
    }

    public long getId_payee_received() {
        return id_payee_received;
    }

    public long getId_payee_provided() {
        return id_payee_provided;
    }

    public Date getStart_payee_date() {
        return start_payee_date;
    }

    public Date getEnd_payee_date() {
        return end_payee_date;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public Payee(){}
}
