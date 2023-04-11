package org.example;

import javafx.application.Platform;
import javafx.stage.Stage;
import monopoly.controllers.MonopolyController;
import monopoly.view.MonopolyViewer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        MonopolyController monopolyController = new MonopolyController();
        monopolyController.startGame();

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