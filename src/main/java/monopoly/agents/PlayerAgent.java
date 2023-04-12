package monopoly.agents;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;
import jade.proto.ContractNetResponder;
import monopoly.actions.*;
import monopoly.agents.tradeStrategy.Strategy;
import monopoly.agents.visitors.PlayerMessageVisitor;
import monopoly.agents.visitors.player.*;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;
import jade.core.AID;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PlayerAgent extends Agent {

    private Map<Class, PlayerMessageVisitor> visitors;

    private ContractNetInitiator initiator;
    private ContractNetResponder responder;

    private MonopolyBoard board;

    private Strategy agentType;


    public PlayerAgent() {}

    public PlayerAgent(Strategy agentType) {
        this.agentType = agentType;
    }

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

        initiator = this.generateInitiator();
        responder = this.generateResponder();

        addBehaviour(initiator);
        addBehaviour(responder);
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
                        PlayerAgent thisAgent = (PlayerAgent) myAgent;
                        thisAgent.board = ((TradeStateAction) content).getBoard();
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

    private ContractNetInitiator generateInitiator() {
        ACLMessage dummyMsg = new ACLMessage(ACLMessage.CFP);
        dummyMsg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
        return new ContractNetInitiator(this, dummyMsg) {
            @Override
            protected void handlePropose(ACLMessage propose, Vector acceptances) {
                try {
                    ContentElement content = getContentManager().extractContent(propose);
                    ProposeTrade trade = (ProposeTrade) content;

                    if (trade.getTrade().getPrice() < trade.getTrade().getBuyer().getMoney()) {

                    }
                } catch (Codec.CodecException | OntologyException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected void handleRefuse(ACLMessage refuse) {
                super.handleRefuse(refuse);
            }

            @Override
            protected void handleFailure(ACLMessage failure) {
                super.handleFailure(failure);
            }

            @Override
            protected void handleAllResponses(Vector responses, Vector acceptances) {
                super.handleAllResponses(responses, acceptances);
            }
        };
    }

    private ContractNetResponder generateResponder() {
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                MessageTemplate.MatchPerformative(ACLMessage.CFP));

        return new ContractNetResponder(this, template) {
            protected ACLMessage handleCfp(ACLMessage cfp) {
                if (cfp.getPerformative() == ACLMessage.CFP) {
                    PlayerAgent thisAgent = (PlayerAgent) myAgent;
                    return thisAgent.agentType.processTrade(getContentManager(), cfp);
                } else {
                    // If the received message is not valid, return a not understood message
                    ACLMessage notUnderstood = cfp.createReply();
                    notUnderstood.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                    return notUnderstood;
                }
            }

            protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) {
                // Handle accepted proposal
                return null;
            }

            protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
                // Handle rejected proposal
            }
        };
    }

    private void initiateTrade(Property property, int price) {
        Purchasable strategy = (Purchasable) property.getBuyStrategy();
        String receiver = strategy.getOwner();

        ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
        msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));

        Trade trade = new Trade(property, price, this.board.getPlayerByName(this.getLocalName()), this.board.getPlayerByName(receiver));
        ProposeTrade content = new ProposeTrade(trade);

        try {
            this.getContentManager().fillContent(msg, content);
        } catch (Codec.CodecException | OntologyException e) {
            e.printStackTrace();
        }
    }
}

