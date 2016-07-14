package mx.ohanahome.app.backend.entity.product;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.ohanahome.app.backend.entity.user.User;

/**
 * Created by brenda on 5/1/16.
 */
@Table(name = "TOH_CUSTOMER")
@Entity
public class Customer {

    @Column(name="id_customer")
    @Id
    private long id_customer;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name= "TOH_CUSTOMER_HOME",
            joinColumns=@JoinColumn(name="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_home"))
    private Set<Home> homes;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name= "TOH_CUSTOMER_ORDER",
            joinColumns=@JoinColumn(name="id_customer"),
            inverseJoinColumns=@JoinColumn(name="id_order"))
    private Set<CustomerOrder> orders;


    @OneToMany(mappedBy=" customer_product")
    private Set<OrderProduct> orderProducts ;

    /*

    @OneToMany(mappedBy=" customer_payment")
    private Set<Payment> payments  ;

    /*@OneToMany(mappedBy="customer",fetch = FetchType.EAGER)
    private Set<CustomerOrder> customerOrders;
    */


    String user_name;
    String last_name;
    String gender;
    String picture;
    String mobile_phone;
    String email;
    Integer height;
    Integer weight;
    String pin;
    String pattern;

    @Temporal(TemporalType.DATE)
    Date birthday;
    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;


    public Customer ( String user_name, String last_name, String gender, String picture, Date birthday, String email) {
        this.user_name = user_name;
        this.last_name = last_name;
        this.gender = gender;
        this.picture = picture;
        this.birthday = birthday;
        this.email=email;
    }

    public Customer(User user){
        this.id_customer = user.getId_user();
        this.user_name = user.getUser_name()==null?this.user_name:user.getUser_name();
        this.last_name = user.getLast_name()==null?this.last_name:user.getLast_name();
        this.gender = user.getGender()==null?this.gender:user.getGender();
        this.picture = user.getPicture()==null?this.picture:user.getPicture();
        this.mobile_phone = user.getMobile_phone()==null?this.mobile_phone:user.getMobile_phone();
        this.birthday = user.getBirthday()==null?this.birthday:user.getBirthday();
        this.email = user.getEmail()==null?this.email:user.getEmail();
        this.height = user.getHeight()==null?this.height:user.getHeight();
        this.weight = user.getWeight()==null?this.weight:user.getWeight();
        this.pin = user.getPin()==null?this.pin:user.getPin();
        this.pattern = user.getPattern()==null?this.pattern:user.getPattern();
        this.creation_date = user.getCreation_date()==null?this.creation_date:user.getCreation_date();
        this.modification_date = user.getModification_date()==null?this.modification_date:user.getModification_date();
    }

    public long getId_customer() {
        return id_customer;
    }

    public void setId_customer(long id_customer) {
        this.id_customer = id_customer;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;

        Customer customer = (Customer) o;

        return id_customer == customer.id_customer;

    }


}



