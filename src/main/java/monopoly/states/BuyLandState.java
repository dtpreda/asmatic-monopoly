package monopoly.states;

import monopoly.controllers.BoardController;
import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Land;

public class BuyLandState extends MonopolyState{

    private Player player;
    private Land land;
    public BuyLandState(Player player, MonopolyBoard board, Land land, MonopolyController monopolyController){
        super(board, monopolyController);
        this.player = player;
        this.land = land;
    }

    public boolean buyLand(Player player){
        return land.getBuyStrategy().purchase(player);
    }

    public void endState(Player player){
        changeState(new RollState(board, monopolyController));
        if(!board.getLastDice().isDouble()){
            nextPlayer();
        }
    }

    public Land getLand(){
        return land;
    }
}
