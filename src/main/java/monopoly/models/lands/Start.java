package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;

public class Start extends Land {

    public Start() {
        super(new NonPurchasable());
    }
}