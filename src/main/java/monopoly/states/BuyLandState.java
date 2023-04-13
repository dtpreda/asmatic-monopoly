package monopoly.states;

import monopoly.controllers.BoardController;
import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Company;
import monopoly.models.lands.Land;
import monopoly.models.lands.Property;
import monopoly.models.lands.RailRoad;
import monopoly.models.lands.rentStrategy.PayOwnerStrategy;

public class BuyLandState extends MonopolyState{

    private Player player;
    private Land land;
    public BuyLandState(Player player, MonopolyBoard board, Land land, MonopolyController monopolyController){
        super(board, monopolyController);
        this.player = player;
        this.land = land;
    }

    public boolean buyLand(Player player){
        boolean success = land.getBuyStrategy().purchase(player);
        if(success){
            //Set rent
            land.setRentStrategy(new PayOwnerStrategy(player));
            if(land instanceof Property){
                Property property = (Property) land;
                if(board.ownsAllPropertiesColor(player.getName(), property.getColor())){
                    for(Property prop: board.getOwnedPropertiesColorPlayer(player.getName(), property.getColor())){
                        prop.increaseBuilding();
                        prop.updateRent();
                    }
                }
                property.updateRent();
            } else if(land instanceof Company){
                Company company = (Company) land;
                company.updateRent();

            } else if(land instanceof RailRoad){
                RailRoad railRoad = (RailRoad) land;
                railRoad.updateRent();
            }
        }
        return success;
    }

    public void endState(Player player){
        changeState(new TradeState(board, monopolyController));
        if(!board.getLastDice().isDouble()){
            nextPlayer();
        }
    }

    public Land getLand(){
        return land;
    }
}
