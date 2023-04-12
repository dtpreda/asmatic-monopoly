package monopoly.agents;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import monopoly.actions.*;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.agents.visitors.PlayerMessageVisitor;
import monopoly.agents.visitors.dealer.RollDiceVisitor;
import monopoly.agents.visitors.player.*;
import monopoly.states.TradeState;

import java.util.HashMap;
import java.util.Map;

public class PlayerAgent extends Agent {

    private Map<Class, PlayerMessageVisitor> visitors;

    @Override
    protected void setup() {
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);
        getContentManager().registerOntology(MonopolyOntology.getInstance());

        visitors = new HashMap<>();
        visitors.put(StartTurn.class ,new StartTurnVisitor(getContentManager()));
        visitors.put(BuyLand.class, new BuyLandVisitor(getContentManager()));
        visitors.put(PayTax.class, new PayTaxVisitor(getContentManager()));
        visitors.put(PrisonAction.class, new PlayerPrisonVisitor(getContentManager()));
        visitors.put(TradeStateAction.class, new PlayerTradeStateVisitor(getContentManager()));
        visitors.put(NeedToSell.class, new NeedToSellVisitor(getContentManager()));
        addBehaviour(new PlayerListeningBehaviour());
    }

    class PlayerListeningBehaviour extends CyclicBehaviour {
        private boolean isTradeState = false;
        private ACLMessage lastTradeMsg = null;
        public void action() {
            System.out.println(getLocalName() + " is behaving");
            ACLMessage msg = receive();
            System.out.println("Player received message: " + getLocalName());
            if (msg != null) {
                try {
                    ContentElement content = getContentManager().extractContent(msg);
                    System.out.println("Player received message: " + content.getClass() + " - " + getLocalName());
                    if(content instanceof TradeStateAction){
                        lastTradeMsg = msg;
                        isTradeState = true;
                    }
                    ACLMessage reply = visitors.get(content.getClass()).visit(content, msg);
                    if(reply != null){
                        sendMsgToDealer(reply);
                    }

                } catch (Codec.CodecException | OntologyException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println(getLocalName() + " is waiting");
                block();
                System.out.println(getLocalName() + " stopped waiting");
            }

        }

        private void sendMsgToDealer(ACLMessage msg){
            ContentElement content = null;
            try {
                content = getContentManager().extractContent(msg);
                System.out.println("Player sending message: " + content.getClass());
                if(content instanceof ReadyAction){
                    isTradeState = false;
                    lastTradeMsg = null;
                }
            } catch (Codec.CodecException e) {
                throw new RuntimeException(e);
            } catch (OntologyException e) {
                throw new RuntimeException(e);
            }

            send(msg);
        }
        private void tradeBehaviour(){
            //TODO:: Do whatever the player wants, and then send a ReadyAction to Dealer when finished
            try {
                //This function could be somehow
                ContentElement content = getContentManager().extractContent(lastTradeMsg);
                ACLMessage reply = visitors.get(content.getClass()).visit(content, lastTradeMsg);
                if(reply != null){
                    sendMsgToDealer(reply);
                }
            } catch (OntologyException e) {
                throw new RuntimeException(e);
            } catch (Codec.CodecException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

