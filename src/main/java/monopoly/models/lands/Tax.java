package monopoly.models.lands;

import com.google.gson.annotations.SerializedName;
import monopoly.models.lands.buyStrategy.NonPurchasable;
import monopoly.models.lands.rentStrategy.PayBankStrategy;

public class Tax extends Land {

    public String name;
    @SerializedName("taxCost")
    public int taxCost;
    public Tax() {
        super(new NonPurchasable());
        setRentStrategy(new PayBankStrategy(taxCost));
        System.out.println("Create with tax " + taxCost);
    }

    public void setTaxCost(int taxCost) {
        this.taxCost = taxCost;
        setRentStrategy(new PayBankStrategy(taxCost));
    }

    public int getTaxCost() {
        return taxCost;
    }

    public String getName() {
        return name;
    }
}