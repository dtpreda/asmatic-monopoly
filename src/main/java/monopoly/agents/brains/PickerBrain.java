package monopoly.agents.brains;

import monopoly.agents.buyLandStrategy.PickerBuyLandStrategy;
import monopoly.agents.buyLandStrategy.RandomBuyLandStrategy;
import monopoly.agents.tradeStrategy.AFKTradeStrategy;
import monopoly.agents.tradeStrategy.PickerTradeStrategy;

public class PickerBrain extends AgentBrain{
    public PickerBrain() {
        super(new PickerTradeStrategy(), new PickerBuyLandStrategy());
    }


}
