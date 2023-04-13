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
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message) {
        try {
            ContentElement content = contentManager.extractContent(message);
            ProposeTrade trade = (ProposeTrade) content;

            ACLMessage reply = message.createReply();

            double randomValue = Math.random();

            if (randomValue < 0.33) {
                reply.setPerformative(ACLMessage.REFUSE);
            } else if (randomValue < 0.66) {
                reply.setPerformative(ACLMessage.PROPOSE);
                try {
                    contentManager.fillContent(reply, content);
                } catch (Codec.CodecException | OntologyException e) {
                    throw new RuntimeException(e);
                }
            } else {
                reply.setPerformative(ACLMessage.PROPOSE);
                Trade newTrade = new Trade(trade.getTrade().getProperty(), trade.getTrade().getPrice() + 100, trade.getTrade().getBuyer(), trade.getTrade().getSeller());
                ProposeTrade newContent = new ProposeTrade(newTrade);
                try {
                    contentManager.fillContent(reply, newContent);
                } catch (Codec.CodecException | OntologyException e) {
                    throw new RuntimeException(e);
                }
            }

            return reply;
        } catch (Codec.CodecException | OntologyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Trade startTrade(MonopolyBoard board, Player player) {
        return null;
    }
}
