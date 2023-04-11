package monopoly.models.lands;

import java.util.ArrayList;
import java.util.List;
import monopoly.models.Player;
import monopoly.models.lands.buyStrategy.Purchasable;
import monopoly.models.lands.buyStrategy.PurchasableStrategy;
import monopoly.models.lands.rentStrategy.RentStrategy;

public abstract class Land{
    private List<Player> players;
    protected int building;
    private PurchasableStrategy buyStrategy;
    public Land(PurchasableStrategy buyStrategy){
        this.buyStrategy = buyStrategy;
        players = new ArrayList<Player>();
    }

    private RentStrategy rentStrategy;

    public boolean canPurchase(){
        return buyStrategy instanceof Purchasable;
    }
    public void addPlayer(Player player){
        players.add(player);
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public PurchasableStrategy getBuyStrategy() {
        return buyStrategy;
    }

    public void setRentStrategy(RentStrategy rentStrategy) {
        this.rentStrategy = rentStrategy;
    }

    public RentStrategy getRentStrategy() {
        return rentStrategy;
    }
}