package monopoly.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import monopoly.models.Player;
import monopoly.models.lands.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static monopoly.view.ViewerUtils.PLAYER_SIZE;
import static monopoly.view.ViewerUtils.WIDTH;


public class LandViewer {
    private PlayerViewer playerViewer;
    public LandViewer(PlayerViewer playerViewer){
        this.playerViewer = playerViewer;
    }

    public Node getLandView(Land land){
        StackPane stack = new StackPane();
        Node node;
        switch (land.getClass().getSimpleName()){
            case "Chance":
                node = getChanceView(land);
                break;
            case "CommunityChest":
                node = getCommunityChest(land);
                break;
            case "Company":
                node = getUtilityView(land);
                break;
            case "GoToJail":
                node = getGoToJailView(land);
                break;
            case "Jail":
                node = getJailView(land);
                break;
            case "Parking":
                node = getParkingView(land);
                break;
            case "Property":
                node = getPropertyView(land);
                break;
            case "RailRoad":
                node = getRailroadView(land);
                break;
            case "Start":
                node = getStartView(land);
                break;
            case "Tax":
                node = getTaxView(land);
                break;
            default:
                node = getChanceView(land);
        }

        //Add player pieces
        stack.getChildren().add(node);
        int i=0;
            for (Player player : land.getPlayers()) {
                int x = i * (ViewerUtils.PLAYER_SIZE) * 2;
                int y = 0;
                Node playerPiece = playerViewer.getPlayerPiece(player, x, y);
                stack.getChildren().add(playerPiece);
                i++;

            }
        return stack;
    }

    private Node getChanceView(Land land){
        return ViewerUtils.getImage("chance.jpg");
    }

    private Node getCommunityChest(Land land){
        return ViewerUtils.getImage("community-chest.jpg");
    }

    private Node getRailroadView(Land land){
        RailRoad railRoad = (RailRoad) land;
        Label label = ViewerUtils.createLabel(railRoad.getName());

        final Node image = ViewerUtils.getImage("railroad.png", WIDTH, WIDTH/ 2);
        StackPane root = new StackPane();
        root.getChildren().addAll(label, image);
        root.setAlignment(label, Pos.TOP_CENTER);
        root.setAlignment(image, Pos.BOTTOM_CENTER);
        root = (StackPane) ViewerUtils.addBorder(root);
        return root;
    }

    private Node getUtilityView(Land land){
        Company company = (Company) land;
        Node image;
        if(company.getName().equals("Electric company")){
            image = ViewerUtils.getImage("electric-company.png");
        } else {
            image = ViewerUtils.getImage("water-works.png");
        }
        return image;
    }

    private Node getGoToJailView(Land land){
        Node image = ViewerUtils.getImage("gotojail.jpg");
        return image;
    }

    private Node getJailView(Land land){
        Node image = ViewerUtils.getImage("monopoly-jail.jpg");
        return image;
    }

    private Node getParkingView(Land land){
        Node image = ViewerUtils.getImage("parking.jpg");
        return image;
    }

    private Node getStartView(Land land){
        Node image = ViewerUtils.getImage("start.png");
        return image;
    }

    private Node getTaxView(Land land){
        Tax tax = (Tax) land;
        Node image;
        if(tax.getName().equals("Luxury Tax")){
            image = ViewerUtils.getImage("luxury-tax.png");
        } else {
            image = ViewerUtils.getImage("income-tax.png");
        }
        return image;
    }

    private Node getPropertyView(Land land){
        final Property property = (Property) land;
        StackPane root = new StackPane();
        root.setAlignment(Pos.TOP_CENTER);

        // Create a rectangle to represent the property tile
        Color color = ViewerUtils.getColor(property.getColor());
        Rectangle tile = new Rectangle(WIDTH, WIDTH, color);

        // Create a label to display the property name
        Label label = ViewerUtils.createLabel(property.getName());

        if(color.equals(Color.BLUE) || color.equals(Color.SADDLEBROWN) || color.equals(Color.GREEN) || color.equals(Color.BLACK) || color.equals(Color.DARKBLUE)){
            //Change label color
            label.setTextFill(Color.WHITE);
        }

        // Add the rectangle and label to the stack pane
        root.getChildren().addAll(tile, label);
        root = (StackPane) ViewerUtils.addBorder(root);
        return root;
    }

}
