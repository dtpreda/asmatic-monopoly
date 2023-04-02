package monopoly.models.lands.rentStrategy;

import monopoly.models.Player;

public class PayOwnerStrategy implements RentStrategy{

    private int rent;
    private Player owner;
    public PayOwnerStrategy(int rent) {
        this.rent = rent;
    }

    @Override
    public void setRent(Player owner, int rent) {
        this.rent = rent;
        this.owner = owner;
    }

    @Override
    public int getRent(int dice) {
        return rent;
    }

    @Override
    public boolean hasToPayRent(Player player) {
        return !player.equals(owner);
    }

    @Override
    public boolean payRent(Player player) {
        if (player.getMoney() >= rent) {
            player.addMoney(-rent);
            owner.addMoney(rent);
            return true;
        }
        return false;
    }

}
