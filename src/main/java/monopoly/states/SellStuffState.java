package monopoly.states;

import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.lands.Property;

public class SellStuffState extends MonopolyState{
    private PayRentState previousState;
    public SellStuffState(MonopolyBoard board, MonopolyController monopolyController, PayRentState previousState) {
        super(board, monopolyController);
        this.previousState = previousState;
    }


    public int getAmountRequired(){
        return previousState.getLand().getRentStrategy().getRent(1);
    }
    public boolean sellHouse(Property property) {
        if(property.decreaseBuilding()){
            previousState.getPlayer().addMoney(property.getHousePrice() / 2);
            return true;
        }
        return false;
    }


    public boolean goBack() {
        if(previousState.getPlayer().getMoney() > previousState.getLand().getRentStrategy().getRent(1)){
            monopolyController.setState(new TradeState(board, monopolyController));
            return true;
        }
        monopolyController.setState(previousState);
        return false;
    }
}
