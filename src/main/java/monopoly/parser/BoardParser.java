package monopoly.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import monopoly.models.MonopolyBoard;
import monopoly.models.lands.*;
import monopoly.models.lands.buyStrategy.NonPurchasable;
import monopoly.models.lands.buyStrategy.Purchasable;
import monopoly.models.lands.buyStrategy.PurchasableStrategy;
import monopoly.models.lands.rentStrategy.NoRentStrategy;
import monopoly.models.lands.rentStrategy.PayBankStrategy;
import monopoly.models.lands.rentStrategy.PayOwnerStrategy;
import monopoly.models.lands.rentStrategy.RentStrategy;

import java.io.File;
import java.util.Scanner;

public class BoardParser{
    BoardParser(){
    }

    public MonopolyBoard parse(final String jsonStr){
        RuntimeTypeAdapterFactory<Land> landAdapter = RuntimeTypeAdapterFactory
                .of(Land.class, "type")
                .registerSubtype(Property.class, "property")
                .registerSubtype(Chance.class, "chance")
                .registerSubtype(RailRoad.class, "railroad")
                .registerSubtype(Tax.class, "tax")
                .registerSubtype(Start.class, "start")
                .registerSubtype(CommunityChest.class, "communitychest")
                .registerSubtype(Jail.class, "jail")
                .registerSubtype(GoToJail.class, "gotojail")
                .registerSubtype(Parking.class, "parking")
                .registerSubtype(Company.class, "company");
        RuntimeTypeAdapterFactory<PurchasableStrategy> purchasableAdapter = RuntimeTypeAdapterFactory
                .of(PurchasableStrategy.class, "type")
                .registerSubtype(Purchasable.class, "purchasable")
                .registerSubtype(NonPurchasable.class, "nonpurchasable");


        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(landAdapter)
                .registerTypeAdapterFactory(purchasableAdapter)
                .create();
        MonopolyBoard board = gson.fromJson(jsonStr, MonopolyBoard.class);

        for(Land land : board.getLands()){
            if((land instanceof Tax)){
                Tax tax = (Tax) land;
                tax.setRentStrategy(new PayBankStrategy(tax.getTaxCost()));
            }
        }
        System.out.println(gson.toJson(board));
        return board;
    }
}