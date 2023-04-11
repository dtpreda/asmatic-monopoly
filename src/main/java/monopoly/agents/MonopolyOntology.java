package monopoly.agents;

import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;

public class MonopolyOntology extends BeanOntology {
    private static final String ONTOLOGY_NAME = "Monopoly Ontology";
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
    }
}
