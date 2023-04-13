package monopoly.models.lands.buyStrategy;

import jade.content.Concept;
import monopoly.models.Player;
import monopoly.models.lands.rentStrategy.RentStrategy;

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
        if(player == null){
            landOwner = null;
            return;
        }
        landOwner = player.getName();
    }

    public boolean canPurchase() {
        return landOwner == null;
    }

    public boolean canPurchase(Player player){
        return canPurchase() && player.getMoney() >= price && !player.getName().equals(landOwner);
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
        if(landOwner != null) return false;
        if(player.getMoney() >= price){
            player.removeMoney(price);
            setOwner(player);
            return true;
        }
        return false;
    }




}
