package monopoly.states;

import monopoly.controllers.BoardController;
import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;
import monopoly.models.lands.Land;
import monopoly.models.lands.Property;

public abstract class MonopolyState {

    protected MonopolyBoard board;
    protected MonopolyController monopolyController;
    //Spaghetti code
    protected BoardController boardController;
    public MonopolyState(MonopolyBoard board, MonopolyController monopolyController){
        this.board = board;
        this.monopolyController = monopolyController;
        this.boardController = monopolyController.getBoardController();
    }

    public boolean buyHouse(Property property, Player player) {
        int cost = property.getHousePrice();
        if (player.getMoney() < cost) {
            return false;
        }
        if(property.increaseBuilding()) {
            player.removeMoney(cost);
        }
        property.updateRent();
        return true;
    }

    public boolean trade(Trade trade){
        //TODO
        return false;
    }

    public boolean mortage(Land land, Player player){
        //TODO
        return false;
    }

    public boolean unmortage(Land land, Player player){
        //TODO
        return false;
    }


    public void changeState(MonopolyState state){
        monopolyController.setState(state);
    }

    public void nextPlayer(){
        int previous = monopolyController.getBoard().getCurrentPlayerIndex();
        monopolyController.getBoard().nextPlayer();
        if(monopolyController.getBoard().getCurrentPlayerIndex() < previous){
            monopolyController.addTurn();
        }
        Player currentPlayer = monopolyController.getBoard().getCurrentPlayer();
        if(monopolyController.getStats().getPlayerStats().get(currentPlayer.getName()).getMoney().size() > 100){
            monopolyController.getState().changeState(new GameOverState(monopolyController.getBoard(), monopolyController));
        }
    }

}
