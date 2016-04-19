package mx.ohanahome.app.backend.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by brenda on 4/17/16.
 */
@Table(name = "TOH_SCALES")
@Entity
public class Scales {
    @GeneratedValue(generator = "increment")
    @Id
    private long id_scale;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="id_home")
    private Home home_scales;


    long id_home;
    String aditional_informat;
    String mac_address;
    String name;
    String creation_date;

    public void setId_scale(long id_scale) {
        this.id_scale = id_scale;
    }

    public void setId_home(long id_home) {
        this.id_home = id_home;
    }

    public void setAditional_informat(String aditional_informat) {
        this.aditional_informat = aditional_informat;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public long getId_scale() {
        return id_scale;
    }

    public long getId_home() {
        return id_home;
    }

    public String getAditional_informat() {
        return aditional_informat;
    }

    public String getMac_address() {
        return mac_address;
    }

    public String getName() {
        return name;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public Scales (){}
}
