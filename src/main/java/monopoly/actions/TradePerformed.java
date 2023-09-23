package monopoly.actions;

import jade.content.Predicate;
import monopoly.models.Trade;

public class TradePerformed implements Predicate {
    private Trade trade;

    public TradePerformed() {
    }
    public TradePerformed(Trade trade) {
        this.trade = trade;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }
}
