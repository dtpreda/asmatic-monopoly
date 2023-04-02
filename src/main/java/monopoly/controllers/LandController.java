package monopoly.controllers;

import monopoly.models.Player;
import monopoly.models.lands.Land;
import monopoly.models.lands.rentStrategy.RentStrategy;

public class LandController {

    public boolean hasToPayRent(Land land, Player player) {
        RentStrategy strategy = land.getRentStrategy();
        return strategy.hasToPayRent(player);
    }

    public boolean payRent(Land land, Player player) {
        RentStrategy strategy = land.getRentStrategy();
        return strategy.payRent(player);
    }

    public int getRent(Land land, int dice){
        RentStrategy strategy = land.getRentStrategy();
        return strategy.getRent(dice);
    }

    public void buyLand(Land land, Player player){
        if(land.canPurchase()) {
            land.getBuyStrategy().purchase(player);
        }

    }
}
