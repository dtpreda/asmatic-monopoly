package monopoly.agents;

import jade.core.Agent;
import monopoly.controllers.MonopolyController;

public class DealerAgent extends Agent {
    private MonopolyController monopolyController;
    @Override
    protected void setup() {
        monopolyController = new MonopolyController();
        monopolyController.startGame();
        System.out.println("Dealer agent started");
    }




}
