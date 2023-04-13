package monopoly.models.stats;

import monopoly.models.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerStats {
    private Player player;

    private List<Integer> money;
    private List<Integer> properties;

    private Integer maxMoney;

    public PlayerStats(Player player) {
        this.player = player;
        money = new ArrayList<>();
        properties = new ArrayList<>();
        maxMoney = 0;
    }

    public void addStats(int money, int properties){
        this.money.add(money);
        this.properties.add(properties);
        if(money > maxMoney){
            maxMoney = money;
        }
    }



    public List<Integer> getMoney() {
        return money;
    }

    public List<Integer> getProperties() {
        return properties;
    }

    public Integer getMaxMoney() {
        return maxMoney;
    }



}
