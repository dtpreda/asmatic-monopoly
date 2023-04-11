package monopoly.view;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.lands.Land;

import javafx.scene.shape.Rectangle;

public class MonopolyViewer extends Application {

    private MonopolyBoard monopolyBoard;
    private MonopolyController controller;

    private LandViewer landViewer;

    public MonopolyViewer(MonopolyController controller){
        this.controller = controller;
        this.monopolyBoard = controller.getBoard();
        this.landViewer = new LandViewer();
    }

    public MonopolyViewer(){

    }

    public static void main(String[] args){
        launch(args);
    }



    @Override
    public void start(Stage stage) throws Exception {
        System.out.println("Ola");
        GridPane board = new GridPane();
        board.setHgap(10);
        board.setVgap(10);

        //Fill spaces for lands
        int index=0;
        int sideLength = 11;
        //11x11 board
        //   0 1 2 3 4 5 6 7 8 9 10
        // 1
        // 2
        // 3
        // 4
        // 5
        // 6
        // 7
        // 8
        // 9
        // 10

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

        Scene scene = new Scene(board, 1000,1000);
        stage.setScene(scene);
        stage.show();
    }


}
