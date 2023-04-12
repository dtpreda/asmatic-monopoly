package monopoly.actions;


import jade.content.AgentAction;
import jade.content.Predicate;
import monopoly.models.lands.Property;

public class BuyHouse implements Predicate {

    private Property property;
    public BuyHouse() {
    }

    public BuyHouse(Property property) {
        super();
        this.property = property;
    }

    public Property getProperty() {
        return property;
    }
    public void setProperty(Property property) {
        this.property = property;
    }
}
