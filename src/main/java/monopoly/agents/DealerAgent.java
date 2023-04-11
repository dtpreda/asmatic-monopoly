package monopoly.agents;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import monopoly.actions.RollDice;
import monopoly.actions.StartTurn;
import monopoly.agents.visitors.MessageVisitor;
import monopoly.agents.visitors.dealer.RollDiceVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.models.PlayResult;
import monopoly.models.Player;
import monopoly.states.RollState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealerAgent extends Agent {
    private MonopolyController monopolyController;
    private Map<Class, MessageVisitor> visitors;
    public DealerAgent(MonopolyController monopolyController) {
        super();
        this.monopolyController = monopolyController;
        visitors = new HashMap<>();
        visitors.put(RollDice.class ,new RollDiceVisitor(monopolyController, getContentManager()));

    }
    @Override
    protected void setup() {
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);
        getContentManager().registerOntology(MonopolyOntology.getInstance());
        addBehaviour(new DealerBehaviour());

        System.out.println("Dealer agent started");
    }
    class DealerBehaviour extends CyclicBehaviour {
        public void action() {
            List<Player> players = monopolyController.getBoard().getPlayers();
            Player currentPlayer = monopolyController.getBoard().getCurrentPlayer();
            //Send message to player

            AID aid = new AID(currentPlayer.getName(), AID.ISLOCALNAME);

            ACLMessage startMessage = new ACLMessage(ACLMessage.PROPOSE);
            startMessage.setOntology(MonopolyOntology.ONTOLOGY_NAME);
            startMessage.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
            startMessage.addReceiver(aid);
            StartTurn startTurn = new StartTurn();
            try {
                getContentManager().fillContent(startMessage, startTurn);
            } catch (Codec.CodecException | OntologyException e) {
                e.printStackTrace();
            }
            send(startMessage);

            //Receive message

            //Advance turn
            RollState state = (RollState) monopolyController.getState();
            ACLMessage msg = receive();
            if (msg != null) {
                System.out.println(msg);
                try {
                    ContentElement content = myAgent.getContentManager().extractContent(msg);
                    if (content instanceof RollDice) {
                        //Check if it's current player

                    }
                } catch (Codec.CodecException | OntologyException e) {
                    e.printStackTrace();
                }
            } else {
                block();
            }

            //state.finishTurn(currentPlayer);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }



        private void handleMessage(ACLMessage message){

        }
    }
}


