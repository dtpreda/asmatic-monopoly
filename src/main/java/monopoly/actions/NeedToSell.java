package monopoly.actions;

import jade.content.Predicate;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;

public class NeedToSell implements Predicate {

    private int amountRequired;
    private Player player;
    private MonopolyBoard board;

    public NeedToSell(int amountRequired, Player player, MonopolyBoard board) {
        this.amountRequired = amountRequired;
        this.player = player;
        this.board = board;
    }

    public NeedToSell() {
    }

    public int getAmountRequired() {
        return amountRequired;
    }

    public void setAmountRequired(int amountRequired) {
        this.amountRequired = amountRequired;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public MonopolyBoard getBoard() {
        return board;
    }

    public void setBoard(MonopolyBoard board) {
        this.board = board;
    }
}
