package monopoly.agents;

import jade.content.ContentElement;
import jade.content.Predicate;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import monopoly.actions.*;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.agents.visitors.dealer.DealerPrisonVisitor;
import monopoly.agents.visitors.dealer.EndTurnVisitor;
import monopoly.agents.visitors.dealer.PerformPayTaxVisitor;
import monopoly.agents.visitors.dealer.RollDiceVisitor;
import monopoly.agents.visitors.player.StartTurnVisitor;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DealerAgent extends Agent {
    private MonopolyController monopolyController;
    private Map<Class, DealerMessageVisitor> visitors;

    private boolean startNewTurn = true;
    public DealerAgent(MonopolyController monopolyController) {
        super();
        this.monopolyController = monopolyController;
        visitors = new HashMap<>();
        visitors.put(RollDice.class ,new RollDiceVisitor(monopolyController, getContentManager()));
        visitors.put(EndTurn.class ,new EndTurnVisitor(monopolyController, getContentManager()));
        visitors.put(PerformPayTax.class, new PerformPayTaxVisitor(monopolyController, getContentManager()));
        visitors.put(AttemptJailBreak.class, new DealerPrisonVisitor(monopolyController, getContentManager()));

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

            if(startNewTurn) {
                //Send message to player)
                AID aid = new AID(currentPlayer.getName(), AID.ISLOCALNAME);

                ACLMessage startMessage = new ACLMessage(ACLMessage.PROPOSE);
                startMessage.setOntology(MonopolyOntology.ONTOLOGY_NAME);
                startMessage.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
                startMessage.addReceiver(aid);

                Predicate predicate;
                if(currentPlayer.isJailed()) {
                    predicate = new PrisonAction();
                } else {
                    predicate = new StartTurn();
                }
                try {
                    getContentManager().fillContent(startMessage, predicate);
                } catch (Codec.CodecException | OntologyException e) {
                    e.printStackTrace();
                }
                send(startMessage);
            }
            ACLMessage msg;
            do{
                msg = receive();
                if (msg != null) {
                    //(System.out.println(msg);
                    try {
                        ContentElement content = myAgent.getContentManager().extractContent(msg);
                        DealerMessageVisitor visitor = visitors.get(content.getClass());
                        if (visitor != null) {
                            ACLMessage reply = visitor.visit(content, msg);
                            if (reply != null) {
                                System.out.println("Replied");
                                System.out.println("Replied with state " + monopolyController.getState());
                                send(reply);
                                startNewTurn = false;
                            } else {
                                // no reply, player was changed
                                startNewTurn = true;
                            }
                        } else {
                            System.err.println("No visitor found for " + content.getClass());
                        }
                    } catch (Codec.CodecException | OntologyException e) {
                        e.printStackTrace();
                    } catch (InvalidMessage e) {
                        // Message was invalid, ignore it
                        System.out.println("Received invalid message: " + e.getMessage());
                    }
                } else {
                    block();
                }
            }while(msg == null);

            //state.finishTurn(currentPlayer);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


        private void handleMessage(ACLMessage message){

        }
    }
}


