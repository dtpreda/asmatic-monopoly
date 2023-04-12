package monopoly.models;

import jade.content.Concept;
import monopoly.models.lands.Land;

import java.util.ArrayList;
import java.util.List;

public class MonopolyBoard implements Concept {
    private List<Land> lands;
    private List<Player> players;
    private int currentPlayerIndex;
    private Dice lastDice;

    public MonopolyBoard(){
        lands = new ArrayList<Land>();
        players = new ArrayList<Player>();
    }

    public void addPlayer(Player player){
        players.add(player);
        lands.get(0).addPlayer(player);
    }

    public Player getPlayer(String name){
        for(Player player : players){
            if(player.getName().equals(name)){
                return player;
            }
        }
        return null;
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

    public Dice getLastDice() {
        return lastDice;
    }

    public void setLastDice(Dice lastDice) {
        this.lastDice = lastDice;
    }
}