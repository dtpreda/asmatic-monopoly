package monopoly.agents.brains;

import monopoly.agents.buyLandStrategy.RandomBuyLandStrategy;
import monopoly.agents.buyLandStrategy.TortoiseBuyLandStrategy;
import monopoly.agents.tradeStrategy.AFKTradeStrategy;
import monopoly.agents.tradeStrategy.TortoiseTradeStrategy;

public class TortoiseBrain extends AgentBrain{
    public TortoiseBrain() {
        super(new TortoiseTradeStrategy(), new TortoiseBuyLandStrategy());
    }


}
