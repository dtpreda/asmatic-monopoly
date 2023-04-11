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
import monopoly.actions.BuyLand;
import monopoly.actions.PayTax;
import monopoly.actions.RollDice;
import monopoly.actions.StartTurn;
import monopoly.agents.visitors.DealerMessageVisitor;
import monopoly.agents.visitors.PlayerMessageVisitor;
import monopoly.agents.visitors.dealer.RollDiceVisitor;
import monopoly.agents.visitors.player.BuyLandVisitor;
import monopoly.agents.visitors.player.PayTaxVisitor;
import monopoly.agents.visitors.player.StartTurnVisitor;

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
        addBehaviour(new PlayerListeningBehaviour());
        System.out.println("Player agent started");
    }

    class PlayerListeningBehaviour extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                //System.out.println(msg);
                try {
                    ContentElement content = getContentManager().extractContent(msg);
                    System.out.println("Received message: " + content.getClass().getSimpleName());
                    ACLMessage reply = visitors.get(content.getClass()).visit(content, msg);
                    if(reply != null){
                        send(reply);
                    }

                } catch (Codec.CodecException | OntologyException e) {
                    e.printStackTrace();
                }
            } else {
                block();
            }
        }
    }
}

