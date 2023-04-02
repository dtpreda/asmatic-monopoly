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

    public Player addPlayer(String name){
        List<Player> players = board.getPlayers();
        for(Player player : players){
            if(player.getName().equals(name)){
                return null;
            }
        }
        Player player = new Player(players.size(), name, 1500, players.size());
        board.addPlayer(player);
        return player;
    }
}
