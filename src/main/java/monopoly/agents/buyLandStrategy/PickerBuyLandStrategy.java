package monopoly.agents.buyLandStrategy;

import monopoly.agents.tradeStrategy.PickerTradeStrategy;
import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Property;

public class PickerBuyLandStrategy implements BuyLandStrategy{
    @Override
    public boolean buyLand(MonopolyBoard board, Property property, Player player) {
        if(property.getPrice() >= PickerTradeStrategy.PICKER_THRESHOLD){
            return true;
        }
        return new Dice().getValue() < 3;
    }
}
