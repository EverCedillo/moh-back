package mx.ohanahome.app.backend.entity.product;

import java.util.Date;

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
 * Created by ever on 11/10/16.
 */
@Entity
@Table(name = "TOH_STOCK_DETAIL")
public class StockDetail {


    @GeneratedValue(generator = "increment")
    @Id
    long id_stock_detail;
    long id_stock_product;

    @Temporal(TemporalType.TIMESTAMP)
    Date stock_date;

    int quantity;

    String movement_stock;//    enum('Existente','Faltante','Salida')

    public void setId_stock_detail(long id_stock_detail) {
        this.id_stock_detail = id_stock_detail;
    }

    public void setId_stock_product(long id_stock_product) {
        this.id_stock_product = id_stock_product;
    }

    public void setStock_date(Date stock_date) {
        this.stock_date = stock_date;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setMovement_stock(String movement_stock) {
        this.movement_stock = movement_stock;
    }

    public long getId_stock_detail() {
        return id_stock_detail;
    }

    public Date getStock_date() {
        return stock_date;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMovement_stock() {
        return movement_stock;
    }
}
