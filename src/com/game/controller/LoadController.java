package com.game.controller;

import com.game.model.Item;
import com.game.model.Player;
import com.game.model.Room;
import com.game.model.Character;
import com.google.gson.Gson;

import java.io.FileReader;
import java.util.*;

public class LoadController {

    public static Map<String, Room> loadRooms() {
        try (FileReader reader = new FileReader("data/Rooms.json")) {
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
        try (FileReader reader = new FileReader("data/Items.json")) {
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

    public static Map<String, Character> loadCharacters() {
        try (FileReader reader = new FileReader("data/Character.json")) {
            Character[] characters = new Gson().fromJson(reader, Character[].class);
            Map<String, Character> characterMap = new HashMap<>();

            for (Character character : characters) {
                characterMap.put(character.getName(), character);
            }
            return characterMap;
        } catch (Exception e) {
            System.out.printf("Error reading the character json file: %s%n", e.getMessage());
        }
        return null;
    }

    public static Player loadPlayer() {
        try (FileReader reader = new FileReader("data/Player.json")) {
            return new Gson().fromJson(reader, Player.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}