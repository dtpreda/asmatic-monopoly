package monopoly.agents.visitors.player;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.PayTax;
import monopoly.actions.PerformPayTax;
import monopoly.actions.RollDice;
import monopoly.actions.StartTurn;
import monopoly.agents.visitors.PlayerMessageVisitor;

public class PayTaxVisitor extends PlayerMessageVisitor {

    public PayTaxVisitor(ContentManager contentManager){
        super(contentManager);
    }

    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException {
        PayTax payTax = (PayTax) content;
        ACLMessage reply = message.createReply();
        contentManager.fillContent(reply, new PerformPayTax());
        return reply;
    }


}
