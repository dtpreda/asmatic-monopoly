package monopoly.states;

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
            System.out.println("NOT YOUR TURN " + player.getName());
            return new PlayResult(false);
        }

        final Dice dice = new Dice();
        System.out.println("Dice value = " + dice.getValue() + (dice.isDouble() ? " (double)" : ""));
        board.setLastDice(dice);
        Land land = boardController.movePlayer(player, dice.getValue());
        final PlayResult result = moveResult(player, land, dice);
        return result;
    }

    private PlayResult moveResult(Player player, Land land, Dice dice){
        PlayResult playResult = new PlayResult(dice, land);
        //doNothingVisit(playResult);

        if(land instanceof Jail){
            jailVisit(player, (Jail) land, playResult);
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

        return playResult;
    }

    /*
        Advance to the next turn. Return false if isn't the player's turn, or requires player action
     */
    public boolean finishTurn(Player player){
        if(!board.getCurrentPlayer().equals(player)){
            return false;
        }

        return true;
    }

    public void jailVisit(Player player, Jail jail, PlayResult playResult){
        playResult.setPlayResultToken(PlayResultToken.END_TURN);
        boardController.nextPlayer();
    }
    public void propertyVisit(Player player, Property property, PlayResult playResult){
        Dice dice = playResult.getDice();
        if(property.canPurchase()){
            changeState(new BuyLandState(player, board, monopolyController));
            playResult.setPlayResultToken(PlayResultToken.BUY_LAND);
        } else if(property.getRentStrategy().hasToPayRent(player)){
            changeState(new PayRentState(player, property, dice, board, monopolyController));
            playResult.setPlayResultToken(PlayResultToken.PAY_RENT);
        } else {
            //It's owned by the player, do nothing
            changePlayerTurn(playResult);
        }
    }

    public void taxVisit(Player player, Tax tax, PlayResult playResult){
        Dice dice = playResult.getDice();
        if(tax.getRentStrategy().hasToPayRent(player)){
            changeState(new PayRentState(player, tax, dice, board, monopolyController));
            playResult.setPlayResultToken(PlayResultToken.PAY_RENT);
            //player.addMoney(-tax.getRentStrategy().getRent(playResult.getDice().getValue()));
        }
        //changePlayerTurn(playResult);
    }

    public void goToJailVisit(Player player, PlayResult playResult){
        boardController.sendToJail(player);
        playResult.setPlayResultToken(PlayResultToken.END_TURN);
        boardController.nextPlayer();
    }

    public void companyVisit(Player player, Company company, PlayResult playResult){
        Dice dice = playResult.getDice();
        if(company.canPurchase()){
            changeState(new BuyLandState(player, board, monopolyController));
            playResult.setPlayResultToken(PlayResultToken.BUY_LAND);
        } else if(company.getRentStrategy().hasToPayRent(player)){
            changeState(new PayRentState(player, company, dice, board, monopolyController));
            playResult.setPlayResultToken(PlayResultToken.PAY_RENT);
        } else {
            changePlayerTurn(playResult);
        }
    }

    private void doNothingVisit(PlayResult playResult){
        playResult.setPlayResultToken(PlayResultToken.END_TURN);
        changePlayerTurn(playResult);
    }

    //Change player turn based on the dice. If is double, maintain player's turn
    private void changePlayerTurn(PlayResult playResult){
        //TODO:: Check for 3 straight times
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
            return true;
        }
        return false;
    }
}
