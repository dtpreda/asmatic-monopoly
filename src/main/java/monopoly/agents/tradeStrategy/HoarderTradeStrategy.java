package monopoly.agents.tradeStrategy;

import jade.content.ContentManager;
import jade.lang.acl.ACLMessage;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;

import java.util.List;

public class HoarderTradeStrategy implements TradeStrategy {
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message) {
        ACLMessage reply = message.createReply();
        reply.setPerformative(ACLMessage.REFUSE);
        return reply;
    }

    @Override
    public Trade startTrade(MonopolyBoard board, Player player) {
        List<Property> properties = board.getOwnedPropertiesNotPlayer(player.getName());
        if(properties.size() == 0){
            return null;
        }
        System.out.println("Number of properties " + properties.size());
        //Choose random property
        int randomProperty = (int) (Math.random() * properties.size());
        Property property = properties.get(randomProperty);

        //Choose price
        int price = (int) ((Math.random() + 0.8) * property.getPrice());

        Player playerToTrade = board.getPlayer(((Purchasable) property.getBuyStrategy()).getOwner());
        System.out.println("---- " + playerToTrade);
        System.out.println("PLAYER " + ((Purchasable) property.getBuyStrategy()).getOwner());
        System.out.println("Player " + player.getName() + " is trading " + property.getName() + " for " + price + " with " + playerToTrade.getName());
        return new Trade(property, price, player, playerToTrade);
    }
}
