package mx.ohanahome.app.backend.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by ever on 31/03/16.
 */
@Entity
public class Dummy {

    @Id
    long id;
    String text;

    public Dummy(){}

    public Dummy(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
