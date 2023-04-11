package monopoly.states;

import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Land;

import java.util.List;

public class PayRentState extends MonopolyState{

    public PayRentState(Player player, Land land, MonopolyBoard board, MonopolyController monopolyController){
        super(board, monopolyController);
    }
}
