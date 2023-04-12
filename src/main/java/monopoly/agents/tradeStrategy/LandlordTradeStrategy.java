package monopoly.agents.tradeStrategy;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.ProposeTrade;

public class LandlordTradeStrategy implements TradeStrategy {
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message) {
        ContentElement content = null;
        try {
            content = contentManager.extractContent(message);
        } catch (Codec.CodecException | OntologyException e) {
            throw new RuntimeException(e);
        }

        ProposeTrade trade = (ProposeTrade) content;

        ACLMessage reply = message.createReply();

        if (trade.getTrade().getProperty().getBuilding() > 0) {
            reply.setPerformative(ACLMessage.REFUSE);
        } else {
            reply.setPerformative(ACLMessage.PROPOSE);
            try {
                contentManager.fillContent(reply, content);
            } catch (Codec.CodecException | OntologyException e) {
                throw new RuntimeException(e);
            }
        }

        return reply;
    }
}
