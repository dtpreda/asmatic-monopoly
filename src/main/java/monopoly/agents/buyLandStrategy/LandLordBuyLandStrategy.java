package monopoly.agents.buyLandStrategy;

import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Property;

import java.util.List;

public class LandLordBuyLandStrategy implements BuyLandStrategy{
    @Override
    public boolean buyLand(MonopolyBoard board, Property property, Player player) {
        String color = property.getColor();
        if(board.getOwnedPropertiesColorPlayer(player.getName(), color).size() > 0){
            return true;
        }

        List<Property> ownedProperties = board.getProperties(player);
        if(ownedProperties.size() <= 3){
            return true;
        }
        Dice dice = new Dice();
        return dice.getValue() <= 8 - ownedProperties.size();
    }
}
