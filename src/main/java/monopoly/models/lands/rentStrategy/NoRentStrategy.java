package monopoly.models.lands.rentStrategy;

import monopoly.models.Player;

public class NoRentStrategy implements RentStrategy {
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
