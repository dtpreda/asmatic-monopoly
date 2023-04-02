package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;

public class Parking extends Land {

    public Parking() {
        super(new NonPurchasable());
    }
}