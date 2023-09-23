package monopoly.states;

import monopoly.controllers.MonopolyController;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.Trade;
import monopoly.models.lands.Property;
import monopoly.models.lands.buyStrategy.Purchasable;
import monopoly.models.lands.rentStrategy.PayOwnerStrategy;
import monopoly.models.lands.rentStrategy.RentStrategy;

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
        return playersReady.size() == board.getPlayers().stream().filter(player -> !player.isBankrupt()).count();
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





        Property property = trade.getProperty();
        Purchasable purchasable = (Purchasable) property.getBuyStrategy();
        if(!seller.getName().equals(purchasable.getOwner())){
            return false;
        }
        property.setBuilding(0);


        purchasable.setOwner(buyer);

        property.setRentStrategy(new PayOwnerStrategy(buyer));
        property.updateRent();

        if(board.ownsAllPropertiesColor(buyer.getName(), property.getColor())){
            for(Property prop: board.getOwnedPropertiesColorPlayer(buyer.getName(), property.getColor())){
                prop.increaseBuilding();
                prop.updateRent();
            }
        }

        seller.addMoney(price);
        buyer.removeMoney(price);

        System.out.println("Player " + buyer.getName() + " traded " + property.getName() + " for " + price + " with " + seller.getName());
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
