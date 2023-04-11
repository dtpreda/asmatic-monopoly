package monopoly.states;

import monopoly.controllers.BoardController;
import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;

import java.util.List;

public class LobbyState extends MonopolyState{

    private String colors[] = {"red", "blue", "green", "yellow", "orange", "purple", "pink", "brown"};

    public LobbyState(MonopolyBoard board, MonopolyController monopolyController){
        super(board, monopolyController);
    }

    public Player addPlayer(String name){
        List<Player> players = board.getPlayers();
        for(Player player : players){
            if(player.getName().equals(name)){
                return null;
            }
        }
        String color = colors[players.size()];
        Player player = new Player(players.size(), name, 1500, players.size(), color);
        board.addPlayer(player);
        return player;
    }
}
