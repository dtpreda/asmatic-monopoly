package monopoly.models.lands.buyStrategy;

import jade.content.Concept;
import monopoly.models.Player;

public abstract class PurchasableStrategy implements Concept {

    public PurchasableStrategy() {
    }
    abstract public boolean purchase(Player player);
}
