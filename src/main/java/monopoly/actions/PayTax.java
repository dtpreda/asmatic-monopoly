package monopoly.actions;

import jade.content.AgentAction;
import jade.content.Predicate;

public class PayTax implements Predicate {
    private int amount;
    public PayTax(int amount){
        this.amount = amount;
    }
}
