package monopoly.agents.visitors.dealer;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.NeedToSell;
import monopoly.actions.SellHouse;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Player;
import monopoly.models.lands.Property;
import monopoly.states.SellStuffState;

public class SellHouseVisitor extends DealerMessageVisitor {

    public SellHouseVisitor(MonopolyController controller, ContentManager contentManager) {
        super(controller, contentManager);
    }

    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException, InvalidMessage {
        SellHouse sellHouse = (SellHouse) content;
        Property property = (Property) monopolyController.getBoard().getLand(sellHouse.getProperty().getId());
        Player player = getPlayer(message);

        if(!(monopolyController.getState() instanceof SellStuffState)){
            throw  new InvalidMessage("Player " + player.getName() + " is not in the sell stuff state, can't sell house");
        }
        SellStuffState state = (SellStuffState) monopolyController.getState();
        if(!state.sellHouse(property)){
            throw  new InvalidMessage("Player " + player.getName() + " can't sell house");
        }
        final ACLMessage reply = message.createReply();
        contentManager.fillContent(reply, new NeedToSell(state.getAmountRequired(), player, monopolyController.getBoard()));
        return reply;
    }
}
