package monopoly.agents.visitors.dealer;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.BuyLand;
import monopoly.actions.PayTax;
import monopoly.actions.RollDice;
import monopoly.actions.StartTurn;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Dice;
import monopoly.models.PlayResult;
import monopoly.models.Player;
import monopoly.models.lands.Land;
import monopoly.states.RollState;

public class RollDiceVisitor extends DealerMessageVisitor {


    public RollDiceVisitor(MonopolyController controller, ContentManager contentManager) {
        super(controller, contentManager);
    }

    public ACLMessage visit(ContentElement element, ACLMessage message) throws OntologyException, Codec.CodecException, InvalidMessage {
        final RollDice rollDice = (RollDice) element;
        final String playerName = message.getSender().getLocalName();
        System.out.println("------------------------------------");
        System.out.println("Dealer: " + playerName + " rolled dice");
        System.out.println("Current state: " + monopolyController.getState());
        System.out.println("Current player: " + monopolyController.getBoard().getCurrentPlayer().getName());
        if(!isCurrentPlayer(playerName)) {
            throw new InvalidMessage("Not current player: " + playerName);
        }
        final Player player = getPlayer(playerName);

        if(monopolyController.getState() instanceof RollState) {
            final RollState state = (RollState) monopolyController.getState();
            final PlayResult result = state.play(player);
            final Dice dice = result.getDice();
            final ACLMessage reply = message.createReply();

            //PLAY_AGAIN,END_TURN,PAY_RENT, BUY_LAND, JAIL
            System.out.println("Dealer: playResult: " + result.getPlayResultToken());
            return DealerVisitorUtils.playResult(contentManager, result, reply, monopolyController.getBoard());
        }

        throw new InvalidMessage("Invalid state: " + monopolyController.getState() + " for roll dice " + rollDice);
    }
}
