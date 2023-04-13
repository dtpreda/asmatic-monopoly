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

import java.util.List;

public class RandomTradeStrategy implements TradeStrategy {
    @Override
    public ACLMessage processTrade(ContentManager contentManager, ACLMessage message) {
        try {
            ContentElement content = contentManager.extractContent(message);
            ProposeTrade trade = (ProposeTrade) content;

            ACLMessage reply = message.createReply();

            double randomValue = Math.random();

            if (randomValue < 0.33) {
                reply.setPerformative(ACLMessage.REFUSE);
                System.out.println(trade.getTrade().getSeller().getName() + " refusing trade with " + trade.getTrade().getBuyer().getName());
            } else if (randomValue < 0.66) {
                reply.setPerformative(ACLMessage.PROPOSE);
                try {
                    contentManager.fillContent(reply, content);
                } catch (Codec.CodecException | OntologyException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(trade.getTrade().getSeller().getName() + " accepting trade with " + trade.getTrade().getBuyer().getName());
            } else {
                reply.setPerformative(ACLMessage.PROPOSE);
                Trade newTrade = new Trade(trade.getTrade().getProperty(), trade.getTrade().getPrice() + 100, trade.getTrade().getBuyer(), trade.getTrade().getSeller());
                ProposeTrade newContent = new ProposeTrade(newTrade);
                try {
                    contentManager.fillContent(reply, newContent);
                } catch (Codec.CodecException | OntologyException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(trade.getTrade().getSeller().getName() + " counter trading with " + trade.getTrade().getBuyer().getName());
            }

            return reply;
        } catch (Codec.CodecException | OntologyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Trade startTrade(MonopolyBoard board, Player player) {
        Dice dice = new Dice();
        if(dice.getValue() <= 6){
            return null;
        }


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
