package com.game.model;

import java.util.ArrayList;
import java.util.List;

/*
 * Room represents a room in the game, like the Kitchen, the Living Room etc
 */
public class Room extends Entity{
    private List<Room> adjacentRooms = new ArrayList<>();
    private List<String> jsonAdjacentRooms = new ArrayList<>();
    private List<String> jsonCharactersInRoom = new ArrayList<>();

    public Room(String name, String description){
        super(name, description);
    }

    public boolean isRoomAdjacent(Room room){
        return adjacentRooms.contains(room);
    }

    public void addAdjacentRoom(Room room, Room ...rooms){
        adjacentRooms.add(room);
        for (var room_entry : rooms){
            addAdjacentRoom(room_entry);
        }
    }

    public List<Room> getAdjacentRooms() {
        return adjacentRooms;
    }

    public void setAdjacentRooms(List<Room> adjacentRooms) {
        this.adjacentRooms = adjacentRooms;
    }

    public void addAdjacentRoom(Room room){
        this.adjacentRooms.add(room);
    }

    public List<String> getJsonAdjacentRooms() {
        return jsonAdjacentRooms;
    }

    public List<String> getCharactersInRoom() {
        return jsonCharactersInRoom;
    }

    public void addCharacterToRoom(String name) {
        jsonCharactersInRoom.add(name);
    }

    // returns a formatted StringBuilder String with commas
    public StringBuilder adjacentRoomToString(){
        StringBuilder sb = new StringBuilder();
        for (var room : adjacentRooms){
            sb.append(room.getName()).append(", ");
        }
        return sb.deleteCharAt(sb.length() - 2); // removes the last comma for formatting purposes
    }
}
