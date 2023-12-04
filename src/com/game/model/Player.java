package com.game.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Player class that is used to create the player object for the game.
 * The player will have a current location and an inventory relationship.
 */

public class Player extends Entity {

    // INSTANCE VARIABLES
    private String currentLocation;
    private List<String> playerHistory = new ArrayList<>();

    // CONSTRUCTOR
    public Player(String name, String description, String currentLocation) {
        super(name, description);
        setCurrentLocation(currentLocation);
    }

    // METHODS
        // move the player to a new location
    public void move(String newLocation) {
        // update the player location
        setCurrentLocation(newLocation);
    }

    // GETTERS AND SETTERS
    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public StringBuilder getInventoryString() {
        // builds a string with all items in the inventory separated by a comma and a space.
        StringBuilder sb = new StringBuilder();
        // appends each item to the StringBuilder
        for (Item item : getInventory().getItems()) {
            sb.append(item.getName()).append(", ");
        }
        // removes the last coma for formatting purposes
        return sb.deleteCharAt(sb.length() - 2);
    }

    public List<String> getPlayerHistory() {
        return playerHistory;
    }

    public void setPlayerHistory(List<String> playerHistory) {
        this.playerHistory = playerHistory;
    }

    public void addToPlayerHistory(String history){
        playerHistory.add(history);
    }
}