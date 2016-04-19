package mx.ohanahome.app.backend.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 4/3/16.
 */
@Table(name = "TOH_IDENTIFY")
@Entity
public class Identify {

    @GeneratedValue(generator = "increment")
    @Id
    @Column(name = "id_identify")
    private long id_identify;

    String id_adapter;
    String adapter;
    String email;
    @Column(updatable = false,insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;
    @Column(updatable = false,insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;

    public Identify(String id_adapter, String adapter, String email, Date creation_date, Date modification_date) {
        this.id_adapter = id_adapter;
        this.adapter = adapter;
        this.email = email;
        this.creation_date = creation_date;
        this.modification_date = modification_date;
    }

    public void setId_identify(long id_identify) {
        this.id_identify = id_identify;
    }


    public void setId_adapter(String id_adapter) {
        this.id_adapter = id_adapter;
    }

    public void setAdapter(String adapter) {
        this.adapter = adapter;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }

    public long getId_identify() {
        return id_identify;
    }


    public String getId_adapter() {
        return id_adapter;
    }

    public String getAdapter() {
        return adapter;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public Identify(){


    }


}
