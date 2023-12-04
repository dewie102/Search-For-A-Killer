package com.game.controller;

import com.game.controller.io.JsonConversation;
import com.game.model.*;
import com.game.model.Character;
import com.google.gson.Gson;

import java.io.FileReader;
import java.util.*;

public class LoadController {
    private static Map<String, Room> rooms;
    private static Map<String, Item> items;
    private static Map<String, Character> characters;
    private static Player player;
    // Making this class fully static
    private LoadController(){}

    public static void loadAllEntities(){
        rooms = loadRooms();
        items = loadItems();
        characters = loadCharacters();
        player = loadPlayer();
        loadConversations();

        fixAllHasAs();
    }

    private static void fixAllHasAs(){
        for (Room room : rooms.values()){
            room.setInventory(new Inventory());
            room.setAdjacentRooms(new ArrayList<>());
            for (String key : room.getJsonInventory()){
                Item item =  items.get(key);
                Inventory inventory = room.getInventory();
                inventory.add(item);
            }
            for (String key : room.getJsonAdjacentRooms()){
                Room r = rooms.get(key);
                room.addAdjacentRoom(r);
            }
        }
        // Add the HAS-A for each character inventory item
        for(Character character : characters.values()){
            character.setInventory(new Inventory());
            for (String key : character.getJsonInventory()){
                Item item = items.get(key);
                character.getInventory().add(item);
            }
        }

        // Add the HAS-A for each player inventory item
        player.setInventory(new Inventory());
        for (String key : player.getJsonInventory()){
            Item item = items.get(key);
            player.getInventory().add(item);
        }
    }

    private static Map<String, Room> loadRooms() {
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

    private static Map<String, Item> loadItems() {
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

    private static Map<String, Character> loadCharacters() {
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

    private static Player loadPlayer() {
        try (FileReader reader = new FileReader("data/Player.json")) {
            return new Gson().fromJson(reader, Player.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JsonConversation loadConversations(){
        JsonConversation conversations;
        try (FileReader reader = new FileReader("data/Conversation.json")) {
            conversations = new Gson().fromJson(reader, JsonConversation.class);
            for (Character character : getCharacters().values()){
                character.setConversation(new Conversation());
                character.getConversation().addDialog(new Dialog(conversations.getRandomGreeting(player), conversations.getRandomGreeting(character)));
                character.getConversation().addDialog(new Dialog(conversations.getRandomIntroduction(player), conversations.getRandomIntroduction(character)));
                character.getConversation().addDialog(new Dialog(conversations.getRandomFarewell(player), conversations.getRandomFarewell(character)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, Room> getRooms() {
        if(rooms == null)
            loadAllEntities();
        return rooms;
    }

    public static Map<String, Item> getItems() {
        if(items == null)
            loadAllEntities();
        return items;
    }

    public static Map<String, Character> getCharacters() {
        if(characters == null)
            loadAllEntities();
        return characters;
    }

    public static Player getPlayer() {
        if(player == null)
            loadAllEntities();
        return player;
    }
}