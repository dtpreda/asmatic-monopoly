package monopoly.agents.visitors;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.models.MonopolyBoard;

public abstract class PlayerMessageVisitor {
    protected ContentManager contentManager;
    public PlayerMessageVisitor(ContentManager contentManager) {
        this.contentManager = contentManager;
    }

    public abstract ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException;
}
