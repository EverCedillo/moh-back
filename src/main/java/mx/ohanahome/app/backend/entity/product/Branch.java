package mx.ohanahome.app.backend.entity.product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by brenda on 4/27/16.
 */

@Table(name = "TOH_BRANCH")
@Entity
public class Branch {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name = "id_branch")
    private long id_branch;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="id_store")
    private  Store stores;

    String branch_name;
    String telephone;
    double shipping_cost;
    String ship_mode;
    String address;
    String shipping_time;


    public Branch(String branch_name, String telephone, double shipping_cost,
                    String ship_mode, String address, String shipping_time){

        this.branch_name = branch_name;
        this.telephone = telephone;
        this.shipping_cost = shipping_cost;
        this.ship_mode = ship_mode;
        this.address = address;
        this.shipping_time = shipping_time;
    }

    public long getId_branch() {
        return id_branch;
    }

    public void setId_branch(long id_branch) {
        this.id_branch = id_branch;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public double getShipping_cost() {
        return shipping_cost;
    }

    public void setShipping_cost(double shipping_cost) {
        this.shipping_cost = shipping_cost;
    }

    public String getShip_mode() {
        return ship_mode;
    }

    public void setShip_mode(String ship_mode) {
        this.ship_mode = ship_mode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShipping_time() {
        return shipping_time;
    }

    public void setShipping_time(String shipping_time) {
        this.shipping_time = shipping_time;
    }
}
