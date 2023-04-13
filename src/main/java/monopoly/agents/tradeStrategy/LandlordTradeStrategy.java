package monopoly.agents.tradeStrategy;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.ProposeTrade;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;

import java.util.List;

public class LandlordTradeStrategy implements TradeStrategy {
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message) {
        try {
            ContentElement content = contentManager.extractContent(message);
            ProposeTrade trade = (ProposeTrade) content;

            ACLMessage reply = message.createReply();

            if (trade.getTrade().getProperty().getBuilding() > 0) {
                reply.setPerformative(ACLMessage.REFUSE);
            } else {
                reply.setPerformative(ACLMessage.PROPOSE);
                try {
                    contentManager.fillContent(reply, content);
                } catch (Codec.CodecException | OntologyException e) {
                    throw new RuntimeException(e);
                }
            }

            return reply;
        } catch (Codec.CodecException | OntologyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Trade startTrade(MonopolyBoard board, Player player) {
        final List<Property> properties = board.getProperties(player);
        final List<Property> ownedByOthers = board.getOwnedPropertiesNotPlayer(player.getName());

        for (Property property : properties) {
            for (Property other : ownedByOthers) {
                if (other.getColor().equals(property.getColor())) {
                    int price = (int) ((Math.random()/2.0 + 1.0) * property.getPrice());
                    Purchasable otherPurchasable = (Purchasable) other.getBuyStrategy();
                    Player owner = board.getPlayer(otherPurchasable.getOwner());
                    return new Trade(property, price, player, owner);
                }
            }
        }
        return null;
    }
}
