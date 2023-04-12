package monopoly.models.lands;

import monopoly.models.lands.buyStrategy.Purchasable;
import monopoly.models.lands.rentStrategy.NoRentStrategy;
import monopoly.models.lands.rentStrategy.PayOwnerStrategy;

import java.util.List;

public class RailRoad extends Land {
    private String name;
    private int price;

    private List<Integer> rents;
    public RailRoad() {
        super(new Purchasable());
        setRentStrategy(new NoRentStrategy());
    }


    public void updateRent(){
        PayOwnerStrategy strat = (PayOwnerStrategy) getRentStrategy();
        strat.setRent(rents.get(building));
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<Integer> getRents() {
        return rents;
    }

    public void setRents(List<Integer> rents) {
        this.rents = rents;
    }
}