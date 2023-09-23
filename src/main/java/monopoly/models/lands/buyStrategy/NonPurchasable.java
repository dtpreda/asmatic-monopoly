package monopoly.models.lands.buyStrategy;

import jade.content.Concept;
import monopoly.models.Player;

public class NonPurchasable extends PurchasableStrategy implements Concept {

    public NonPurchasable() {
    }
    @Override
    public boolean purchase(Player player) {
        return false;
    }

    @Override
    public boolean canPurchase(Player player) {
        return false;
    }
}
