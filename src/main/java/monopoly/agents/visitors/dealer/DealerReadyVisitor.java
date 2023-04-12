package monopoly.agents.visitors.dealer;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.AttemptJailBreak;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.PlayResult;
import monopoly.models.Player;
import monopoly.states.JailState;

public class DealerReadyVisitor extends DealerMessageVisitor {
    public DealerReadyVisitor(MonopolyController controller, ContentManager contentManager) {
        super(controller, contentManager);
    }

    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException, InvalidMessage {
        AttemptJailBreak endTurn = (AttemptJailBreak) content;
        Player player = getPlayer(message);
        if(!isCurrentPlayer(player)){
            throw new InvalidMessage("Player " + player.getName() + " is not the current player, can't pay tax");
        }
        if(!(player.isJailed())){
            throw new InvalidMessage("Player " + player.getName() + " is not jailed, why breakout of prison?");
        }
        monopolyController.setState(new JailState(monopolyController.getBoard(), monopolyController));
        JailState state = (JailState) monopolyController.getState();
        PlayResult result = state.attemptJailBreak(player);
        if(result == null){
            return null;
        }
        ACLMessage reply = message.createReply();
        return DealerVisitorUtils.playResult(contentManager, result, reply, monopolyController.getBoard());
    }


}
