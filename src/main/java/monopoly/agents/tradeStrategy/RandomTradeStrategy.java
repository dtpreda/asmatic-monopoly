package monopoly.agents.tradeStrategy;

import jade.content.ContentManager;
import jade.lang.acl.ACLMessage;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;

public class RandomTradeStrategy implements TradeStrategy {
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message) {
        return null;
    }

    @Override
    public Trade startTrade(MonopolyBoard board, Player player) {
        return null;
    }
}
