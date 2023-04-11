package org.example;

import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import monopoly.controllers.MonopolyController;
import jade.core.Runtime;
import jade.core.Profile;
import monopoly.agents.DealerAgent;

public class Main {
    public static void main(String[] args) throws StaleProxyException {
        System.out.println("Hello world!");

        MonopolyController monopolyController = new MonopolyController();
        monopolyController.startGame();

        Runtime rt = Runtime.instance();
        Profile p1 = new ProfileImpl();
        p1.setParameter(Profile.MAIN_HOST, "192.168.1.154");
        p1.setParameter(Profile.MAIN_PORT, "1099");
        p1.setParameter(Profile.CONTAINER_NAME, "Main-Container");
        ContainerController container = rt.createMainContainer(p1);

        AgentController ac1 = container.acceptNewAgent("John", new DealerAgent());
        ac1.start();
    }
}