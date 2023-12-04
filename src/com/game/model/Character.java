package com.game.model;

/*
 * This class represents a character in the game, like a suspect
 */
public class Character extends Entity{
    // A character must be in a Room
    private String currentLocation;
    private Conversation conversation = new Conversation();


    public Character(String name, String description, String currentLocation){
        super(name, description);
        setCurrentLocation(currentLocation);
    }

    // GETTERS AND SETTERS

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
