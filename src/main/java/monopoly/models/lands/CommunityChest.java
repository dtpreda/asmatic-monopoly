package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;
import monopoly.models.lands.rentStrategy.NoRentStrategy;

public class CommunityChest extends Land {

    public CommunityChest() {
        super(new NonPurchasable());
        setRentStrategy(new NoRentStrategy());
    }
}