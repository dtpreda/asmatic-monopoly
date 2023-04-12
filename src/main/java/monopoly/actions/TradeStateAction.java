package monopoly.actions;

import jade.content.Predicate;
import monopoly.models.MonopolyBoard;

public class TradeStateAction implements Predicate {
    private MonopolyBoard board;

    public TradeStateAction() {
    }
    public TradeStateAction(MonopolyBoard board) {
        super();
        this.board = board;
    }


    public MonopolyBoard getBoard() {
        return board;
    }

    public void setBoard(MonopolyBoard board) {
        this.board = board;
    }
}
