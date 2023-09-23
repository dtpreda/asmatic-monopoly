package monopoly.models.lands.rentStrategy;

import jade.content.Concept;
import monopoly.models.Player;

public class PayOwnerStrategy extends RentStrategy{

    private int rent;
    private Player owner;
    public PayOwnerStrategy(int rent) {
        super();
        this.rent = rent;
    }

    public PayOwnerStrategy(Player owner){
        this.owner = owner;
    }

    public PayOwnerStrategy() {
        super();
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
            System.out.println("Transaction:: " + player.getName() + " -> " + owner.getName() + " : " + rent );
            player.addMoney(-rent);
            owner.addMoney(rent);
            return true;
        }
        return false;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
