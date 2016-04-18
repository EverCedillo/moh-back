package mx.ohanahome.app.backend.model;



import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by brenda on 4/3/16.
 */

@Table(name = "TOH_HOME")
@Entity
public class Home {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name = "id_home")

    private long id_home;
    @OneToMany(mappedBy="home_scales")
    private List<Scales> scales;

    @ManyToMany(mappedBy= "homes")
    private List<User>users;

    @ManyToMany
    @JoinTable(
            name="HomeStore",
            joinColumns=@JoinColumn(name="id_home", referencedColumnName="id_home"),
            inverseJoinColumns=@JoinColumn(name="id_store", referencedColumnName="id_store"))
    private List<Store> stores;


    String home_name;
    String url;
    String telephone;
    long creator_id;
    String creation_date;
    String property_type;
    String modification_date;
    String street;
    String neighborhood;
    String interior_number;
    String exterior_number;
    int postal_code;
    String deleg_municip;
    double lenght;
    double latitude;

    public Home() {
    }

    public void setId_home(long id_home) {
        this.id_home = id_home;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setCreator_id(long creator_id) {
        this.creator_id = creator_id;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public void setProperty_type(String property_type) {
        this.property_type = property_type;
    }

    public void setModification_date(String modification_date) {
        this.modification_date = modification_date;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public void setInterior_number(String interior_number) {
        this.interior_number = interior_number;
    }

    public void setExterior_number(String exterior_number) {
        this.exterior_number = exterior_number;
    }

    public void setPostal_code(int postal_code) {
        this.postal_code = postal_code;
    }

    public void setDeleg_municip(String deleg_municip) {
        this.deleg_municip = deleg_municip;
    }

    public void setLenght(double lenght) {
        this.lenght = lenght;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setAditional_informat(String aditional_informat) {
        this.aditional_informat = aditional_informat;
    }

    public long getId_home() {
        return id_home;
    }

    public String getHome_name() {
        return home_name;
    }

    public String getUrl() {
        return url;
    }

    public String getTelephone() {
        return telephone;
    }

    public long getCreator_id() {
        return creator_id;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getProperty_type() {
        return property_type;
    }

    public String getModification_date() {
        return modification_date;
    }

    public String getStreet() {
        return street;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getInterior_number() {
        return interior_number;
    }

    public String getExterior_number() {
        return exterior_number;
    }

    public int getPostal_code() {
        return postal_code;
    }

    public String getDeleg_municip() {
        return deleg_municip;
    }

    public double getLenght() {
        return lenght;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAditional_informat() {
        return aditional_informat;
    }

    String aditional_informat;


}

