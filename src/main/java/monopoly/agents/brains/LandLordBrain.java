package monopoly.agents.brains;

import monopoly.agents.buyLandStrategy.RandomBuyLandStrategy;
import monopoly.agents.tradeStrategy.AFKTradeStrategy;
import monopoly.agents.tradeStrategy.LandlordTradeStrategy;

public class LandLordBrain extends AgentBrain{
    public LandLordBrain() {
        super(new LandlordTradeStrategy(), new RandomBuyLandStrategy());
    }


}
