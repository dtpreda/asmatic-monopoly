package monopoly.models;

import jade.content.Concept;

import java.util.Objects;

public class Player implements Concept {
    private int id;
    private String name;
    private int money;
    private int position;

    private String color;

    public static int MAX_JAIL_TRIES = 3;
    private int isJailed = 0;


    public Player(int id, String name, int money, int position, String color){
        this.id = id;
        this.name = name;
        this.money = money;
        this.position = position;
        this.color = color;
    }

    public Player() {

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

    public void removeMoney(int value){
        money -= value;
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
        return name.equals(player.name);
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    public String getColor(){
        return color;
    }

    public boolean isJailed() {
        return isJailed > 0;
    }

    public void setJailed(boolean isJailed) {
        if(isJailed){
            this.isJailed = MAX_JAIL_TRIES;
        }else{
            this.isJailed = 0;
        }
    }


    public void setMoney(int money) {
        this.money = money;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void decreaseJailTries(){
        isJailed--;
    }
}