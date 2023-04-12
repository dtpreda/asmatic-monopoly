package monopoly.agents.visitors.player;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.ReadyAction;
import monopoly.actions.TradeStateAction;
import monopoly.agents.visitors.PlayerMessageVisitor;
import monopoly.models.MonopolyBoard;

public class PlayerTradeStateVisitor extends PlayerMessageVisitor {
    public PlayerTradeStateVisitor(ContentManager contentManager) {
        super(contentManager);
    }
    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException {
        final TradeStateAction tradeState = (TradeStateAction) content;
        final MonopolyBoard board = tradeState.getBoard();


        ACLMessage reply = message.createReply();

        //TODO - implement trades


        contentManager.fillContent(reply, new ReadyAction());
        return reply;
    }
}
