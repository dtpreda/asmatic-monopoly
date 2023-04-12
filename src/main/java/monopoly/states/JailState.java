package monopoly.states;

import monopoly.controllers.MonopolyController;
import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.PlayResult;
import monopoly.models.Player;

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
            changeState(new RollState(board, monopolyController));
        }
        return null;
    }

    public boolean payFine(Player player){
        if(player.getMoney() >= 50){
            player.addMoney(-50);
            changeState(new RollState(board, monopolyController));
            return true;
        }
        return false;
    }
}
