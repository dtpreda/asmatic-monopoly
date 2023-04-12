package monopoly.agents.visitors.dealer;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.AbandonGame;
import monopoly.actions.NeedToSell;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Player;
import monopoly.states.GameOverState;
import monopoly.states.RollState;
import monopoly.states.SellStuffState;
import monopoly.states.TradeState;

public class AbandonGameVisitor extends DealerMessageVisitor {
    public AbandonGameVisitor(MonopolyController controller, ContentManager contentManager) {
        super(controller, contentManager);
    }

    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException, InvalidMessage {
        AbandonGame abandonGame = (AbandonGame) content;
        final Player player = getPlayer(message);

        //TODO:: implement action
        monopolyController.bankRuptPlayer(player);

        if(monopolyController.getBoard().isGameFinished()){
            monopolyController.getState().changeState(new GameOverState(monopolyController.getBoard(), monopolyController));
        }

        if(monopolyController.getState() instanceof SellStuffState){
            SellStuffState state = (SellStuffState) monopolyController.getState();
            state.nextPlayer();
            state.changeState(new TradeState(monopolyController.getBoard(), monopolyController));

        }
        return null;
    }
}
