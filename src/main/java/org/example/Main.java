package org.example;

import javafx.application.Platform;
import javafx.stage.Stage;
import monopoly.controllers.MonopolyController;
import monopoly.models.Player;
import monopoly.states.LobbyState;
import monopoly.states.RollState;
import monopoly.view.MonopolyViewer;

public class Main {
    public static void main(String[] args) {
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