package monopoly.agents.buyLandStrategy;

import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Property;

public class AfkBuyLandStrategy implements BuyLandStrategy{
    @Override
    public boolean buyLand(MonopolyBoard board, Property property, Player player) {
        return false;
    }
}
