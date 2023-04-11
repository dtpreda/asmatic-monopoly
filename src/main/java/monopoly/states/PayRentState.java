package monopoly.states;

import monopoly.controllers.MonopolyController;
import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Land;

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
        boolean success = land.getRentStrategy().payRent(player);
        if(success){
            monopolyController.setState(new RollState(board, monopolyController));
        }
        return success;
    }
}
