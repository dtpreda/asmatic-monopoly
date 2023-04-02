package monopoly.models;

import java.util.Objects;

public class Player{
    private int id;
    private String name;
    private int money;
    private int position;


    public Player(int id, String name, int money, int position){
        this.id = id;
        this.name = name;
        this.money = money;
        this.position = position;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void addMoney(int value){
        money += value;
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }
    public int getMoney(){
        return money;
    }
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        Player player = (Player) object;
        return id == player.id;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }
}