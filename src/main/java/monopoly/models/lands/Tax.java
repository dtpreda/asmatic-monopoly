package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;

public class Tax extends Land {

    private String name;
    private int taxCost;
    public Tax() {
        super(new NonPurchasable());
    }

    public String getName() {
        return name;
    }
}