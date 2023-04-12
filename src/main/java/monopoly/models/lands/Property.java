package monopoly.models.lands;

import com.google.gson.annotations.SerializedName;
import monopoly.models.Player;
import monopoly.models.lands.Land;
import monopoly.models.lands.buyStrategy.Purchasable;
import monopoly.models.lands.buyStrategy.PurchasableStrategy;
import monopoly.models.lands.rentStrategy.NoRentStrategy;
import monopoly.models.lands.rentStrategy.PayOwnerStrategy;

import java.util.List;

public class Property extends Land {
    private int price;
    private List<Integer> rents;

    private String color;

    @SerializedName("name")
    private String name;

    public Property(PurchasableStrategy buyStrategy) {
        super(buyStrategy);
        setRentStrategy(new NoRentStrategy());
    }
    public Property(){
        super(new Purchasable());
        setRentStrategy(new NoRentStrategy());
    }

    public int getCurrentRent(){
        return rents.get(building);
    }

    public boolean increaseBuilding(){
        building++;
        if(building >= rents.size()){
            building = rents.size() - 1;
            updateRent();
            return false;
        }
        return true;
    }

    public void updateRent(){
        PayOwnerStrategy strat = (PayOwnerStrategy) getRentStrategy();
        strat.setRent(rents.get(building));
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}