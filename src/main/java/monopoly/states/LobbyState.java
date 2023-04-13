package monopoly.states;

import monopoly.agents.brains.AgentBrain;
import monopoly.controllers.BoardController;
import monopoly.controllers.MonopolyController;
import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;
import monopoly.models.lands.Property;

import java.util.List;

public class LobbyState extends MonopolyState{

    private String colors[] = {"red", "blue", "green", "yellow", "orange", "purple", "pink", "brown"};

    public LobbyState(MonopolyBoard board, MonopolyController monopolyController){
        super(board, monopolyController);
    }

    public Player addPlayer(String name, AgentBrain brain){
        List<Player> players = board.getPlayers();
        for(Player player : players){
            if(player.getName().equals(name)){
                return null;
            }
        }
        String color = colors[players.size()];
        Player player = new Player(players.size(), name, 1500, 0, color, brain);
        board.addPlayer(player);
        return player;
    }

    public void startGame(){
        /*
        changeState(new TradeState(board, monopolyController));

        final Player player = board.getCurrentPlayer();


        monopolyController.setState(new BuyLandState(player, board, board.getLands().get(1), monopolyController));
        BuyLandState state = (BuyLandState) monopolyController.getState();
        state.buyLand(player);

        monopolyController.setState(new BuyLandState(player, board, board.getLands().get(3), monopolyController));
        state = (BuyLandState) monopolyController.getState();
        state.buyLand(player);

        monopolyController.setState(new TradeState(board, monopolyController));

        final TradeState tradeState = (TradeState) monopolyController.getState();
        tradeState.buyHouse((Property) board.getLands().get(1), player);
        tradeState.buyHouse((Property) board.getLands().get(3), player);
        tradeState.buyHouse((Property) board.getLands().get(1), player);
        */
        monopolyController.setState(new RollState(board, monopolyController));






    }
}
