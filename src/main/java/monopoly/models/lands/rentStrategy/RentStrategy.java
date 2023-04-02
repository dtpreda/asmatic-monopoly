package monopoly.models.lands.rentStrategy;

import monopoly.models.Player;

public interface RentStrategy {

    void setRent(Player owner, int rent);
    int getRent(int dice);
    boolean hasToPayRent(Player player);
    boolean payRent(Player player);
}
