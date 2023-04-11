package monopoly.view;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import monopoly.models.Player;

public class PlayerViewer {
    public PlayerViewer() {
    }

    public Node getPlayerInfo(Player player, int size) {
        StackPane stackPane = new StackPane();
        stackPane.setMinWidth(size);
        stackPane.setMinHeight(size);
        Rectangle rectangle = new Rectangle(size, size);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        Label label = new Label();
        label.setTranslateX(16);
        label.setWrapText(true);
        label.setMaxWidth(size);
        label.textProperty().bind(Bindings.format("Player Info:\nName: %s\nMoney: $%d\nColor: %s",
                player.getName(),
                player.getMoney(),
                player.getColor()));
        Circle playerPiece = getPlayerPiece(player, size/2, -size/2);
        stackPane.getChildren().addAll(rectangle, label, playerPiece);

        return stackPane;
    }

    public Circle getPlayerPiece(Player player, int x, int y) {
        Circle circle = new Circle(10);
        Color color = ViewerUtils.getColor(player.getColor());
        circle.setFill(color);
        circle.setTranslateX(x - 15);
        circle.setTranslateY(y + 15);
        return circle;
    }
}
