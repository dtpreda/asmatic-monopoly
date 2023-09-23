package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.NonPurchasable;
import monopoly.models.lands.rentStrategy.NoRentStrategy;
import monopoly.models.lands.rentStrategy.PayOwnerStrategy;

import java.util.List;

public class Company extends Land {

    private String name;
    private int price;

    private List<Integer> rents;
    public Company() {
        super(new NonPurchasable());
        setRentStrategy(new NoRentStrategy());
    }

    public void updateRent(){
        PayOwnerStrategy strat = (PayOwnerStrategy) getRentStrategy();
        strat.setRent(rents.get(building));
    }
    public String getName() {
        return name;
    }

    public List<Integer> getRents() {
        return rents;
    }

    public void setRents(List<Integer> rents) {
        this.rents = rents;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}