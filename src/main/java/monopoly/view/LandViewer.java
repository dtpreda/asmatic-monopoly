package monopoly.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import monopoly.models.lands.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class LandViewer {
    private int WIDTH = 75;
    public LandViewer(){

    }

    public Node getLandView(Land land){
        switch (land.getClass().getSimpleName()){
            case "Chance":
                return getChanceView(land);
            case "CommunityChest":
                return getCommunityChest(land);
            case "Company":
                return getUtilityView(land);
            case "GoToJail":
                return getGoToJailView(land);
            case "Jail":
                return getJailView(land);
            case "Parking":
                return getParkingView(land);
            case "Property":
                return getPropertyView(land);
            case "RailRoad":
                return getRailroadView(land);
            case "Start":
                return getStartView(land);
            case "Tax":
                return getTaxView(land);
        }
        Rectangle rectangle = new Rectangle(WIDTH,WIDTH);
        rectangle.setFill(Color.RED);
        return rectangle;
    }

    private Node getChanceView(Land land){
        return getImage("chance.jpg");
    }

    private Node getCommunityChest(Land land){
        return getImage("community-chest.jpg");
    }

    private Node getRailroadView(Land land){
        RailRoad railRoad = (RailRoad) land;
        Label label = createLabel(railRoad.getName());

        final Node image = getImage("railroad.png", WIDTH, WIDTH/ 2);
        StackPane root = new StackPane();
        root.getChildren().addAll(label, image);
        root.setAlignment(label, Pos.TOP_CENTER);
        root.setAlignment(image, Pos.BOTTOM_CENTER);
        root = (StackPane) addBorder(root);
        return root;
    }

    private Node getUtilityView(Land land){
        Company company = (Company) land;
        Node image;
        if(company.getName().equals("Electric company")){
            image = getImage("electric-company.png");
        } else {
            image = getImage("water-works.png");
        }
        return image;
    }

    private Node getGoToJailView(Land land){
        Node image = getImage("gotojail.jpg");
        return image;
    }

    private Node getJailView(Land land){
        Node image = getImage("monopoly-jail.jpg");
        return image;
    }

    private Node getParkingView(Land land){
        Node image = getImage("parking.jpg");
        return image;
    }

    private Node getStartView(Land land){
        Node image = getImage("start.png");
        return image;
    }

    private Node getTaxView(Land land){
        Tax tax = (Tax) land;
        Node image;
        if(tax.getName().equals("Luxury Tax")){
            image = getImage("luxury-tax.png");
        } else {
            image = getImage("income-tax.png");
        }
        return image;
    }

    private Node getPropertyView(Land land){
        final Property property = (Property) land;
        StackPane root = new StackPane();
        root.setAlignment(Pos.TOP_CENTER);

        // Create a rectangle to represent the property tile
        Color color = getColor(property.getColor());
        Rectangle tile = new Rectangle(WIDTH, WIDTH, color);

        // Create a label to display the property name
        Label label = createLabel(property.getName());

        if(color.equals(Color.BLUE) || color.equals(Color.SADDLEBROWN) || color.equals(Color.GREEN) || color.equals(Color.BLACK) || color.equals(Color.DARKBLUE)){
            //Change label color
            label.setTextFill(Color.WHITE);
        }

        // Add the rectangle and label to the stack pane
        root.getChildren().addAll(tile, label);
        root = (StackPane) addBorder(root);
        return root;
    }

    private Node addBorder(Node node){
        Rectangle rectangle = new Rectangle(WIDTH,WIDTH);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(node, rectangle);
        return stackPane;
    }

    private Node getImage(String name){
        return getImage(name, WIDTH, WIDTH);
    }
    private Node getImage(String name, int width, int height){
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

    private Color getColor(String str){
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

    private Label createLabel(String str){
        Label label = new Label(str);
        label.setWrapText(true);
        label.setMaxWidth(WIDTH - 5);
        label.setAlignment(Pos.CENTER);
        label.setFont(label.getFont().font(9));
        return label;
    }

}
