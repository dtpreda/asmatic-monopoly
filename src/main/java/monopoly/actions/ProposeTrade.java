package monopoly.actions;

import jade.content.Predicate;
import monopoly.models.MonopolyBoard;
import monopoly.models.Trade;

public class ProposeTrade implements Predicate {
    private Trade trade;
    private MonopolyBoard board;
    public ProposeTrade() {
    }
    public ProposeTrade(Trade trade, MonopolyBoard board) {
        this.trade = trade;
        this.board = board;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public MonopolyBoard getBoard() {
        return board;
    }

    public void setBoard(MonopolyBoard board) {
        this.board = board;
    }
}
