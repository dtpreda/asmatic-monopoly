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

public class PickerTradeStrategy implements TradeStrategy {
    private final static int PICKER_THRESHOLD = 250;
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message) {
        try {
            ContentElement content = contentManager.extractContent(message);
            ProposeTrade trade = (ProposeTrade) content;

            ACLMessage reply = message.createReply();

            if (trade.getTrade().getProperty().getPrice() >= PICKER_THRESHOLD) {
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
        } catch (Codec.CodecException | OntologyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Trade startTrade(MonopolyBoard board, Player player) {
        return null;
    }
}
