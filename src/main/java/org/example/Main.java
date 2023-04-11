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

import java.awt.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws StaleProxyException {
        System.out.println("Hello world!");

        MonopolyController monopolyController = new MonopolyController();
        monopolyController.startGame();
        Player player = monopolyController.addPlayer("John");
        Player player2 = monopolyController.addPlayer("Mike");
        Player player3 = monopolyController.addPlayer("Torcato");
        Player player4 = monopolyController.addPlayer("Bdcanss");
        LobbyState state =  (LobbyState) monopolyController.getState();
        state.startGame();
        Runtime rt = Runtime.instance();
        Profile p1 = new ProfileImpl();
        p1.setParameter(Profile.MAIN_HOST, "localhost");
        p1.setParameter(Profile.MAIN_PORT, "1099");
        p1.setParameter(Profile.CONTAINER_NAME, "Main-Container");
        ContainerController container = rt.createMainContainer(p1);

        initPlayerAgents(container, monopolyController.getBoard().getPlayers());
        initDealerAgent(container, monopolyController);

        initViewer(monopolyController);
        state.startGame();
    }


    private static void initPlayerAgents(ContainerController container, List<Player> players) throws StaleProxyException {
        for (Player player: players) {
            AgentController playerController = container.acceptNewAgent(player.getName(), new PlayerAgent());
            playerController.start();
        }
    }

    private static void initDealerAgent(ContainerController container, MonopolyController monopolyController) throws StaleProxyException {
        AgentController dealerController = container.acceptNewAgent("Dealer", new DealerAgent(monopolyController));
        dealerController.start();
    }

    private static void initViewer(MonopolyController monopolyController){
        final MonopolyViewer mainStage = new MonopolyViewer(monopolyController);
        try {
            mainStage.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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