package monopoly.models.lands.rentStrategy;

import jade.content.Concept;
import monopoly.models.Player;

public class PayBankStrategy extends RentStrategy {
    public PayBankStrategy() {
        super();
    }

    private int rent;
    public PayBankStrategy(int rent) {
        this.rent = rent;
    }

    @Override
    public void setRent(Player owner, int rent) {
        this.rent = rent;
    }

    @Override
    public int getRent(int dice) {
        return rent;
    }

    @Override
    public boolean hasToPayRent(Player player) {
        return true;
    }

    @Override
    public boolean payRent(Player player) {
        if(player.getMoney() < rent)
            return false;
        player.addMoney(-rent);
        return true;
    }
}
