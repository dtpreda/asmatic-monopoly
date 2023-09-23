package monopoly.agents.visitors.dealer;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.BuyLand;
import monopoly.actions.EndTurn;
import monopoly.actions.PerformBuyLand;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Player;
import monopoly.states.BuyLandState;
import monopoly.states.MonopolyState;

public class PerformBuyLandVisitor extends DealerMessageVisitor {
    public PerformBuyLandVisitor(MonopolyController controller, ContentManager contentManager) {
        super(controller, contentManager);
    }

    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException, InvalidMessage {
        PerformBuyLand performBuyLand = (PerformBuyLand) content;
        Player player = getPlayer(message);
        if(!isCurrentPlayer(player)){
            throw new InvalidMessage("Player " + player.getName() + " is not the current player, can't end turn");
        }
        MonopolyState state = monopolyController.getState();
        if(state instanceof BuyLandState){
            BuyLandState buyLandState = (BuyLandState)state;
            boolean success = buyLandState.buyLand(player);
            if(success) {
                buyLandState.endState(player);
                return null;
            } else {
                throw new InvalidMessage("Player " + player.getName() + "  can't purchase " + buyLandState.getLand());
            }
        }

        throw new InvalidMessage("Player " + player.getName() + "  can't end turn in state " + state);
    }


}
