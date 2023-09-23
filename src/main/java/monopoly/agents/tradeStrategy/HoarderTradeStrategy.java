package monopoly.agents.tradeStrategy;

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

public class HoarderTradeStrategy implements TradeStrategy {
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message, String current) {
        ACLMessage reply = message.createReply();
        try {
            ProposeTrade proposeTrade = (ProposeTrade) contentManager.extractContent(message);
            Trade trade = proposeTrade.getTrade();
            MonopolyBoard board = proposeTrade.getBoard();
            boolean needMoney = trade.getSeller().getMoney() < 100 && Math.random() < 0.5;
            boolean cantSell = board.ownsAllPropertiesColor(current, trade.getProperty().getColor());
            if(cantSell || (!needMoney && !trade.getBuyer().equals(current))){
                System.out.println("Hoarder refused the trade:");
                System.out.println("Refused trade: " + trade.getProperty().getName() + " " + trade.getProperty().getPrice() + " " + trade.getPrice());
                reply.setPerformative(ACLMessage.REFUSE);
                return reply;
            } else {
                reply.setPerformative(ACLMessage.PROPOSE);
                contentManager.fillContent(reply, proposeTrade);
                return reply;
            }
        } catch (Codec.CodecException e) {
            throw new RuntimeException(e);
        } catch (OntologyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Trade startTrade(MonopolyBoard board, Player player) {
        List<Property> properties = board.getOwnedPropertiesNotPlayer(player.getName());
        if(properties.size() == 0){
            return null;
        }
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
