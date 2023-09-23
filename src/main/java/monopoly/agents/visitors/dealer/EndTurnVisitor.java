package monopoly.agents.visitors.dealer;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.EndTurn;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Player;
import monopoly.states.BuyLandState;
import monopoly.states.MonopolyState;

public class EndTurnVisitor extends DealerMessageVisitor {
    public EndTurnVisitor(MonopolyController controller, ContentManager contentManager) {
        super(controller, contentManager);
    }

    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException, InvalidMessage {
        EndTurn endTurn = (EndTurn) content;
        Player player = getPlayer(message);
        if(!isCurrentPlayer(player)){
            throw new InvalidMessage("Player " + player.getName() + " is not the current player, can't end turn");
        }
        MonopolyState state = monopolyController.getState();
        if(state instanceof BuyLandState){
            ((BuyLandState)state).endState(player);
            return null;
        }

        throw new InvalidMessage("Player " + player.getName() + "  can't end turn in state " + state);
    }


}
