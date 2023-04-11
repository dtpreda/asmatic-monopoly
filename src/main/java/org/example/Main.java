package org.example;

import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import monopoly.controllers.MonopolyController;
import jade.core.Runtime;
import jade.core.Profile;
import monopoly.agents.DealerAgent;
import javafx.application.Platform;
import javafx.stage.Stage;
import monopoly.controllers.MonopolyController;
import monopoly.models.Player;
import monopoly.view.MonopolyViewer;

public class Main {
    public static void main(String[] args) throws StaleProxyException {
        System.out.println("Hello world!");

        MonopolyController monopolyController = new MonopolyController();
        monopolyController.startGame();
        Player player = monopolyController.addPlayer("Player 1");
        Player player2 = monopolyController.addPlayer("Player 2");
        Player player3 = monopolyController.addPlayer("Player 3");
        final MonopolyViewer mainStage = new MonopolyViewer(monopolyController);
        try {
            mainStage.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Runtime rt = Runtime.instance();
        Profile p1 = new ProfileImpl();
        p1.setParameter(Profile.MAIN_HOST, "192.168.1.154");
        p1.setParameter(Profile.MAIN_PORT, "1099");
        p1.setParameter(Profile.CONTAINER_NAME, "Main-Container");
        ContainerController container = rt.createMainContainer(p1);

        AgentController ac1 = container.acceptNewAgent("John", new DealerAgent());
        ac1.start();

        Platform.startup(() -> {
            Stage stage = new Stage();
            try {
                mainStage.start(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}