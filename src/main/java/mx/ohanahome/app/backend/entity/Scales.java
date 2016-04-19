package mx.ohanahome.app.backend.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 4/17/16.
 */
@Table(name = "TOH_SCALES")
@Entity
public class Scales {
    @GeneratedValue(generator = "increment")
    @Id
    private long id_scales;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="id_home")
    private Home home_scales;
    @Id
    private long id_home;
    String aditional_information;
    String mac_address;
    String scales_name;

    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;

    public Scales ( String mac_address, String scales_name){
        this.mac_address = mac_address;
        this.scales_name = scales_name;
    }

    public void setId_scales(long id_scales) {
        this.id_scales = id_scales;
    }

    public void setId_home(long id_home) {
        this.id_home = id_home;
    }

    public void setAditional_information(String aditional_information) {
        this.aditional_information = aditional_information;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public void setScale_name(String scales_name) {
        this.scales_name = scales_name;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public long getId_scales() {
        return id_scales;
    }

    public long getId_home() {
        return id_home;
    }

    public String getAditional_information() {
        return aditional_information;
    }

    public String getMac_address() {
        return mac_address;
    }

    public String getScales_name() {
        return scales_name;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }

    public Scales (){}
}
