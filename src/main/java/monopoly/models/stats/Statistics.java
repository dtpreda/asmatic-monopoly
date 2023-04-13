package monopoly.models.stats;

import monopoly.models.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistics {
    private Map<String, PlayerStats> playerStats;
    private Long turn;

    public Statistics(List<Player> players){
        playerStats = new HashMap<>();
        for(Player player : players){
            playerStats.put(player.getName(), new PlayerStats(player));
        }
    }

    public void addStats(String name, int money, int properties){
        playerStats.get(name).addStats(money, properties);
    }

    public Map<String, PlayerStats> getPlayerStats() {
        return playerStats;
    }



}
