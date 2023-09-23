package monopoly.agents.visitors.player;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.*;
import monopoly.agents.brains.AgentBrain;
import monopoly.agents.visitors.PlayerMessageVisitor;

public class PlayerPrisonVisitor extends PlayerMessageVisitor {

    public PlayerPrisonVisitor(ContentManager contentManager, AgentBrain brain){
        super(contentManager, brain);
    }

    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException {
        PrisonAction prisonAction = (PrisonAction) content;
        ACLMessage reply = message.createReply();
        contentManager.fillContent(reply, new AttemptJailBreak());
        return reply;
    }


}
