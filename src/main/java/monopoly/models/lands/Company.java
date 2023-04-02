package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;

public class Company extends Land {

    private String name;
    private int price;
    public Company() {
        super(new NonPurchasable());
    }
}