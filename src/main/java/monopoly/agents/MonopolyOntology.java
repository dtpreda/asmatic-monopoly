package monopoly.agents;

import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import monopoly.actions.*;

public class MonopolyOntology extends BeanOntology {
    public static final String ONTOLOGY_NAME = "Monopoly Ontology";
    private static MonopolyOntology SINGLETON;

    public static MonopolyOntology getInstance() {
        if (SINGLETON == null) {
            try {
                SINGLETON = new MonopolyOntology();
            } catch (BeanOntologyException e) {
                e.printStackTrace();
            }
        }

        return SINGLETON;
    }

    private MonopolyOntology() throws BeanOntologyException {
        super(ONTOLOGY_NAME);

        //TODO: Add concepts

        //TODO: Add actions
        add(StartTurn.class);
        add(BuyLand.class);
        add(BuyHouse.class);
        add(RollDice.class);
        add(EndTurn.class);
        add(PayTax.class);
        add(PerformPayTax.class);
    }
}
