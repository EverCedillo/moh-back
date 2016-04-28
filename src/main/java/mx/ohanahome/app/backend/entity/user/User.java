package mx.ohanahome.app.backend.entity.user;



import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by brenda on 4/3/16.
 */

@NamedQuery(name = "User.findUserByEmail",query = "select t from User t where email = :email")
@Table(name = "TOH_USER")
@Entity
public class User{

    @GeneratedValue(generator = "increment")
    @Column(name="id_user")
    @Id
    private long id_user;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name= "TOH_USER_HOME",
            joinColumns=@JoinColumn(name="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_home"))
    private Set<Home> homes;

    /*/Esta relación causa conflicto,
    @ManyToMany
    @JoinTable(
            name="TOH_PAYEE",
            joinColumns=@JoinColumn(name="id_payee_received"),
            inverseJoinColumns=@JoinColumn(name="id_payee_provided"))
    private Set<Payee> payees; //*/

    @OneToMany(mappedBy="user",fetch = FetchType.EAGER)
    private Set<UserRole> userRoles;



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="TOH_USER_INTOLERANCE",
            joinColumns=@JoinColumn(name="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_user_intolerance"))
    private Set<Intolerance> intolerances;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="TOH_USER_ILLNESS",
            joinColumns=@JoinColumn(name="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_user_illness"))
    private Set<Illness> illnesses;


    @ManyToMany(mappedBy="user", fetch = FetchType.EAGER)
    private Set<PurchaseLimit> purchases;


    String user_name;
    String last_name;
    String gender;
    String picture;
    String mobile_phone;
<<<<<<< HEAD
=======
    Date birthday;
>>>>>>> 41406de807575e5302d011b2d66afc7317f8f5d5
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


    public User( String user_name, String last_name, String gender, String picture, Date birthday, String email) {
        this.user_name = user_name;
        this.last_name = last_name;
        this.gender = gender;
        this.picture = picture;
        this.birthday = birthday;
        this.email=email;
    }

    public User mergeValues(User user) {

        this.user_name = user.user_name==null?this.user_name:user.user_name;
        this.last_name = user.last_name==null?this.last_name:user.last_name;
        this.gender = user.gender==null?this.gender:user.gender;
        this.picture = user.picture==null?this.picture:user.picture;
        this.mobile_phone = user.mobile_phone==null?this.mobile_phone:user.mobile_phone;
        this.birthday = user.birthday==null?this.birthday:user.birthday;
        this.email = user.email==null?this.email:user.email;
        this.height = user.height==null?this.height:user.height;
        this.weight = user.weight==null?this.weight:user.weight;
        this.pin = user.pin==null?this.pin:user.pin;
        this.pattern = user.pattern==null?this.pattern:user.pattern;
        this.creation_date = user.creation_date==null?this.creation_date:user.creation_date;
        this.modification_date = user.modification_date==null?this.modification_date:user.modification_date;

        return this;
    }

    public long getId_user() {
        return id_user;
    }

    public void setId_user(long id_user) {
        this.id_user = id_user;
    }

    @JsonIgnore
    public Set<Home> getHomes() {
        return homes;
    }

    public void setHomes(Set<Home> homes) {
        this.homes = homes;
    }

    /*/
    public Set<Payee> getPayees() {
        return payees;
    }

    public void setPayees(Set<Payee> payees) {
        this.payees = payees;
    }//*/


    public Set<Intolerance> getIntolerances() {
        return intolerances;
    }

    public void setIntolerances(Set<Intolerance> intolerances) {
        this.intolerances = intolerances;
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

    public Set<Illness> getIllnesses() {
        return illnesses;
    }

    public void setIllnesses(Set<Illness> illnesses) {
        this.illnesses = illnesses;
    }

    public Set<PurchaseLimit> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<PurchaseLimit> purchases) {
        this.purchases = purchases;
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

<<<<<<< HEAD
    public void setBirthday(java.sql.Date birthday) {
=======
    public void setBirthday(Date birthday) {
>>>>>>> 41406de807575e5302d011b2d66afc7317f8f5d5
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void addHome(Home home){
        homes.add(home);
    }
    public User(){

    }

    public void addUserRole(UserRole userRole){
        userRoles.add(userRole);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof User && ((User)obj).getId_user()==this.getId_user();
    }
}
