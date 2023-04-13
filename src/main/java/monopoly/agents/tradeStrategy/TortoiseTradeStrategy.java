package monopoly.agents.tradeStrategy;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.ProposeTrade;
import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;

import java.util.Collections;
import java.util.List;

public class TortoiseTradeStrategy implements TradeStrategy {
    public final static int TORTOISE_THRESHOLD = 160;
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message, String current) {
        try {
            ContentElement content = contentManager.extractContent(message);
            ProposeTrade trade = (ProposeTrade) content;

            ACLMessage reply = message.createReply();
            Property property = trade.getTrade().getProperty();
            boolean priceAcceptable = trade.getTrade().getPrice() >= property.getPrice();
            boolean wontSell = !priceAcceptable || trade.getBoard().ownsAllPropertiesColor(current, property.getColor());
            if (wontSell || property.getPrice() <= TORTOISE_THRESHOLD) {
                System.out.println("Tortoise refused the trade:");
                System.out.println("Refused trade: " + property.getName() + " " + property.getPrice() + " " + trade.getTrade().getPrice());
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
        for (Property property : properties) {
            if (property.getPrice() <= TORTOISE_THRESHOLD) {
                int price = (int) ((Math.random()/2.0 + 1.0) * property.getPrice());
                Purchasable otherPurchasable = (Purchasable) property.getBuyStrategy();
                if(player.getMoney() < price) {
                    continue;
                }
                if(new Dice().isDouble()){
                    continue;
                }
                return new Trade(property, property.getPrice(), player,  board.getPlayer(otherPurchasable.getOwner()));
            }
        }
        return null;
    }
}
