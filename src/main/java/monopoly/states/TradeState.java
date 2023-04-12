package monopoly.states;

import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;

import java.util.ArrayList;
import java.util.List;

public class TradeState extends MonopolyState{
    List<Player> playersReady;

    public TradeState(MonopolyBoard board, MonopolyController monopolyController) {
        super(board, monopolyController);
        playersReady = new ArrayList<>();
    }

    public void playerReady(Player player){
        if(!playersReady.contains(player)){
            playersReady.add(player);
        }
    }

    public boolean canStart(){
        return playersReady.size() == board.getPlayers().size();
    }

    public boolean start(){
        if(canStart()){
            monopolyController.setState(new RollState(board, monopolyController));
            return true;
        }
        return false;
    }


}
