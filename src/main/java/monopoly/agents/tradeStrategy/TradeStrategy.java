package monopoly.agents.tradeStrategy;

import jade.content.ContentManager;
import jade.lang.acl.ACLMessage;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;

public interface TradeStrategy {
    ACLMessage processTrade(ContentManager contentManager, ACLMessage message, String current);

    Trade startTrade(MonopolyBoard board, Player player);
}
