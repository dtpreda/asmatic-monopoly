package monopoly.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ViewerUtils {
    public static int WIDTH = 75;

    public static Node addBorder(Node node){
        Rectangle rectangle = new Rectangle(WIDTH,WIDTH);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(node, rectangle);
        return stackPane;
    }


    public static Color getColor(String str){
        switch (str){
            case "brown":
                return Color.SADDLEBROWN;
            case "lightblue":
                return Color.LIGHTBLUE;
            case "pink":
                return Color.PINK;
            case "orange":
                return Color.ORANGE;
            case "red":
                return Color.ORANGERED;
            case "yellow":
                return Color.YELLOW;
            case "green":
                return Color.GREEN;
            case "blue":
                return Color.BLUE;
            case "white":
                return Color.WHITE;
            case "black":
                return Color.BLACK;
            case "darkblue":
                return Color.DARKBLUE;
        }
        return Color.BLACK;
    }

    public static Label createLabel(String str){
        Label label = new Label(str);
        label.setWrapText(true);
        label.setMaxWidth(WIDTH - 5);
        label.setAlignment(Pos.CENTER);
        label.setFont(label.getFont().font(9));
        return label;
    }

    public static Node getImage(String name){
        return getImage(name, WIDTH, WIDTH);
    }
    public static Node getImage(String name, int width, int height){
        try {
            InputStream inputStream = new FileInputStream(new File("src/main/resources/images/" + name));
            Image image = new Image(inputStream);
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            if(height != -1) {
                imageView.setFitHeight(height);
                imageView.setPreserveRatio(false);
            }
            return imageView;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
