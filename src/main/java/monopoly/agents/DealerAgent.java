package monopoly.agents;

import jade.content.ContentElement;
import jade.content.Predicate;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import monopoly.actions.*;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.agents.visitors.dealer.*;
import monopoly.controllers.MonopolyController;
import monopoly.exceptions.InvalidMessage;
import monopoly.models.Player;
import monopoly.states.GameOverState;
import monopoly.states.TradeState;

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
        visitors.put(AttemptJailBreak.class, new DealerJailBreakVisitor(monopolyController, getContentManager()));
        visitors.put(PerformBuyLand.class, new PerformBuyLandVisitor(monopolyController, getContentManager()));
        visitors.put(ReadyAction.class, new DealerReadyVisitor(monopolyController, getContentManager()));
        visitors.put(BuyHouse.class, new DealerBuyHouseVisitor(monopolyController, getContentManager()));
        visitors.put(SellHouse.class, new SellHouseVisitor(monopolyController, getContentManager()));
        visitors.put(AbandonGame.class, new AbandonGameVisitor(monopolyController, getContentManager()));
        visitors.put(TradePerformed.class, new TradePerformedVisitor(monopolyController, getContentManager()));

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
            if(monopolyController.getState() instanceof GameOverState){
                System.out.println("Game finished");
                myAgent.removeBehaviour(this);
                return;
            }
            if(monopolyController.getState() instanceof TradeState){
                //System.out.println("Trade state behaviour");
                tradeStateBehaviour();
                return;
            }

            List<Player> players = monopolyController.getBoard().getPlayers();
            Player currentPlayer = monopolyController.getBoard().getCurrentPlayer();

            if(startNewTurn) {
                //Send message to player
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

                    try {
                        ContentElement content = myAgent.getContentManager().extractContent(msg);
                        DealerMessageVisitor visitor = visitors.get(content.getClass());
                        if (visitor != null) {
                            ACLMessage reply = visitor.visit(content, msg);
                            if (reply != null) {
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
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void tradeStateBehaviour(){
            List<Player> players = monopolyController.getBoard().getPlayers();
            for(Player player : players) {
                AID aid = new AID(player.getName(), AID.ISLOCALNAME);
                if(player.isBankrupt()){
                    continue;
                }
                ACLMessage startMessage = new ACLMessage(ACLMessage.PROPOSE);
                startMessage.setOntology(MonopolyOntology.ONTOLOGY_NAME);
                startMessage.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
                startMessage.addReceiver(aid);
                Predicate predicate = new TradeStateAction(monopolyController.getBoard(), player);
                try {
                    getContentManager().fillContent(startMessage, predicate);
                } catch (Codec.CodecException | OntologyException e) {
                    e.printStackTrace();
                }
                send(startMessage);
            }
            List<Class> acceptedClasses = List.of(ReadyAction.class, BuyHouse.class, TradePerformed.class);
            //Receive messages
            while (true) {
                ACLMessage message = receive();
                TradeState state = (TradeState) monopolyController.getState();

                if(message != null) {
                    try {
                        ContentElement content = getContentManager().extractContent(message);
                        if(!acceptedClasses.contains(content.getClass())){
                            postMessage(message);
                            continue;
                        }
                        ACLMessage reply =
                            visitors.get(content.getClass()).visit(content, message);
                        if(reply != null) {
                            send(reply);
                        }
                    }
                    catch (Codec.CodecException | OntologyException e) {
                        e.printStackTrace();
                    } catch (InvalidMessage e) {
                        System.out.println("Received invalid message: " + e.getMessage());
                    }
                    if(!(monopolyController.getState() instanceof TradeState)) {
                        return;
                    }
                } else {
                    block();
                }

            }
        }



        private void handleMessage(ACLMessage message){

        }
    }
}


