package mx.ohanahome.app.backend.model;

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
@Table(name = "TOH_ILLNESS")
@Entity
public class Illness {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name="id_illness")
    private long id_illness;

    @ManyToMany(mappedBy="illnesses")
    private List<User> users;

    String illness_name;

    public Illness(String illness_name){
        this.illness_name = illness_name;
    }


    public void setId_illness(long id_illness) {
        this.id_illness = id_illness;
    }

    public void setIllness_name(String illness_name) {
        this.illness_name = illness_name;
    }


    public long getId_illness() {
        return id_illness;
    }

    public String getIllness_name() {
        return illness_name;
    }

    public Illness(){

    }

}
