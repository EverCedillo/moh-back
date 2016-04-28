package mx.ohanahome.app.backend.entity.product;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
 * Created by brenda on 4/27/16.
 */

@Table(name = "TOH_PRODUCT")
@Entity
public class Product {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name = "id_product")
    private long id_product;

    @ManyToMany(mappedBy= "products",fetch = FetchType.EAGER)
    private Set<Store> stores;

    @OneToMany(mappedBy="product_prices")
    private Set<ProductPrice> product_prices ;

    String product_name;
    int order_quantity;
    int category;
    int sub_category;
    int depto;
    int amount;
    int unit;
    String brand;
    int product_no;

    public Product(String product_name,int order_quantity,  int category, int sub_category,
                   int depto, int amount,int unit, String brand, int product_no){

        this.product_name = product_name;
        this.order_quantity = order_quantity;
        this.category = category;
        this.sub_category = sub_category;
        this.depto = depto;
        this.amount = amount;
        this.unit = unit;
        this.brand = brand;
        this. product_no = product_no;
    }

    public long getId_product() {
        return id_product;
    }

    public void setId_product(long id_product) {
        this.id_product = id_product;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(int order_quantity) {
        this.order_quantity = order_quantity;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSub_category() {
        return sub_category;
    }

    public void setSub_category(int sub_category) {
        this.sub_category = sub_category;
    }

    public int getDepto() {
        return depto;
    }

    public void setDepto(int depto) {
        this.depto = depto;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getProduct_no() {
        return product_no;
    }

    public void setProduct_no(int product_no) {
        this.product_no = product_no;
    }
}

