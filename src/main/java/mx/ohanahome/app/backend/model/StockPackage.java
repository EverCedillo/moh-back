package mx.ohanahome.app.backend.model;


import java.util.List;

import mx.ohanahome.app.backend.entity.product.Stock;
import mx.ohanahome.app.backend.entity.product.StockDetail;
import mx.ohanahome.app.backend.entity.product.StockProduct;
import mx.ohanahome.app.backend.entity.user.Identify;

/**
 * Created by ever on 11/10/16
 */
public class StockPackage {

    boolean updating;

    Identify identify;

    Stock stock;

    StockProduct item;
    StockDetail itemDetails;



    public Identify getIdentify() {
        return identify;
    }

    public void setIdentify(Identify identify) {
        this.identify = identify;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public StockProduct getItem() {
        return item;
    }

    public void setItem(StockProduct item) {
        this.item = item;
    }

    public StockDetail getItemDetails() {
        return itemDetails;
    }

    public void setItemDetails(StockDetail itemDetails) {
        this.itemDetails = itemDetails;
    }

    public boolean isUpdating() {
        return updating;
    }

    public void setUpdating(boolean updating) {
        this.updating = updating;
    }
}
