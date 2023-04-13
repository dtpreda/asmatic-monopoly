package monopoly.controllers;

import monopoly.agents.brains.AgentBrain;
import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.PlayResult;
import monopoly.models.Player;
import monopoly.models.lands.Land;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;
import monopoly.parser.BoardLoader;
import monopoly.states.LobbyState;
import monopoly.states.MonopolyState;

import java.util.List;

public class MonopolyController{

    private BoardController boardController;

    public MonopolyController(){
        startGame();
    }
    private MonopolyBoard board;
    private MonopolyState state;

    public void startGame(){
        BoardLoader boardLoader = new BoardLoader();
        board = boardLoader.loadBoard("board.json");
        boardController = new BoardController(board);
        state = new LobbyState(board, this);

    }

    public void bankRuptPlayer(Player player){
        player.setBankrupt(true);
        //Remove ownership of all lands
        List<Property> properties = board.getProperties(player);
        for(Property prop : properties){
            Purchasable purchasable = (Purchasable) prop.getBuyStrategy();
            purchasable.setOwner(null);
            prop.setBuilding(0);
            prop.updateRent();
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


}