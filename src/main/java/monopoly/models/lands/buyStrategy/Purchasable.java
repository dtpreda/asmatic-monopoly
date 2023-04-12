package monopoly.models.lands.buyStrategy;

import monopoly.models.Player;

public class Purchasable implements PurchasableStrategy {
        private int price;
        private String landOwner;

        public Purchasable() {
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

    @Override
    public boolean purchase(Player player) {
        if(player.getMoney() >= price){
            player.addMoney(- price);
            setOwner(player);
            return true;
        }
        return false;
    }
}
