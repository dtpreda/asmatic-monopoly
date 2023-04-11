package monopoly.states;

import monopoly.controllers.BoardController;
import monopoly.controllers.MonopolyController;
import monopoly.models.*;
import monopoly.models.lands.*;

// WAiting to roll the dice
public class RollState extends MonopolyState{

    public RollState(MonopolyBoard board, MonopolyController monopolyController){
        super(board, monopolyController);
    }
    /*
        Roll the dice
     */
    public PlayResult play(Player player){
        if(!player.equals(board.getCurrentPlayer())){
            return new PlayResult(false);
        }
        final Dice dice = new Dice();
        board.setDice(dice);
        Land land = boardController.movePlayer(player, dice.getValue());
        final PlayResult result = moveResult(player, land, dice);
        return result;
    }

    private PlayResult moveResult(Player player, Land land, Dice dice){
        PlayResult playResult = new PlayResult(dice, land);
        if(land instanceof Jail){

        }
        else if(land instanceof GoToJail){
            goToJailVisit(player, playResult);
        } else if(land instanceof RailRoad){
            doNothingVisit(playResult);
        } else if(land instanceof Property){
            propertyVisit(player, (Property) land, playResult);
        } else if(land instanceof Company){
            companyVisit(player, (Company) land, playResult);
        } else if(land instanceof Tax){
            taxVisit(player, (Tax) land, playResult);
        } else if(land instanceof CommunityChest){
            doNothingVisit(playResult);
        } else if(land instanceof Chance){
            doNothingVisit(playResult);
        } else {
            doNothingVisit(playResult);
        }

        if(dice.isDouble()){
            return new PlayResult(true);
        }
        return playResult;
    }

    /*
        Advance to the next turn. Return false if isn't the player's turn, or requires player action
     */
    public boolean finishTurn(Player player){
        if(!board.getCurrentPlayer().equals(player)){
            return false;
        }

        board.nextPlayer();
        return true;
    }

    public void propertyVisit(Player player, Property property, PlayResult playResult){
        if(property.canPurchase()){
            changeState(new BuyLandState(player, board, monopolyController));
            playResult.setPlayResultToken(PlayResultToken.BUY_LAND);
        } else if(property.getRentStrategy().hasToPayRent(player)){
            changeState(new PayRentState(player, property, board, monopolyController));
            playResult.setPlayResultToken(PlayResultToken.PAY_RENT);
        } else {
            changePlayerTurn(playResult);
        }
    }

    public void taxVisit(Player player, Tax tax, PlayResult playResult){
        if(tax.getRentStrategy().hasToPayRent(player)){
            player.addMoney(-tax.getRentStrategy().getRent(playResult.getDice().getValue()));
        }
        changePlayerTurn(playResult);
    }

    public void goToJailVisit(Player player, PlayResult playResult){
        boardController.sendToJail(player);
        playResult.setPlayResultToken(PlayResultToken.JAIL);
        boardController.nextPlayer();
    }

    public void companyVisit(Player player, Company company, PlayResult playResult){
        if(company.canPurchase()){
            changeState(new BuyLandState(player, board, monopolyController));
            playResult.setPlayResultToken(PlayResultToken.BUY_LAND);
        } else if(company.getRentStrategy().hasToPayRent(player)){
            changeState(new PayRentState(player, company, board, monopolyController));
            playResult.setPlayResultToken(PlayResultToken.PAY_RENT);
        } else {
            changePlayerTurn(playResult.getDice());
        }
    }

    private void doNothingVisit(PlayResult playResult){
        changePlayerTurn(playResult);
    }

    //Change player turn based on the dice. If is double, maintain player's turn
    private void changePlayerTurn(PlayResult playResult){
        boolean changed = changePlayerTurn(playResult.getDice());
        if(!changed){
            playResult.setPlayResultToken(PlayResultToken.PLAY_AGAIN);
        } else {
            playResult.setPlayResultToken(PlayResultToken.END_TURN);
        }
    }
    private boolean changePlayerTurn(Dice dice){
        if(!dice.isDouble()){
            boardController.nextPlayer();
            return false;
        }
        return true;
    }
}