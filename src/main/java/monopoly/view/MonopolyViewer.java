package monopoly.view;

import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import monopoly.models.MonopolyBoard;
import monopoly.models.lands.Land;

import java.awt.*;

public class MonopolyViewer extends Application {

    private MonopolyBoard monopolyBoard;

    public MonopolyViewer(MonopolyBoard board){
        this.board = board;
    }

    @Override
    public void start(Stage stage) throws Exception {
        GridPane board = new GridPane();
        board.setHgap(10);
        board.setVgap(10);

        //Fill spaces for lands
        for (Land property : board) {
            Rectangle propertySpace = new Rectangle();
            propertySpace.setWidth(50);
            propertySpace.setHeight(50);
            propertySpace.setFill(getColorForProperty(property));
            board.add(propertySpace, property.getColumn(), property.getRow());
        }
    }
}
