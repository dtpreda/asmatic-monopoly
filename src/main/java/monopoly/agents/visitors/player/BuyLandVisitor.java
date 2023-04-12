package monopoly.agents.visitors.player;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.BuyLand;
import monopoly.actions.EndTurn;
import monopoly.actions.RollDice;
import monopoly.actions.StartTurn;
import monopoly.agents.visitors.PlayerMessageVisitor;

public class BuyLandVisitor extends PlayerMessageVisitor {

    public BuyLandVisitor(ContentManager contentManager){
        super(contentManager);
    }

    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException {
        BuyLand startTurn = (BuyLand) content;
        ACLMessage reply = message.createReply();
        contentManager.fillContent(reply, new EndTurn());

        //To buy or not to buy

        return reply;
    }


}
