package monopoly.agents.visitors.dealer;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.BuyHouse;
import monopoly.actions.ReadyAction;
import monopoly.actions.TradeStateAction;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Player;
import monopoly.models.lands.Property;
import monopoly.states.TradeState;

public class DealerBuyHouseVisitor extends DealerMessageVisitor {

    public DealerBuyHouseVisitor(MonopolyController controller, ContentManager contentManager) {
        super(controller, contentManager);
    }
    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException, InvalidMessage {
        BuyHouse buyHouse = (BuyHouse) content;
        if(!(monopolyController.getState() instanceof TradeState)){
            throw new InvalidMessage("Not in trade state, can't ready");
        }
        TradeState state = (TradeState) monopolyController.getState();
        final Player player = getPlayer(message);

        // Get property from board with id
        Property property = (Property) monopolyController.getBoard().getLand(buyHouse.getProperty().getId());
        state.buyHouse(property, player);

        if(state.canStart()){
            boolean success = state.start();

            if(success){
                //ok
            }
        }

        System.out.println("Dealer bought house, sending TradeStateAction again");
        ACLMessage reply = message.createReply();
        contentManager.fillContent(reply, new TradeStateAction(monopolyController.getBoard(), player));
        System.out.println("Dealer sent TradeStateAction again " + reply);
        return reply;
    }
}
