package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;
import monopoly.models.lands.rentStrategy.NoRentStrategy;

public class Jail extends Land {

    public Jail() {
        super(new NonPurchasable());
        setRentStrategy(new NoRentStrategy());
    }
}