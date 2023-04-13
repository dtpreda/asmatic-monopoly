package monopoly.models.stats;

import monopoly.models.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerStats {
    private Player player;

    private List<Integer> money;
    private List<Integer> properties;

    private Integer maxMoney;

    private List<Integer> assets;

    public PlayerStats(Player player) {
        this.player = player;
        money = new ArrayList<>();
        properties = new ArrayList<>();
        assets = new ArrayList<>();
        maxMoney = 0;
    }

    public void addStats(int money, int properties, int assets){
        this.money.add(money);
        this.properties.add(properties);
        this.assets.add(assets);
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

    public List<Integer> getAssets() {
        return assets;
    }

    public void setAssets(List<Integer> assets) {
        this.assets = assets;
    }
}
