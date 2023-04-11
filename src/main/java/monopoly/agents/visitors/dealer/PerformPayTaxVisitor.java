package monopoly.agents.visitors.dealer;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.EndTurn;
import monopoly.actions.PerformPayTax;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Player;
import monopoly.states.PayRentState;

public class PerformPayTaxVisitor extends DealerMessageVisitor {
    public PerformPayTaxVisitor(MonopolyController controller, ContentManager contentManager) {
        super(controller, contentManager);
    }

    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException, InvalidMessage {
        PerformPayTax endTurn = (PerformPayTax) content;
        Player player = getPlayer(message);
        if(!isCurrentPlayer(player)){
            throw new InvalidMessage("Player " + player.getName() + " is not the current player, can't pay tax");
        }
        if(!(monopolyController.getState() instanceof PayRentState)){
            System.out.println(" state = " + monopolyController.getState() + " is not a PayRentState");
            throw new InvalidMessage("Player " + player.getName() + " is not in the pay rent state, can't pay tax ");

        }
        PayRentState state = (PayRentState) monopolyController.getState();
        boolean success = state.payRent(player);
        if(!success){
            throw new InvalidMessage("Player " + player.getName() + " can't pay tax " );
        }
        return null;
    }


}
