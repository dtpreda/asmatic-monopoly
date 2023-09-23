package monopoly.agents.tradeStrategy;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.ProposeTrade;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;

public class AFKTradeStrategy implements TradeStrategy {
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message, String current) {
        ACLMessage reply = message.createReply();
        reply.setPerformative(ACLMessage.REFUSE);
        return reply;
    }

    @Override
    public Trade startTrade(MonopolyBoard board, Player player) {
        return null;
    }
}
