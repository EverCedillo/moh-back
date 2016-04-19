package mx.ohanahome.app.backend.entity;

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
@Table(name = "TOH_INTOLERANCE")
@Entity
public class Intolerance {
    @GeneratedValue (generator = "increment")
    @Id
    @Column(name="id_intolerance")
    private long id_intolerance;

    @ManyToMany(mappedBy="intolerances")
    private List<User> users;


    String intolerance_name;

    public Intolerance(String intolerance_name){
        this.intolerance_name = intolerance_name;

    }

    public void setId_intolerance(long id_intolerance) {
        this.id_intolerance = id_intolerance;
    }

    public void setIntolerance_name(String intolerance_name) {
        this.intolerance_name = intolerance_name;
    }


    public long getId_intolerance() {
        return id_intolerance;
    }

    public String getIntolerance_name() {
        return intolerance_name;
    }

    public Intolerance(){

    }

}
