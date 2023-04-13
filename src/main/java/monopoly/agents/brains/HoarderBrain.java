package monopoly.agents.brains;

import monopoly.agents.buyLandStrategy.HoarderBuyLandStrategy;
import monopoly.agents.buyLandStrategy.RandomBuyLandStrategy;
import monopoly.agents.tradeStrategy.AFKTradeStrategy;
import monopoly.agents.tradeStrategy.HoarderTradeStrategy;

public class HoarderBrain extends AgentBrain{
    public HoarderBrain() {
        super(new HoarderTradeStrategy(), new HoarderBuyLandStrategy());
    }


}
