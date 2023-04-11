package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;
import monopoly.models.lands.rentStrategy.NoRentStrategy;

public class Parking extends Land {

    public Parking() {
        super(new NonPurchasable());
        setRentStrategy(new NoRentStrategy());
    }
}