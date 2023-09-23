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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PickerTradeStrategy implements TradeStrategy {
    public final static int PICKER_THRESHOLD = 250;
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message, String current) {
        try {
            ContentElement content = contentManager.extractContent(message);
            ProposeTrade trade = (ProposeTrade) content;

            ACLMessage reply = message.createReply();

            boolean priceAcceptable = trade.getTrade().getPrice() >= trade.getTrade().getProperty().getPrice();
            boolean wontSell = !priceAcceptable || trade.getBoard().ownsAllPropertiesColor(current, trade.getTrade().getProperty().getColor());
            if (wontSell || trade.getTrade().getProperty().getPrice() >= PICKER_THRESHOLD) {
                System.out.println("Picker refused the trade:");
                System.out.println("Refused trade: " + trade.getTrade().getProperty().getName() + " " + trade.getTrade().getProperty().getPrice() + " " + trade.getTrade().getPrice());
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
        List<Property> properties = board.getOwnedPropertiesNotPlayer(player.getName());
        Collections.reverse(properties);
        for (Property property : properties) {
            if (property.getPrice() >= PICKER_THRESHOLD) {
                int price = (int) ((Math.random()/4.0 + 1.0) * property.getPrice());
                Purchasable otherPurchasable = (Purchasable) property.getBuyStrategy();
                if(player.getMoney() < price) {
                    continue;
                }

                return new Trade(property, price, player,  board.getPlayer(otherPurchasable.getOwner()));
            }
        }
        return null;
    }
}
