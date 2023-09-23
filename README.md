# asmatic-monopoly 

A peer-to-peer agent based version of the classic board game Monopoly.

## What is this?

This is a project for the course "Agents and Multi-Agents Systems" at FEUP. The goal is to create a peer-to-peer agent based version of the classic board game Monopoly.

Our implementation is based on the [JADE](http://jade.tilab.com/) framework. We developed a set of agents that represent the players, each with a different strategy. The agents communicate with each other using the FIPA ACL language. The game is played in a virtual board, where each agent can move around and buy properties. The game ends when only one player is left with money.

## Strategies

The agents have different strategies that they use to play the game. The strategies are:

- **AFK**: The agent does nothing, just moves around the board.
- **Random**: The agent moves randomly around the board and buys properties randomly.
- **Hoarder**: The agent buys properties if it has enough money.
- **Landlord**: The agent buys properties and focuses on building houses and hotels.
- **Picker**: The agent buys more expensive properties.
- **Tortoise**: The agent buys less expensive properties. 

There is a 7th agent that is not a player, but a dealer. The dealer is responsible for creating the board and the properties, and for starting the game, as well as managing the turns.

## Results

We ran 60 games with 6 players, each with a different strategy and 1500$ to start. Here are some results:

- **Most wins**: Picker (14 wins).
- **Most money in a single game**: Picker (7390$).
- **Most money on average**: Landlord (3003$).
- **Most properties on average**: Hoarder (8.84 properties).
