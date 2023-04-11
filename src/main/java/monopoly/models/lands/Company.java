package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;
import monopoly.models.lands.rentStrategy.NoRentStrategy;

public class Company extends Land {

    private String name;
    private int price;
    public Company() {
        super(new NonPurchasable());
        setRentStrategy(new NoRentStrategy());
    }

    public String getName() {
        return name;
    }
}