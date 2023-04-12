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
            System.out.println("JAILBREAK SUCCESSFUL " + player.getName() + " rolled " + dice.getValue());
            player.setJailed(false);
            boardController.movePlayer(player, dice.getValue());
            RollState newState = new RollState(board, monopolyController);
            PlayResult result = newState.play(player, dice);
            return result;
        } else {
            System.out.println("Couldn't jailbreak " + player.getName() + " rolled " + dice.getValue());
            player.decreaseJailTries();
            boardController.nextPlayer();
            changeState(new TradeState(board, monopolyController));
        }
        return null;
    }
}
