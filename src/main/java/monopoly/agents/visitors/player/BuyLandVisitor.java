package monopoly.agents.visitors.player;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.*;
import monopoly.agents.visitors.PlayerMessageVisitor;
import monopoly.models.Player;
import monopoly.models.lands.buyStrategy.Purchasable;

public class BuyLandVisitor extends PlayerMessageVisitor {

    public BuyLandVisitor(ContentManager contentManager){
        super(contentManager);
    }

    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException {
        BuyLand buyLand = (BuyLand) content;
        ACLMessage reply = message.createReply();
        boolean canBuy = false;
        //To buy or not to buy
        Player player = buyLand.getPlayer();
        if(buyLand.getLand().getBuyStrategy() instanceof Purchasable){
            Purchasable purchasable = (Purchasable) buyLand.getLand().getBuyStrategy();
            if(purchasable.canPurchase() && player.getMoney() >= purchasable.getPrice()){
                canBuy = true;
            }
        }


        if(canBuy){
            contentManager.fillContent(reply, new PerformBuyLand());
        } else {
            contentManager.fillContent(reply, new EndTurn());
        }
        return reply;
    }


}
