package monopoly.actions;

import jade.content.AgentAction;
import jade.content.Predicate;
import monopoly.models.MonopolyBoard;
import monopoly.models.lands.Land;

public class BuyLand implements Predicate {
    private Land land;
    private MonopolyBoard monopolyBoard;
    public BuyLand(Land land, MonopolyBoard monopolyBoard){
        this.land = land;
        this.monopolyBoard = monopolyBoard;
    }

    public BuyLand(){

    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public MonopolyBoard getMonopolyBoard() {
        return monopolyBoard;
    }

    public void setMonopolyBoard(MonopolyBoard monopolyBoard) {
        this.monopolyBoard = monopolyBoard;
    }

}
