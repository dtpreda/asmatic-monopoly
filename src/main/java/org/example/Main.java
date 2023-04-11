package org.example;

import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import monopoly.agents.PlayerAgent;
import monopoly.controllers.MonopolyController;
import jade.core.Runtime;
import jade.core.Profile;
import monopoly.agents.DealerAgent;
import javafx.application.Platform;
import javafx.stage.Stage;
import monopoly.models.Player;
import monopoly.states.LobbyState;
import monopoly.states.RollState;
import monopoly.view.MonopolyViewer;

public class Main {
    public static void main(String[] args) throws StaleProxyException {
        System.out.println("Hello world!");

        MonopolyController monopolyController = new MonopolyController();
        monopolyController.startGame();
        Player player = monopolyController.addPlayer("Player 1");
        Player player2 = monopolyController.addPlayer("Player 2");
        Player player3 = monopolyController.addPlayer("Player 3");
        LobbyState state =  (LobbyState) monopolyController.getState();


        final MonopolyViewer mainStage = new MonopolyViewer(monopolyController);
        try {
            mainStage.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Runtime rt = Runtime.instance();
        Profile p1 = new ProfileImpl();
        p1.setParameter(Profile.MAIN_HOST, "localhost");
        p1.setParameter(Profile.MAIN_PORT, "1099");
        p1.setParameter(Profile.CONTAINER_NAME, "Main-Container");
        ContainerController container = rt.createMainContainer(p1);

        AgentController ac1 = container.acceptNewAgent("John", new DealerAgent());
        ac1.start();

        AgentController ac2 = container.acceptNewAgent("Mike", new PlayerAgent());
        ac2.start();

        Platform.startup(() -> {
            Stage stage = new Stage();
            try {
                mainStage.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        state.startGame();

        try {
            Thread.sleep(3000);
            RollState rollState = (RollState) monopolyController.getState();
            rollState.play(player);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}