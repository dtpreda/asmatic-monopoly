package monopoly.agents.visitors.dealer;

import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.BuyLand;
import monopoly.actions.PayTax;
import monopoly.actions.StartTurn;
import monopoly.models.PlayResult;
import monopoly.models.lands.Land;

public class DealerVisitorUtils {
    public static ACLMessage playResult(ContentManager contentManager, PlayResult result, ACLMessage reply) throws OntologyException, Codec.CodecException {
        Land land = result.getLandedLand();
        switch (result.getPlayResultToken()){
            case BUY_LAND:
                contentManager.fillContent(reply, new BuyLand());
                return reply;
            case PLAY_AGAIN:
                contentManager.fillContent(reply, new StartTurn());
                return reply;
            case PAY_RENT:
                PayTax tax = new PayTax(land.getRentStrategy().getRent(result.getDice().getValue()));
                contentManager.fillContent(reply, tax);
                return reply;
            case END_TURN:
                return null;
            case JAIL:
                return null;
        }
        return null;

    }
}
