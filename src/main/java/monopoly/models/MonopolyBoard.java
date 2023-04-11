package monopoly.models;

import monopoly.models.lands.Land;

import java.util.ArrayList;
import java.util.List;

public class MonopolyBoard {
    private List<Land> lands;
    private List<Player> players;

    private int currentPlayerIndex;
    private Dice dice;

    public MonopolyBoard(){
        lands = new ArrayList<Land>();
        players = new ArrayList<Player>();
    }

    public void addPlayer(Player player){
        players.add(player);
        lands.get(0).addPlayer(player);
    }

    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }


    public List<Land> getLands() {
        return lands;
    }

    public Land getLand(int position){
        return lands.get(position);
    }

    public void setLands(List<Land> lands) {
        this.lands = lands;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }
}