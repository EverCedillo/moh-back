package mx.ohanahome.app.backend.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by ever on 17/04/16.
 */
@Table(name = "T_TEST")
@Entity
public class Dummy3 {

    @GeneratedValue(generator = "increment")
    @Id
    long id;

    String name;

    @Temporal(TemporalType.TIMESTAMP)
    Date date;

    public Dummy3() {
    }

    public Dummy3(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
