package monopoly.agents.visitors;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Player;

public abstract class MessageVisitor {

    protected MonopolyController monopolyController;
    protected ContentManager contentManager;
    public MessageVisitor(MonopolyController controller, ContentManager contentManager){
        this.monopolyController = controller;
        this.contentManager = contentManager;
    }


    public abstract ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException, InvalidMessage;



    protected boolean isCurrentPlayer(String name) {
        return monopolyController.getBoard().getCurrentPlayer().getName().equals(name);
    }

    protected Player getPlayer(String player){
        return monopolyController.getBoard().getPlayer(player);
    }
}
