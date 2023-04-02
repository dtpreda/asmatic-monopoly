package monopoly.states;

import monopoly.controllers.BoardController;
import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;
import monopoly.models.lands.Land;

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

    public boolean buyHouse(Land land, Player player) {
        //TODO
        return false;
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

    public boolean sellHouse(Land land, Player player){
        //TODO
        return false;
    }

    public void changeState(MonopolyState state){
        monopolyController.setState(state);
    }

}
