package monopoly.agents.buyLandStrategy;

import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Property;

public interface BuyLandStrategy {
    public boolean buyLand(MonopolyBoard board, Property property, Player player);
}
