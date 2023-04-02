package monopoly.states;

import monopoly.controllers.BoardController;
import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;

public class BuyLandState extends MonopolyState{

    private Player player;
    public BuyLandState(Player player, MonopolyBoard board, MonopolyController monopolyController){
        super(board, monopolyController);
        this.player = player;
    }

    public boolean buyLand(Player player){
        //TODO
        return false;
    }

    public void skip(Player player){
        changeState(new RollState(board, monopolyController));
    }
}
