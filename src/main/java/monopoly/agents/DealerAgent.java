package monopoly.agents;

import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.domain.FIPANames;
import monopoly.controllers.MonopolyController;

public class DealerAgent extends Agent {
    private MonopolyController monopolyController;
    @Override
    protected void setup() {
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);
        getContentManager().registerOntology(MonopolyOntology.getInstance());

        monopolyController = new MonopolyController();
        monopolyController.startGame();
        System.out.println("Dealer agent started");
    }
}
