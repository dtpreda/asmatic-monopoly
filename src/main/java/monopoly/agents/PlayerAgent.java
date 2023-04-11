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
import monopoly.actions.RollDice;
import monopoly.actions.StartTurn;

public class PlayerAgent extends Agent {
    @Override
    protected void setup() {
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);
        getContentManager().registerOntology(MonopolyOntology.getInstance());

        addBehaviour(new PlayerListeningBehaviour());
        System.out.println("Player agent started");
    }

    class PlayerListeningBehaviour extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                System.out.println(msg);
                try {
                    ContentElement content = getContentManager().extractContent(msg);

                    if (content instanceof StartTurn) {
                        ACLMessage reply = msg.createReply();
                        RollDice rollDice = new RollDice();
                        getContentManager().fillContent(reply, rollDice);
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

