package monopoly.agents.brains;

import monopoly.agents.buyLandStrategy.BuyLandStrategy;
import monopoly.agents.buyLandStrategy.RandomBuyLandStrategy;
import monopoly.agents.tradeStrategy.AFKTradeStrategy;
import monopoly.agents.tradeStrategy.RandomTradeStrategy;
import monopoly.agents.tradeStrategy.TradeStrategy;

public class RandomBrain extends AgentBrain{
    public RandomBrain() {
        super(new RandomTradeStrategy(), new RandomBuyLandStrategy());
    }


}
