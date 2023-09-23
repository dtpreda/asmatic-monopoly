package monopoly.agents.visitors;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.agents.brains.AgentBrain;
import monopoly.models.MonopolyBoard;

public abstract class PlayerMessageVisitor {
    protected ContentManager contentManager;
    protected AgentBrain brain;
    public PlayerMessageVisitor(ContentManager contentManager, AgentBrain brain) {
        this.contentManager = contentManager;
        this.brain = brain;
    }

    public abstract ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException;
}
