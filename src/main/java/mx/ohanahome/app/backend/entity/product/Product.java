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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.ohanahome.app.backend.entity.user.User;

/**
 * Created by brenda on 4/27/16.
 */

@NamedQueries({
        @NamedQuery(name = "Product.getProducts", query = "select p from Product p"),
        @NamedQuery(name = "Product.getProductsMap", query = "select new mx.ohanahome.app.backend.entity.product.Product(p.id_product, p.product_name) from Product p")
}
)
@Table(name = "TOH_PRODUCT")
@Entity
public class Product {
    @GeneratedValue(generator = "increment")
    @Id
    @Column(name = "id_product")
    private long id_product;

    @ManyToMany(mappedBy= "products",fetch = FetchType.EAGER)
    private Set<Store> stores;

    @OneToMany(mappedBy="products")
    private Set<ProductPrice> product_prices ;

    String product_name;
    Integer order_quantity;
    Integer category;
    Integer sub_category;
    Integer depto;
    Integer amount;
    String  unit;
    String brand;
    Integer product_no;
    String image;

    public Product(String product_name,Integer order_quantity,  Integer category, Integer sub_category,
                   Integer depto, Integer amount,String  unit, String brand, Integer product_no, String image){

        this.product_name = product_name;
        this.order_quantity = order_quantity;
        this.category = category;
        this.sub_category = sub_category;
        this.depto = depto;
        this.amount = amount;
        this.unit = unit;
        this.brand = brand;
        this. product_no = product_no;
        this.image = image;
    }

    public Product(long id_product, String product_name) {
        this.id_product = id_product;
        this.product_name = product_name;
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

    public Integer getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(Integer order_quantity) {
        this.order_quantity = order_quantity;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getSub_category() {
        return sub_category;
    }

    public void setSub_category(Integer sub_category) {
        this.sub_category = sub_category;
    }

    public Integer getDepto() {
        return depto;
    }

    public void setDepto(Integer depto) {
        this.depto = depto;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String  getUnit() {
        return unit;
    }

    public void setUnit(String  unit) {
        this.unit = unit;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Integer getProduct_no() {
        return product_no;
    }

    public void setProduct_no(Integer product_no) {
        this.product_no = product_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<Store> getStores() {
        return stores;
    }

    public Set<ProductPrice> getProduct_prices() {
        return product_prices;
    }
}

