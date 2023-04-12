package monopoly.models;

import jade.content.Concept;
import monopoly.models.lands.Property;

public class Trade implements Concept {
    private int price;
    private Property property;
    private Player buyer;
    private Player seller;

    public Trade(Property property, int price, Player buyer, Player seller) {
        this.property = property;
        this.price = price;
        this.buyer = buyer;
        this.seller = seller;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Player getBuyer() {
        return buyer;
    }

    public void setBuyer(Player buyer) {
        this.buyer = buyer;
    }

    public Player getSeller() {
        return seller;
    }

    public void setSeller(Player seller) {
        this.seller = seller;
    }
}
