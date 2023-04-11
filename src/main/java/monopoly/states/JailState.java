package monopoly.states;

import monopoly.controllers.MonopolyController;
import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;

public class JailState extends MonopolyState{

    public JailState(MonopolyBoard board, MonopolyController monopolyController){
        super(board, monopolyController);
    }

    public boolean rollDice(Player player){
        Dice dice = new Dice();
        board.setLastDice(dice);
        if(dice.isDouble()){

            boardController.movePlayer(player, dice.getValue());
            changeState(new RollState(board, monopolyController));
            return true;
        }
        return false;
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
