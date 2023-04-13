package monopoly.agents;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.OntologyException;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;
import jade.proto.ContractNetResponder;
import monopoly.actions.*;
import monopoly.agents.brains.AgentBrain;
import monopoly.agents.tradeStrategy.TradeStrategy;
import monopoly.agents.visitors.PlayerMessageVisitor;
import monopoly.agents.visitors.player.*;
import monopoly.models.MonopolyBoard;
import monopoly.models.Trade;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;
import jade.core.AID;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PlayerAgent extends Agent {

    private Map<Class, PlayerMessageVisitor> visitors;

    private ContractNetInitiator initiator;
    private ContractNetResponder responder;

    private MonopolyBoard board;

    private TradeStrategy agentType;
    private AgentBrain brain;

    private boolean canTrade = true;
    private boolean isTradeState;
    private boolean lastTradeMsg;

    public PlayerAgent(AgentBrain brain) {
        this.brain = brain;
    }

    public PlayerAgent(TradeStrategy agentType) {
        this.agentType = agentType;
    }

    @Override
    protected void setup() {
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);
        getContentManager().registerOntology(MonopolyOntology.getInstance());

        visitors = new HashMap<>();
        visitors.put(StartTurn.class ,new StartTurnVisitor(getContentManager(), brain));
        visitors.put(BuyLand.class, new BuyLandVisitor(getContentManager(), brain));
        visitors.put(PayTax.class, new PayTaxVisitor(getContentManager(), brain));
        visitors.put(PrisonAction.class, new PlayerPrisonVisitor(getContentManager(), brain));
        visitors.put(TradeStateAction.class, new PlayerTradeStateVisitor(getContentManager(), brain, this));
        visitors.put(NeedToSell.class, new NeedToSellVisitor(getContentManager(), brain));
        addBehaviour(new PlayerListeningBehaviour());

        //initiator = this.generateInitiator();
        responder = this.generateResponder();

        //addBehaviour(initiator);
        addBehaviour(responder);
    }

    class PlayerListeningBehaviour extends CyclicBehaviour {
        private boolean isTradeState = false;
        private ACLMessage lastTradeMsg = null;
        public void action() {
            System.out.println(getLocalName() + " is behaving");
            MessageTemplate template = MessageTemplate.not(MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET));
            ACLMessage msg = receive(template);
            System.out.println("Player  " + getLocalName());
            if (msg != null) {
                try {
                    System.out.println("Player received message: " + msg.getContent());
                    ContentElement content = getContentManager().extractContent(msg);
                    System.out.println("Player received message: " + content + " - " + getLocalName());
                    if(content instanceof ProposeTrade){
                        System.out.println("PROPOSE TRADE IGNORED");
                        return;
                    }
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
                    canTrade = true;
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

    private ContractNetInitiator generateInitiator(ACLMessage msg) {
        return new ContractNetInitiator(this, msg) {
            @Override
            protected void handlePropose(ACLMessage propose, Vector acceptances) {
                PlayerAgent thisAgent = (PlayerAgent) myAgent;
                if(!thisAgent.isTradeState){
                    System.out.println("Entrou aqui__________");
                    ACLMessage refuse = propose.createReply();
                    refuse.setPerformative(ACLMessage.REFUSE);
                    acceptances.addElement(refuse);
                }
                System.out.println("Handle propose");
                try {
                    ContentElement content = getContentManager().extractContent(propose);
                    ProposeTrade trade = (ProposeTrade) content;

                    if (trade.getTrade().getPrice() <= trade.getTrade().getBuyer().getMoney()) {
                        // Create an acceptance message and add it to acceptances
                        ACLMessage accept = propose.createReply();
                        accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                        acceptances.addElement(accept);
                        // TODO: Enviar mensagem ao Dealer a dizer que a trade foi efetuada
                        thisAgent.canTrade = false;

                        ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                        message.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
                        message.setOntology(MonopolyOntology.getInstance().getName());
                        message.addReceiver(new AID("Dealer", AID.ISLOCALNAME));
                        getContentManager().fillContent(message, new TradePerformed(trade.getTrade()));
                        thisAgent.send(message);

                        sendReadyMessage();
                        isTradeState = false;
                        canTrade = true;


                    } else {
                        // Create a refusal message and add it to acceptances
                        ACLMessage refuse = propose.createReply();
                        refuse.setPerformative(ACLMessage.REFUSE);
                        acceptances.addElement(refuse);
                        sendReadyMessage();
                    }
                } catch (Codec.CodecException | OntologyException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected void handleRefuse(ACLMessage refuse) {
                System.out.println("Handle refuse");


                try {
                    sendReadyMessage();
                } catch (OntologyException e) {
                    throw new RuntimeException(e);
                } catch (Codec.CodecException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected void handleFailure(ACLMessage failure) {
                System.out.println("Handle failure");

                try {
                    sendReadyMessage();
                } catch (OntologyException e) {
                    throw new RuntimeException(e);
                } catch (Codec.CodecException e) {
                    throw new RuntimeException(e);
                }

            }

            private void sendReadyMessage() throws OntologyException, Codec.CodecException {
                System.out.println("Sending ready message");
                ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                message.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
                message.setOntology(MonopolyOntology.getInstance().getName());
                message.addReceiver(new AID("Dealer", AID.ISLOCALNAME));
                getContentManager().fillContent(message, new ReadyAction());
                send(message);
            }
        };
    }

    private ContractNetResponder generateResponder() {
        MessageTemplate template = MessageTemplate.and(
                MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
                MessageTemplate.MatchPerformative(ACLMessage.CFP));

        return new ContractNetResponder(this, template) {
            protected ACLMessage handleCfp(ACLMessage cfp) {
                System.out.println("handleCFP ");
                if (cfp.getPerformative() == ACLMessage.CFP) {
                    PlayerAgent thisAgent = (PlayerAgent) myAgent;
                    ACLMessage message = thisAgent.brain.getTradeStrategy().processTrade(getContentManager(), cfp);
                    System.out.println("returned " + message);
                    send(message);
                    return message;
                } else {
                    ACLMessage notUnderstood = cfp.createReply();
                    notUnderstood.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                    System.out.println("Not understood");
                    return notUnderstood;
                }
            }

            protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) {
                System.out.println("handleAcceptProposal ");
                return null;
            }

            protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
                System.out.println("handleRejectProposal ");
                // Handle rejected proposal
            }
        };
    }

    public void initiateTrade(Trade trade) {
        //Purchasable strategy = (Purchasable) property.getBuyStrategy();
        //String receiver = strategy.getOwner();

        ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        msg.setOntology(MonopolyOntology.ONTOLOGY_NAME);
        msg.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
        msg.addReceiver(new AID(trade.getSeller().getName(), AID.ISLOCALNAME));
        msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
        msg.setReplyByDate(new Date(System.currentTimeMillis() + 500));

        //Trade trade = new Trade(property, price, this.board.getPlayerByName(this.getLocalName()), this.board.getPlayerByName(receiver));
        ProposeTrade content = new ProposeTrade(trade);

        try {
            this.getContentManager().fillContent(msg, content);
        } catch (Codec.CodecException | OntologyException e) {
            e.printStackTrace();
        }
        System.out.println("Sending trade proposal to " + trade.getSeller().getName());
        System.out.println("msg = " + msg);
        //send(msg);
        initiator = generateInitiator(msg);
        addBehaviour(initiator);
        //initiator.reset(msg);
        //initiator.restart();
        canTrade = false;
    }

    public boolean getCanTrade() {
        return canTrade;
    }

    public void setCanTrade(boolean canTrade) {
        this.canTrade = canTrade;
    }
}

