package monopoly.actions;

import jade.content.AgentAction;
import jade.content.Predicate;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.Land;

public class BuyLand implements Predicate {
    private Land land;
    private Player player;
    private MonopolyBoard monopolyBoard;
    public BuyLand(Land land, Player player, MonopolyBoard monopolyBoard){
        this.land = land;
        this.monopolyBoard = monopolyBoard;
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }


    public BuyLand(){

    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public MonopolyBoard getMonopolyBoard() {
        return monopolyBoard;
    }

    public void setMonopolyBoard(MonopolyBoard monopolyBoard) {
        this.monopolyBoard = monopolyBoard;
    }

}
