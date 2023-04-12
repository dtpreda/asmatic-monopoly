package monopoly.agents.visitors.player;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.BuyHouse;
import monopoly.actions.ReadyAction;
import monopoly.actions.TradeStateAction;
import monopoly.agents.visitors.PlayerMessageVisitor;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;

import java.util.List;

public class PlayerTradeStateVisitor extends PlayerMessageVisitor {
    public PlayerTradeStateVisitor(ContentManager contentManager) {
        super(contentManager);
    }
    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException {
        final TradeStateAction tradeState = (TradeStateAction) content;
        final MonopolyBoard board = tradeState.getBoard();


        ACLMessage reply = message.createReply();

        //TODO - implement trades

        //Buy houses if possible
        Property property = chooseHouseToBuy(board, tradeState.getPlayer());
        if(property != null){
            System.out.println("Player " + tradeState.getPlayer().getName() + " is buying a house on " + property.getName());
            contentManager.fillContent(reply, new BuyHouse(property));
            return reply;
        }
        System.out.println("Player " + tradeState.getPlayer().getName() + " is ready");

        contentManager.fillContent(reply, new ReadyAction());
        return reply;
    }

    private Property chooseHouseToBuy(MonopolyBoard board, Player player){
        List<Property> playerProperties = board.getProperties(player);


        for(Property property : playerProperties){
            List<Property> propertiesSameColor = board.getProperties(property.getColor());

            // See if all properties in the same color are owned by the player
            boolean canBuyHouses = true;
            for(Property propertySameColor : propertiesSameColor){
                Purchasable purchasable = (Purchasable) propertySameColor.getBuyStrategy();
                if(!player.getName().equals(purchasable.getOwner())){
                    canBuyHouses = false;
                    break;
                }
            }
            if(!canBuyHouses){
                continue;
            }

            //Get properties with the least houses
            int minHouses = Integer.MAX_VALUE;
            for(Property propertySameColor : propertiesSameColor){
                if(propertySameColor.getBuilding() < minHouses){
                    minHouses = propertySameColor.getBuilding();
                }
            }
            //Choose random property with the least houses
            for(Property propToUpgrade : propertiesSameColor){
                if(propToUpgrade.getBuilding() == minHouses){
                    return propToUpgrade;
                }
            }
        }
        return null;
    }
}
