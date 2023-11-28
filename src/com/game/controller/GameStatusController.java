package com.game.controller;

import com.game.model.Inventory;
import com.game.view.AnsiTextColor;
import com.game.view.ConsoleText;
import com.game.view.ConsoleView;

import java.util.List;

/**
 * The GameStatusController class is used to control the game status menu within the game.
 * It'll display information such as the player location and the player inventory
 */

class GameStatusController {

    // INSTANCE VARIABLES
    private String playerLocation;
    private Inventory inventory;

    // CONSTRUCTOR
    GameStatusController(String playerLocation, Inventory inventory) {
        setPlayerLocation(playerLocation);
        setInventory(inventory);
    }

    // METHODS
    void displayGameStatus() {
        ConsoleView consoleView = new ConsoleView();
        ConsoleText statusBars =  new ConsoleText("#################################################", AnsiTextColor.BLUE);
        consoleView.add(statusBars);
        consoleView.add(new ConsoleText(String.format("Player Location: %s", getPlayerLocation())));
        consoleView.add(new ConsoleText(String.format("Inventory: %s", getInventory().toString())));
        consoleView.add(statusBars);
        consoleView.show();
    }

    // GETTERS AND SETTERS
    public String getPlayerLocation() {
        return playerLocation;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void setPlayerLocation(String playerLocation) {
        this.playerLocation = playerLocation;
    }
}