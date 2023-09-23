package monopoly.models.lands.rentStrategy;

import jade.content.Concept;
import monopoly.models.Player;

public abstract class RentStrategy implements Concept {

    public RentStrategy() {
    }

    abstract public void setRent(Player owner, int rent);
    abstract public int getRent(int dice);
    abstract public boolean hasToPayRent(Player player);
    abstract public boolean payRent(Player player);
}
