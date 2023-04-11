package monopoly.models.lands;

import monopoly.models.Player;
import monopoly.models.lands.buyStrategy.NonPurchasable;
import monopoly.models.lands.rentStrategy.NoRentStrategy;

public class Chance extends Land {

    public Chance() {
        super(new NonPurchasable());
        setRentStrategy(new NoRentStrategy());
    }
}