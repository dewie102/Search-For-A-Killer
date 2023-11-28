package com.game.model;

import java.util.ArrayList;
import java.util.List;

/*
 * Room represents a room in the game, like the Kitchen, the Living Room etc
 */
public class Room extends Entity{
    private List<Room> adjacentRooms = new ArrayList<>();

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
}
