package mx.ohanahome.app.backend.entity;



import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 4/3/16.
 */

@Table(name = "TOH_USER")
@Entity
public class User {

    @GeneratedValue(generator = "increment")
    @Column(name="id_user")
    @Id
    private long id_user;

    @ManyToMany
    @JoinTable(
            name= "UserHome",
            joinColumns=@JoinColumn(name="id_user", referencedColumnName="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_home", referencedColumnName="id_home"))
    private List<Home> homes;

    @ManyToMany
    @JoinTable(
            name="Payee",
            joinColumns=@JoinColumn(name="id_payee_received", referencedColumnName="id_payee_received"),
            inverseJoinColumns=@JoinColumn(name="id_payee_provided", referencedColumnName="id_payee_provided"))
    private List<Payee> payees;

    @ManyToMany
    @JoinTable(
            name="UserRole",
            joinColumns=@JoinColumn(name="id_user", referencedColumnName="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_user_role", referencedColumnName="id_user_role"))
    private List<Role> roles;



    @ManyToMany
    @JoinTable(
            name="UserIntolerance",
            joinColumns=@JoinColumn(name="id_user", referencedColumnName="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_user_intolerance", referencedColumnName="id_user_intolerance"))
    private List<Intolerance> intolerances;


    @ManyToMany
    @JoinTable(
            name="UserIllness",
            joinColumns=@JoinColumn(name="id_user", referencedColumnName="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_user_illness", referencedColumnName="id_user_illness"))
    private List<Illness> illnesses;


    @ManyToMany(mappedBy="purchase_limit")
    private List<PurchaseLimit> purchases;


    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="id_identify")
    private Identify identify;


    String user_name;
    String last_name;
    String gender;
    String picture;
    String mobile_phone;
    String email;
    int height;
    int weight;
    String pin;
    String pattern;

    @Temporal(TemporalType.DATE)
    Date birthday;
    @Temporal(TemporalType.TIMESTAMP)
    Date creation_date;
    @Temporal(TemporalType.TIMESTAMP)
    Date modification_date;


    public User( String user_name, String last_name, String gender, String picture, Date birthday, String email) {
        this.user_name = user_name;
        this.last_name = last_name;
        this.gender = gender;
        this.picture = picture;
        this.birthday = birthday;
        this.email=email;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    public List<Home> getHomes() {
        return homes;
    }

    public void setHomes(List<Home> homes) {
        this.homes = homes;
    }

    public List<Payee> getPayees() {
        return payees;
    }

    public void setPayees(List<Payee> payees) {
        this.payees = payees;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Intolerance> getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(List<Intolerance> intolerances) {
        this.intolerances = intolerances;
    }

    public List<Illness> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(List<Illness> illnesses) {
        this.illnesses = illnesses;
    }

    public List<PurchaseLimit> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseLimit> purchases) {
        this.purchases = purchases;
    }

    public Identify getIdentify() {
        return identify;
    }

    public void setIdentify(Identify identify) {
        this.identify = identify;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
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


    public User(){

    }
}
