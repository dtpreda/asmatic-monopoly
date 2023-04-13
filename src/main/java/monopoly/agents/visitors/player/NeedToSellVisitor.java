package monopoly.agents.visitors.player;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.AbandonGame;
import monopoly.actions.NeedToSell;
import monopoly.actions.SellHouse;
import monopoly.agents.brains.AgentBrain;
import monopoly.agents.visitors.PlayerMessageVisitor;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Property;

import java.util.List;

public class NeedToSellVisitor extends PlayerMessageVisitor {


    public NeedToSellVisitor(ContentManager contentManager, AgentBrain brain) {
        super(contentManager, brain);
    }
    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException {
        NeedToSell needToSell = (NeedToSell) content;
        ACLMessage reply = message.createReply();
        MonopolyBoard board = needToSell.getBoard();
        Player player = needToSell.getPlayer();
        int amountRequired = needToSell.getAmountRequired();

        // Will prioritize selling houses with lower number of houses and then lower house cost

        //Get myProperties with houses
        List<Property> propertiesWithHouses = board.getProperties(player);
        propertiesWithHouses.removeIf(property -> property.getBuilding() <= 1);
        propertiesWithHouses.sort((o1, o2) -> {
            if(o1.getBuilding() == o2.getBuilding()){
                return o1.getHousePrice() - o2.getHousePrice();
            }
            return o1.getBuilding() - o2.getBuilding();
        });

        //Sell first house (more preferable)
        if(propertiesWithHouses.size() > 0){
            Property property = propertiesWithHouses.get(0);
            contentManager.fillContent(reply, new SellHouse(property));
            return reply;
        }

        //Can't sell houses, abandon game
        contentManager.fillContent(reply, new AbandonGame());
        return reply;
    }
}
