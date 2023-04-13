package monopoly.agents.buyLandStrategy;

import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Property;

public class RandomBuyLandStrategy implements BuyLandStrategy{
    @Override
    public boolean buyLand(MonopolyBoard board, Property property, Player player) {
        Dice dice = new Dice();
        return dice.isDouble();
    }
}
