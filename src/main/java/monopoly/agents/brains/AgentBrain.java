package monopoly.agents.brains;

import monopoly.agents.buyLandStrategy.BuyLandStrategy;
import monopoly.agents.tradeStrategy.TradeStrategy;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Property;

public abstract class AgentBrain {

    protected TradeStrategy tradeStrategy;
    protected BuyLandStrategy buyLandStrategy;

    public AgentBrain(TradeStrategy tradeStrategy, BuyLandStrategy buyLandStrategy) {
        this.tradeStrategy = tradeStrategy;
        this.buyLandStrategy = buyLandStrategy;
    }

    public TradeStrategy getTradeStrategy() {
        return tradeStrategy;
    }

    public BuyLandStrategy getBuyLandStrategy() {
        return buyLandStrategy;
    }
}
