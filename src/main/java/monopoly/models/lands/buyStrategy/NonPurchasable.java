package monopoly.models.lands.buyStrategy;

import monopoly.models.Player;

public class NonPurchasable implements PurchasableStrategy{
    @Override
    public boolean purchase(Player player) {
        return false;
    }
}
