package com.game.model;

/**
 * Player class that is used to create the player object for the game.
 * The player will have a current location and an inventory relationship.
 */

public class Player {

    // INSTANCE VARIABLES
    private String currentLocation;
    private Inventory inventory = new Inventory(
            new Item("Pen", "Blue pen"),
            new Item("Glove", "Evidence glove"));

    // CONSTRUCTOR
    public Player(String currentLocation) {
        setCurrentLocation(currentLocation);
    }

    // METHODS
        // move the player to a new location
    public void move(String newLocation) {
        // update the player location
        setCurrentLocation(newLocation);
    }

    public void look(Entity entity) {
        //TODO: add the logic to allow the player to look at items
        //TODO: change the argument type to match whatever is being passed in
    }

    // GETTERS AND SETTERS
    public String getCurrentLocation() {
        return currentLocation;
    }

    public StringBuilder getInventory() {
        // builds a string with all items in the inventory separated by a comma and a space.
        StringBuilder sb = new StringBuilder();
        // appends each item to the StringBuilder
        for (Item item : inventory.getItems()) {
            sb.append(item.getName()).append(", ");
        }
        // removes the last coma for formatting purposes
        return sb.deleteCharAt(sb.length() - 2);
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}