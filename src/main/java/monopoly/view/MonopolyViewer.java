package monopoly.view;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Land;

import javafx.scene.shape.Rectangle;

public class MonopolyViewer extends Application {

    private MonopolyBoard monopolyBoard;
    private MonopolyController controller;

    private LandViewer landViewer;
    private PlayerViewer playerViewer;

    public MonopolyViewer(MonopolyController controller){
        this.controller = controller;
        this.monopolyBoard = controller.getBoard();
        this.playerViewer = new PlayerViewer();
        this.landViewer = new LandViewer(playerViewer);

    }

    public MonopolyViewer(){

    }

    public static void main(String[] args){
        launch(args);
    }



    @Override
    public void start(Stage stage) throws Exception {
        int BOARD_WIDTH = ViewerUtils.WIDTH * 11;
        int BOARD_HEIGHT = ViewerUtils.WIDTH * 11;

        GridPane board = getBoard(monopolyBoard);
        GridPane playersSpace = getPlayersInfo(monopolyBoard);

        final StackPane root = new StackPane();

        root.getChildren().addAll(board, playersSpace);
        Scene scene = new Scene(root, 850, 800);
        stage.setScene(scene);
        stage.show();

        Thread thread = new Thread(() -> {

            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException exc) {
                    throw new Error("Unexpected interruption", exc);
                }
                Platform.runLater(() -> {
                    GridPane board2 = getBoard(monopolyBoard);
                    GridPane playersSpace2 = getPlayersInfo(monopolyBoard);
                    root.getChildren().remove(0);
                    root.getChildren().remove(0);
                    root.getChildren().addAll(board2, playersSpace2);

                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void setMonopolyBoard(MonopolyBoard board){
        this.monopolyBoard = board;
    }

    private GridPane getBoard(MonopolyBoard monopolyBoard){
        GridPane board = new GridPane();
        board.setHgap(0);
        board.setVgap(0);

        //Fill spaces for lands
        int index=0;
        int sideLength = 11;
        for (Land property : monopolyBoard.getLands()) {
            int row = 0, column = 0;

            if (index >= 0 && index < sideLength) {
                row = 0;
                column = index;
            } else if (index >= sideLength && index < (2 * sideLength - 1)) {
                row = index - -1 - sideLength;
                column = sideLength - 1;
            }
            else if (index >= (2 * sideLength - 1) && index < (3 * sideLength - 2)) {
                row = sideLength - 1;
                column = sideLength  - 3 - (index - 2 * sideLength );
            } else if (index >= (3 * sideLength - 2) && index < 4 * sideLength) {
                row = sideLength - 4 - (index - (3 * sideLength ));
                column = 0;
            }
            Node propertySpace = landViewer.getLandView(property);

            // convert so that 0,0 is bottom right
            row = sideLength - 1 - row;
            column = sideLength - 1 - column;
            board.add(propertySpace, column, row);
            index++;
        }
        return board;
    }

    private GridPane getPlayersInfo(MonopolyBoard monopolyBoard){
        GridPane playersSpace = new GridPane();
        int PLAYER_SPACE_SIZE = 150;
        playersSpace.setHgap(10);
        playersSpace.setVgap(10);
        int PLAYER_PER_ROW = 3;
        for (int i = 0; i < monopolyBoard.getPlayers().size(); i++) {
            Player player = monopolyBoard.getPlayers().get(i);
            int row = i / PLAYER_PER_ROW;
            int column = i % PLAYER_PER_ROW;
            Node playerSpace = playerViewer.getPlayerInfo(player, PLAYER_SPACE_SIZE);
            playersSpace.add(playerSpace, column, row);
        }
        playersSpace.setTranslateY(150);
        playersSpace.setTranslateX(150);
        return playersSpace;
    }


}
