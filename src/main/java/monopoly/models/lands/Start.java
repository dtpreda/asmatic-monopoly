package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;
import monopoly.models.lands.rentStrategy.NoRentStrategy;

public class Start extends Land {

    public Start() {
        super(new NonPurchasable());
        setRentStrategy(new NoRentStrategy());
    }
}