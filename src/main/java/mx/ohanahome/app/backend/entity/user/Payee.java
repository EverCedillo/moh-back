package mx.ohanahome.app.backend.entity.user;
//import com.googlecode.objectify.annotation.Entity;
//import com.googlecode.objectify.annotation.Id;

import java.util.Set;
import java.util.Date;


import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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

    @EmbeddedId
    private PayeeId id;

    @ManyToMany(mappedBy="payees")
    private Set<User> users;




    @Temporal(TemporalType.TIMESTAMP)
    Date payee_start_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date payee_end_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;



    public Payee(PayeeId id) {
        this.id=id;

    }


    public PayeeId getId() {
        return id;
    }

    public void setId(PayeeId id) {
        this.id = id;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }


    public Date getPayee_start_date() {
        return payee_start_date;
    }

    public void setPayee_start_date(Date payee_start_date) {
        this.payee_start_date = payee_start_date;
    }

    public Date getPayee_end_date() {
        return payee_end_date;
    }

    public void setPayee_end_date(Date payee_end_date) {
        this.payee_end_date = payee_end_date;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public Payee(){}

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Payee && ((Payee)obj).getId().equals(this.getId());
    }
}

@Embeddable
class PayeeId{
    long id_payee_received;
    long id_payee_provided;

    public long getId_payee_received() {
        return id_payee_received;
    }

    public void setId_payee_received(long id_payee_received) {
        this.id_payee_received = id_payee_received;
    }

    public long getId_payee_provided() {
        return id_payee_provided;
    }

    public void setId_payee_provided(long id_payee_provided) {
        this.id_payee_provided = id_payee_provided;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PayeeId
                && ((PayeeId)obj).getId_payee_provided()==this.getId_payee_provided()
                &&((PayeeId)obj).getId_payee_received()==this.getId_payee_received();
    }
}