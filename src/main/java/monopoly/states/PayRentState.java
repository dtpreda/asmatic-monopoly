package monopoly.states;

import monopoly.controllers.MonopolyController;
import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Land;
import monopoly.models.lands.rentStrategy.PayOwnerStrategy;

import java.util.List;

public class PayRentState extends MonopolyState{

    private Land land;
    private Player player;
    private Dice dice;
    public PayRentState(Player player, Land land, Dice dice, MonopolyBoard board, MonopolyController monopolyController){
        super(board, monopolyController);
        this.land = land;
        this.player = player;
        this.dice = dice;
    }

    public boolean payRent(Player player){
        System.out.println("Player paying rent for " + land);
        boolean success = land.getRentStrategy().payRent(player);
        if(success){
            System.out.println("Player paid rent for " + land);
            monopolyController.setState(new RollState(board, monopolyController));
            //Send money to owner if applicate
            if(land.getRentStrategy() instanceof  PayOwnerStrategy && false){
                PayOwnerStrategy payOwnerStrategy = (PayOwnerStrategy) land.getRentStrategy();
                payOwnerStrategy.getOwner().addMoney(land.getRentStrategy().getRent(1));
            }
        }
        return success;
    }
}
