package monopoly.models.lands;

import monopoly.models.Player;
import monopoly.models.lands.Land;
import monopoly.models.lands.buyStrategy.PurchasableStrategy;

import java.util.List;

public class Property extends Land {
    private int price;
    private List<Integer> rents;

    private String color;

    public Property(PurchasableStrategy buyStrategy) {
        super(buyStrategy);
    }

    public int getCurrentRent(){
        return rents.get(building);
    }

    public boolean increaseBuilding(){
        building++;
        if(building >= rents.size()){
            building = rents.size() - 1;
            return false;
        }
        return true;

    }
}