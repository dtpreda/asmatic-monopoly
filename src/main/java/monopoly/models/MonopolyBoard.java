package monopoly.models;

import jade.content.Concept;
import monopoly.models.lands.Land;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
                System.out.println("returning player " + player);
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

    public Player getPlayerByName(String name) {
        for (Player player: this.players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }

        return null;
    }

    public List<Property> getOwnedProperties(){
        List<Property> ownedProperties = new ArrayList<>();
        for (Land land: this.lands) {
            if (land instanceof Property) {
                Property property = (Property) land;
                if (((Purchasable)property.getBuyStrategy()).getOwner() != null) {
                    ownedProperties.add(property);
                }
            }
        }
        return ownedProperties;
    }

    public List<Property> getOwnedPropertiesColor(String color){
        return getOwnedProperties().stream().filter(property -> property.getColor().equals(color)).collect(Collectors.toList());
    }

    public List<Property> getOwnedPropertiesColorNotPlayer(String color, Player player){
        return getOwnedProperties().stream().filter(
                property -> property.getColor().equals(color) &&
                            !player.getName().equals(((Purchasable)property.getBuyStrategy()).getOwner())
        ).collect(Collectors.toList());
    }

    public List<Property> getOwnedPropertiesNotPlayer(String player){
        return getOwnedProperties().stream().filter(
                property -> !player.equals(((Purchasable)property.getBuyStrategy()).getOwner())
        ).collect(Collectors.toList());
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
}