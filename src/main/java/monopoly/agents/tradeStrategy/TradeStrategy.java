package monopoly.agents.tradeStrategy;

import jade.content.ContentManager;
import jade.lang.acl.ACLMessage;

public interface TradeStrategy {
    ACLMessage processTrade(ContentManager contentManager, ACLMessage message);
}
