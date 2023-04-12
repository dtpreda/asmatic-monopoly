package monopoly.actions;

import jade.content.Predicate;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;

public class TradeStateAction implements Predicate {
    private MonopolyBoard board;
    private Player player;

    public TradeStateAction() {
    }
    public TradeStateAction(MonopolyBoard board, Player player) {
        super();
        this.board = board;
        this.player = player;
    }


    public MonopolyBoard getBoard() {
        return board;
    }

    public void setBoard(MonopolyBoard board) {
        this.board = board;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
