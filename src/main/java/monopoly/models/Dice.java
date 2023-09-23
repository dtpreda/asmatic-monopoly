package monopoly.models;

public class Dice {
    private int value;
    private boolean isDouble;

    public Dice(){
        roll();
    }

    public int getValue(){
        return value;
    }

    public boolean isDouble(){
        return isDouble;
    }

    public void roll(){
        int dice1 = (int)(Math.random() * 6) + 1;
        int dice2 = (int)(Math.random() * 6) + 1;
        value = dice1 + dice2;
        isDouble = dice1 == dice2;
    }
}
