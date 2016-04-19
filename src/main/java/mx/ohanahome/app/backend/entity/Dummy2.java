package mx.ohanahome.app.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by ever on 10/04/16.
 */
@Table(name = "T02_TEST")
@Entity
public class Dummy2 {
    @GeneratedValue(generator = "increment")
    @Id
    long id;

    String name;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="dummy")
    Dummy dummy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Dummy getDummy() {
        return dummy;
    }

    public void setDummy(Dummy dummy) {
        this.dummy = dummy;
    }
}
