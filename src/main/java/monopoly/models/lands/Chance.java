package monopoly.models.lands;

import monopoly.models.Player;
import monopoly.models.lands.buyStrategy.NonPurchasable;

public class Chance extends Land {

    public Chance() {
        super(new NonPurchasable());
    }
}