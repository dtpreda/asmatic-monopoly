package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;

public class CommunityChest extends Land {

    public CommunityChest() {
        super(new NonPurchasable());
    }
}