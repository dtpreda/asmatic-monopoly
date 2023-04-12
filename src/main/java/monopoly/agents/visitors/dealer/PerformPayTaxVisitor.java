package monopoly.agents.visitors.dealer;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.EndTurn;
import monopoly.actions.NeedToSell;
import monopoly.actions.PerformPayTax;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Player;
import monopoly.states.PayRentState;
import monopoly.states.SellStuffState;

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

        if(monopolyController.getState() instanceof SellStuffState){
            SellStuffState state = (SellStuffState) monopolyController.getState();
            if(!state.goBack()){
                ACLMessage reply = message.createReply();
                contentManager.fillContent(reply, new NeedToSell(state.getAmountRequired(), player, monopolyController.getBoard()));
                return reply;
            }
            //Changed to PayRentState, so we can continue

        }
        if(!(monopolyController.getState() instanceof PayRentState)){
            System.out.println(" state = " + monopolyController.getState() + " is not a PayRentState");
            throw new InvalidMessage("Player " + player.getName() + " is not in the pay rent state, can't pay tax ");

        }
        PayRentState state = (PayRentState) monopolyController.getState();
        boolean success = state.payRent(player);
        if(!success){
            SellStuffState sellStuffState = new SellStuffState(monopolyController.getBoard(), monopolyController, state);
            monopolyController.getState().changeState(sellStuffState);
            ACLMessage reply = message.createReply();
            contentManager.fillContent(reply, new NeedToSell(state.getLand().getRentStrategy().getRent(1), player, monopolyController.getBoard()));
            System.out.println("Player " + player.getName() + " can't pay tax, need to sell stuff");
            return reply;
        }
        return null;
    }


}
