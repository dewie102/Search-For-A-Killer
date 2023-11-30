package com.game.model;

/*
 * This class represents a character in the game, like a suspect
 */
public class Character extends Entity{
    // A character must be in a Room
    private Room currentRoom;

    //TODO once all characters are loaded from JSON, this won't be required
    public Character(String name, String description, Room start) {
        super(name, description);
        setCurrentRoom(start);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
}
