package monopoly.states;

import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;

import java.util.ArrayList;
import java.util.List;

public class TradeState extends MonopolyState{
    List<Player> playersReady;

    public TradeState(MonopolyBoard board, MonopolyController monopolyController) {
        super(board, monopolyController);
        playersReady = new ArrayList<>();
    }

    public void playerReady(Player player){
        if(!playersReady.contains(player)){
            playersReady.add(player);
        }
    }

    public boolean canStart(){
        return playersReady.size() == board.getPlayers().size();
    }

    public boolean start(){
        if(canStart()){
            monopolyController.setState(new RollState(board, monopolyController));
            return true;
        }
        return false;
    }

    public boolean performTrade(Trade trade){
        Player buyer = trade.getBuyer();
        Player seller = trade.getSeller();
        int price = trade.getPrice();

        if(buyer.getMoney() < price){
            return false;
        }

        seller.addMoney(price);
        buyer.removeMoney(price);

        Property property = trade.getProperty();
        property.setBuilding(0);
        property.updateRent();
        Purchasable purchasable = (Purchasable) property.getBuyStrategy();
        purchasable.setOwner(buyer);
        return true;
    }

    public String playersReady(){
        //Join player names
        String players = "";
        for(Player player : playersReady){
            players += player.getName() + ", ";
        }
        return players;
    }


}
