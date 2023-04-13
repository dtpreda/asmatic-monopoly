package monopoly.states;

import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.stats.PlayerStats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GameOverState extends MonopolyState{
    public GameOverState(MonopolyBoard board, MonopolyController monopolyController) {
        super(board, monopolyController);

        Long turns = monopolyController.getTurns();
        Map<String, PlayerStats> playerStats = monopolyController.getStats().getPlayerStats();

        //Find winner
        String winner = "";
        int maxMoney = 0;
        int bankrupts = 0;
        for (String playerName : playerStats.keySet()) {

            PlayerStats stats = playerStats.get(playerName);
            Player player = monopolyController.getBoard().getPlayer(playerName);
            if(player.isBankrupt()){
                bankrupts++;
                continue;
            }
            if (stats.getMaxMoney() > maxMoney && !player.isBankrupt()) {
                maxMoney = stats.getMaxMoney();
                winner = playerName;
            }
        }




        String fileName = "game_stats.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write("--------Game--------- ");
            writer.newLine();
            writer.write("Winner - " + winner);
            writer.newLine();
            writer.write("Turns - " + turns);
            writer.newLine();

            for (String playerName : playerStats.keySet()) {
                PlayerStats stats = playerStats.get(playerName);
                int moneySize = stats.getMoney().size();
                writer.write("Money " + playerName + " - ");
                for (int i = 0; i < moneySize; i++) {
                    writer.write(stats.getMoney().get(i) + (i < moneySize - 1 ? ", " : ""));
                }
                writer.newLine();

                int propertiesSize = stats.getProperties().size();
                writer.write("Properties " + playerName + " - ");
                for (int i = 0; i < propertiesSize; i++) {
                    writer.write(stats.getProperties().get(i) + (i < propertiesSize - 1 ? ", " : ""));
                }
                writer.newLine();
            }

            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }


    }



}
