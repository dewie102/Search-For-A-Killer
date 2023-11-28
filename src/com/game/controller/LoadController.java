package com.game.controller;

import com.game.model.Item;
import com.game.model.Room;
import com.google.gson.Gson;

import java.io.FileReader;
import java.util.*;

public class LoadController {

    public static Map<String, Room> loadRooms() {
        try (FileReader reader = new FileReader("data/rooms.json")) {
            Room[] rooms = new Gson().fromJson(reader, Room[].class);
            Map<String, Room> roomMap = new HashMap<>();

            for (Room room : rooms) {
                roomMap.put(room.getName(), room);
            }

            return roomMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Item> loadItems() {
        try (FileReader reader = new FileReader("data/items.json")) {
            Item[] items = new Gson().fromJson(reader, Item[].class);
            Map<String, Item> itemMap = new HashMap<>();

            for (Item item : items) {
                itemMap.put(item.getName(), item);
            }

            return itemMap;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}