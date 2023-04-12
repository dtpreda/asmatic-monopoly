package monopoly.agents.tradeStrategy;

import jade.content.ContentManager;
import jade.lang.acl.ACLMessage;

public class HoarderTradeStrategy implements TradeStrategy {
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message) {
        ACLMessage reply = message.createReply();
        reply.setPerformative(ACLMessage.REFUSE);
        return reply;
    }
}
