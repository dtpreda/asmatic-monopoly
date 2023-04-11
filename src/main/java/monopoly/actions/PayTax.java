package monopoly.actions;

import jade.content.AgentAction;
import jade.content.Predicate;

public class PayTax implements Predicate {
    private int amount;
    public PayTax(int amount){
        this.amount = amount;
    }

    public PayTax(){

    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


}
