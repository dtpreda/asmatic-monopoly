package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;

public class Jail extends Land {

    public Jail() {
        super(new NonPurchasable());
    }
}