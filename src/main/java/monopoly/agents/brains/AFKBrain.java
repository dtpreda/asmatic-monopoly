package monopoly.agents.brains;

import monopoly.agents.buyLandStrategy.AfkBuyLandStrategy;
import monopoly.agents.buyLandStrategy.RandomBuyLandStrategy;
import monopoly.agents.tradeStrategy.AFKTradeStrategy;

public class AFKBrain extends AgentBrain{
    public AFKBrain() {
        super(new AFKTradeStrategy(), new AfkBuyLandStrategy());
    }


}
