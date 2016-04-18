package mx.ohanahome.app.backend.model;
//import com.googlecode.objectify.annotation.Entity;
//import com.googlecode.objectify.annotation.Id;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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

    long id_payee_received;
    long id_payee_provided;
    String start_payee_date;
    String end_payee_date;
    String creation_date;
    String modification_date;

    public void setId_payee(long id_payee) {
        this.id_payee = id_payee;
    }

    public void setId_payee_received(long id_payee_received) {
        this.id_payee_received = id_payee_received;
    }

    public void setId_payee_provided(long id_payee_provided) {
        this.id_payee_provided = id_payee_provided;
    }

    public void setStart_payee_date(String start_payee_date) {
        this.start_payee_date = start_payee_date;
    }

    public void setEnd_payee_date(String end_payee_date) {
        this.end_payee_date = end_payee_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public void setModification_date(String modification_date) {
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

    public String getStart_payee_date() {
        return start_payee_date;
    }

    public String getEnd_payee_date() {
        return end_payee_date;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getModification_date() {
        return modification_date;
    }

    public Payee(){}
}
