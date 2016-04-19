package mx.ohanahome.app.backend.entity;




import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Table(name = "T01_TEST")
@Entity
public class Dummy {

    @GeneratedValue(generator = "increment")
    @Id
    long id;

    String name;

    public Dummy(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Dummy(){}

    public Dummy(String name) {
        this.name = name;
    }

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
}
