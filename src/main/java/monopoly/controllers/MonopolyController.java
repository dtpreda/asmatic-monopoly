package monopoly.controllers;

import monopoly.actions.NeedToSell;
import monopoly.agents.brains.AgentBrain;
import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.PlayResult;
import monopoly.models.Player;
import monopoly.models.lands.Land;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;
import monopoly.models.lands.rentStrategy.NoRentStrategy;
import monopoly.models.stats.Statistics;
import monopoly.parser.BoardLoader;
import monopoly.states.LobbyState;
import monopoly.states.MonopolyState;
import monopoly.states.SellStuffState;

import java.util.List;

public class MonopolyController{

    private BoardController boardController;

    public MonopolyController(){
        startGame();
    }
    private MonopolyBoard board;
    private MonopolyState state;

    private Statistics stats;

    private Long turns;

    public void addTurn(){
        turns ++;
    }
    public void startGame(){
        BoardLoader boardLoader = new BoardLoader();
        board = boardLoader.loadBoard("board.json");
        boardController = new BoardController(board);
        state = new LobbyState(board, this);
        turns = 0L;

    }

    public void bankRuptPlayer(Player player){
        player.setBankrupt(true);
        //Remove ownership of all lands
        List<Property> properties = board.getProperties(player);
        for(Property prop : properties){
            Purchasable purchasable = (Purchasable) prop.getBuyStrategy();
            purchasable.setOwner(null);
            prop.setRentStrategy(new NoRentStrategy());
            prop.setBuilding(0);
            prop.updateRent();
        }

        //Transfer money to last player
        if(getState() instanceof SellStuffState){
            SellStuffState sellStuffState = (SellStuffState) getState();
            Land land  = sellStuffState.getPreviousState().getLand();
            if(land instanceof Property){
                Property property = (Property) land;
                Purchasable purchasable = (Purchasable) property.getBuyStrategy();
                Player lastPlayer = board.getPlayer(purchasable.getOwner());
                lastPlayer.addMoney(player.getMoney());
            }
        }
    }
    public MonopolyBoard getBoard(){
        return board;
    }

    public MonopolyState getState(){
        return state;
    }
    public void setState(MonopolyState state){
        this.state = state;
    }

    public BoardController getBoardController(){
        return boardController;
    }

    public Player addPlayer(String name, AgentBrain brain){
        if(state instanceof LobbyState){
            return ((LobbyState) state).addPlayer(name, brain);
        }
        return null;
    }


    public Statistics getStats() {
        return stats;
    }

    public void setStats(Statistics stats) {
        this.stats = stats;
    }

    public Long getTurns() {
        return turns;
    }

    public void setTurns(Long turns) {
        this.turns = turns;
    }
}