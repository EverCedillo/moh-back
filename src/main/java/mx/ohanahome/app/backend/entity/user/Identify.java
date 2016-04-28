package mx.ohanahome.app.backend.entity.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 4/3/16.
 */
@NamedQueries(
        @NamedQuery(name = "Identify.verifyIdentity", query = "select i from Identify i where i.id_adapter=:id_adapter and i.adapter=:adapter")
)
@Table(name = "TOH_IDENTIFY")
@Entity
public class Identify {

    @GeneratedValue(generator = "increment")
    @Id
    @Column(name = "id_identify")
    private long id_identify;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_user")
    private User user;

    String id_adapter;
    String adapter;
    String email;
    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;

    public Identify(String id_adapter, String adapter, String email, Date creation_date, Date modification_date) {
        this.id_adapter = id_adapter;
        this.adapter = adapter;
        this.email = email;
        this.creation_date = creation_date;
        this.modification_date = modification_date;
    }

    public Identify updateIdentify(Identify identify) {
        this.id_adapter = identify.id_adapter;
        this.adapter = identify.adapter;
        this.email = identify.email;
        this.creation_date = identify.creation_date;
        this.modification_date = identify.modification_date;
        this.user=identify.user;
        return this;
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


    @Override
    public boolean equals(Object obj) {
        return obj instanceof Identify && ((Identify)obj).getId_identify()== this.getId_identify();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
