package monopoly.agents.visitors.dealer;

import jade.content.ContentElement;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.lang.acl.ACLMessage;
import monopoly.actions.ReadyAction;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.states.TradeState;

public class DealerReadyVisitor extends DealerMessageVisitor {

    public DealerReadyVisitor(MonopolyController controller, ContentManager contentManager) {
        super(controller, contentManager);
    }
    @Override
    public ACLMessage visit(ContentElement content, ACLMessage message) throws OntologyException, Codec.CodecException, InvalidMessage {
        ReadyAction readyAction = (ReadyAction) content;
        if(!(monopolyController.getState() instanceof TradeState)){
            throw new InvalidMessage("Not in trade state, can't ready");
        }
        TradeState state = (TradeState) monopolyController.getState();
        state.playerReady(getPlayer(message));

        if(state.canStart()){
            boolean success = state.start();

            if(success){
                return null;
            }
        }

        return null;
    }
}
