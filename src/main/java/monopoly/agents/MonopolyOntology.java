package monopoly.agents;

import jade.content.onto.BeanOntology;
import jade.content.onto.BeanOntologyException;
import monopoly.actions.*;
import monopoly.models.MonopolyBoard;
import monopoly.models.Player;
import monopoly.models.lands.*;
import monopoly.models.lands.buyStrategy.NonPurchasable;
import monopoly.models.lands.buyStrategy.Purchasable;
import monopoly.models.lands.rentStrategy.NoRentStrategy;
import monopoly.models.lands.rentStrategy.PayBankStrategy;
import monopoly.models.lands.rentStrategy.PayOwnerStrategy;

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
        add(MonopolyBoard.class);
        add(Land.class);
        add(Property.class);
        add(NoRentStrategy.class);
        add(PayBankStrategy.class);
        add(PayOwnerStrategy.class);
        add(NonPurchasable.class);
        add(Purchasable.class);
        add(Chance.class);
        add(CommunityChest.class);
        add(Company.class);
        add(GoToJail.class);
        add(Jail.class);
        add(Parking.class);
        add(RailRoad.class);
        add(Start.class);
        add(Tax.class);
        add(Player.class);

        //TODO: Add actions
        add(StartTurn.class);
        add(BuyLand.class);
        add(BuyHouse.class);
        add(RollDice.class);
        add(EndTurn.class);
        add(PayTax.class);
        add(PerformPayTax.class);
        add(AttemptJailBreak.class);
        add(PrisonAction.class);
        add(PerformBuyLand.class);
        add(ReadyAction.class);
        add(TradeStateAction.class);

    }
}
