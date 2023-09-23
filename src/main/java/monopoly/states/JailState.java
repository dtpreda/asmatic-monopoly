package monopoly.states;

import monopoly.controllers.MonopolyController;
import monopoly.models.*;

public class JailState extends MonopolyState{

    public JailState(MonopolyBoard board, MonopolyController monopolyController){
        super(board, monopolyController);
    }

    public PlayResult attemptJailBreak(Player player){
        Dice dice = new Dice();
        board.setLastDice(dice);

        if(dice.isDouble()){
            player.setJailed(false);
            boardController.movePlayer(player, dice.getValue());
            RollState newState = new RollState(board, monopolyController);
            changeState(newState);
            PlayResult result = newState.play(player, dice);
            return result;
        } else {
            player.decreaseJailTries();
            nextPlayer();
            changeState(new TradeState(board, monopolyController));
        }
        return null;
    }
}
