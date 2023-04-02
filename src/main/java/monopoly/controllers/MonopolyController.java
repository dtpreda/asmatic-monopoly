package monopoly.controllers;

import monopoly.models.Dice;
import monopoly.models.MonopolyBoard;
import monopoly.models.PlayResult;
import monopoly.models.Player;
import monopoly.models.lands.Land;
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


}