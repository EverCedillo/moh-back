package mx.ohanahome.app.backend.model;

import mx.ohanahome.app.backend.entity.user.Identify;
import mx.ohanahome.app.backend.entity.user.PurchaseLimit;


/**
 * Created by ever on 12/07/16.
 */
public class BudgetPackage {
    Identify identify;
    PurchaseLimit purchaseLimit;

    public BudgetPackage(Identify identify, PurchaseLimit purchaseLimit) {
        this.identify = identify;
        this.purchaseLimit = purchaseLimit;

    }

    public Identify getIdentify() {
        return identify;
    }

    public void setIdentify(Identify identify) {
        this.identify = identify;
    }

    public PurchaseLimit getPurchaseLimit() {
        return purchaseLimit;
    }

    public void setPurchaseLimit(PurchaseLimit purchaseLimit) {
        this.purchaseLimit = purchaseLimit;
    }
}
