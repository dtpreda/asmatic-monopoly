package monopoly.controllers;

import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Jail;
import monopoly.models.lands.Land;

public class BoardController {
    MonopolyBoard board;
    public BoardController(MonopolyBoard board){
           this.board = board;
    }

    public Land movePlayer(Player player, int diceValue){
        int oldPosition = player.getPosition();
        int newPosition = player.getPosition() + diceValue;
        if(newPosition >= 40){
            newPosition -= 40;
            player.addMoney(200);
        }
        player.setPosition(newPosition);
        board.getLand(oldPosition).removePlayer(player);
        board.getLand(newPosition).addPlayer(player);
        return board.getLand(newPosition);
    }

    public void sendToJail(Player player){
        int currentPosition = player.getPosition();
        board.getLand(currentPosition).removePlayer(player);
        for(int i=0; i<board.getLands().size(); i++){
            final Land land = board.getLand(i);
            if(land instanceof Jail){
                player.setPosition(i);
                player.setJailed(true);
                land.addPlayer(player);
                return;
            }
        }
    }

}
