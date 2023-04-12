package monopoly.actions;

import jade.content.Predicate;
import monopoly.models.lands.Property;

public class SellHouse implements Predicate {
    Property property;
    public SellHouse(Property property) {
        this.property = property;
    }
    public SellHouse() {
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }
}
