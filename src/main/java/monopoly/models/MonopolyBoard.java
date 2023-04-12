package monopoly.models;

import jade.content.Concept;
import monopoly.models.lands.Land;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;

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


    public List<Property> getProperties(String color){
        List<Property> props = new ArrayList<>();
        for(Land land : lands){
            if(land instanceof Property){
                Property prop = (Property) land;
                if(prop.getColor().equals(color)){
                    props.add(prop);
                }
            }
        }
        return props;
    }

    public List<Property> getProperties(Player player){
        List<Property> props = new ArrayList<>();
        for(Land land : lands){
            if(land instanceof Property){
                Property prop = (Property) land;
                Purchasable purchasable = (Purchasable) prop.getBuyStrategy();
                if(player.getName().equals(purchasable.getOwner())){
                    props.add(prop);
                }
            }
        }
        return props;
    }


    public Player getCurrentPlayer(){
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer(){
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        if(getCurrentPlayer().isBankrupt()){
            nextPlayer();
        }
    }

    public boolean isGameFinished(){
        int count = 0;
        for(Player player : players){
            if(!player.isBankrupt()){
                count++;
            }
        }
        return count == 1;
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