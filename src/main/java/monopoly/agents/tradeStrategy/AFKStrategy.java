package monopoly.agents.tradeStrategy;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.ProposeTrade;

public class AFKStrategy implements Strategy {
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message) {
        ACLMessage reply = message.createReply();
        reply.setPerformative(ACLMessage.REFUSE);
        return reply;
    }
}
