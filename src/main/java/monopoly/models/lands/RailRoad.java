package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.Purchasable;

public class RailRoad extends Land {
    private String name;
    private int price;
    public RailRoad() {
        super(new Purchasable());
    }
}