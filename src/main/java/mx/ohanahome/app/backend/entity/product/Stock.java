package mx.ohanahome.app.backend.entity.product;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by ever on 11/10/16
 */

@NamedQueries(
        {
                @NamedQuery(name = "Stock.getStockByHome",query = "select s from Stock s where s.home.id_home = :id_home"),
                @NamedQuery(name = "Stock.getItems", query = "select i from StockProduct i where i.stock.id_stock = :id_stock")
        }
)
@Table(name = "TOH_STOCK")
@Entity
public class Stock {


    @Id
    long id_stock;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn (name = "id_home")
    private Home home;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "stock")
    Set<StockProduct> stockProducts;

    public long getId_stock() {
        return id_stock;
    }

    public void setId_stock(long id_stock) {
        this.id_stock = id_stock;
    }

    @JsonIgnore
    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }




    public void setStockProducts(Set<StockProduct> stockProducts) {
        this.stockProducts = stockProducts;
    }
}
