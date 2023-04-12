package monopoly.models.lands.buyStrategy;

import jade.content.Concept;
import monopoly.models.Player;

public class Purchasable extends PurchasableStrategy implements Concept {
    private int price;
    private String landOwner;

    public Purchasable() {
        super();
    }

    public boolean isOwner(Player player) {
        return player.getName().equals(landOwner);
    }

    public String getOwner() {
        return landOwner;
    }

    public void setOwner(Player player) {
        landOwner = player.getName();
    }

    public boolean canPurchase() {
        return landOwner == null;
    }

    public int getPrice() {
        return price;
    }

    public String getLandOwner() {
        return landOwner;
    }

    public void setLandOwner(String landOwner) {
        this.landOwner = landOwner;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean purchase(Player player) {
        if(player.getMoney() >= price){
            player.removeMoney(price);
            setOwner(player);
            return true;
        }
        return false;
    }
}
