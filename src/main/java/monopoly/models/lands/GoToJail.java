package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;

public class GoToJail extends Land {

    public GoToJail() {
        super(new NonPurchasable());
    }
}