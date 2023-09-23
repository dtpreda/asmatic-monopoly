package monopoly.agents.visitors.dealer;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.TradePerformed;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Player;
import monopoly.models.Trade;
import monopoly.models.lands.Property;
import monopoly.states.TradeState;

public class TradePerformedVisitor extends DealerMessageVisitor {
    public TradePerformedVisitor(MonopolyController controller, ContentManager contentManager) {
        super(controller, contentManager);
    }

    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException, InvalidMessage {
        TradePerformed tradePerformed = (TradePerformed) content;
        Trade trade = tradePerformed.getTrade();
        Property prop = (Property) monopolyController.getBoard().getLand(trade.getProperty().getId());
        Player buyer = monopolyController.getBoard().getPlayer(trade.getBuyer().getName());
        Player seller = monopolyController.getBoard().getPlayer(trade.getSeller().getName());
        trade = new Trade(prop, trade.getPrice(), buyer, seller);
        if(!(monopolyController.getState() instanceof TradeState)) {
            throw new InvalidMessage("Received trade performed message while not in trade state");
        }

        TradeState tradeState = (TradeState) monopolyController.getState();

        boolean success = tradeState.performTrade(trade);
        return null;
    }
}
