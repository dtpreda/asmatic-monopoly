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
            monopolyController.setState(new TradeState(board, monopolyController));
        }
        return success;
    }


    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }
}
