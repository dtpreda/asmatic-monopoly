package monopoly.models.lands.rentStrategy;

import jade.content.Concept;
import monopoly.models.Player;

public class NoRentStrategy extends RentStrategy{

    public NoRentStrategy() {
        super();
    }
    @Override
    public void setRent(Player owner, int rent) {
        return;
    }

    @Override
    public int getRent(int dice) {
        return 0;
    }

    @Override
    public boolean hasToPayRent(Player player) {
        return false;
    }

    @Override
    public boolean payRent(Player player) {
        return true;
    }
}
