package com.game.controller;

import com.game.controller.commands.CommandType;
import com.game.model.*;
import com.game.view.AnsiTextColor;
import com.game.view.CommandConsoleView;
import com.game.view.ConsoleText;
import com.game.model.Character;

import java.util.*;

public class GameController {
    private CommandConsoleView consoleView;
    //private Player player;
//    List<Command> commandList = new ArrayList<>();
    private Player player = LoadController.loadPlayer();
    //List<Command> commandList = new ArrayList<>();
    private final Map<String, Room> rooms = LoadController.loadRooms();
    private final Map<String, Item> items = LoadController.loadItems();
    private static final String DIVIDER = "#################################################";
    private List<ConsoleText> mainText = new ArrayList<>();
    private List<ConsoleText> secondaryText = new ArrayList<>();
    private final Map<String, Command> commandMap = new TreeMap<>();
    private final Map<String, Entity> entityDictionary = new HashMap<>();
    private final Map<String, Character> characters = LoadController.loadCharacters();

    public GameController(){
        fixHasAs();
    }

    public void run(){

        Map<String, Entity> entityDictionary = new HashMap<>();
        entityDictionary.putAll(rooms);
        entityDictionary.putAll(items);
        entityDictionary.putAll(characters);
        mainText = getViewText();
        commandMap.put("go", new Command("go", List.of("run", "move", "walk", "travel"), "Go to a room. e.g. go kitchen", CommandType.TWO_PARTS, this::goCommand));
        commandMap.put("look", new Command("look", List.of("see", "inspect"), "Look at an object or room. e.g. look knife", CommandType.HYBRID, this::lookCommand));
        commandMap.put("quit", new Command("quit", List.of("exit"), "Quits the game, no questions asked.", CommandType.STANDALONE, this::quitCommand));
        commandMap.put("help", new Command("help", List.of(), "It displays this menu.", CommandType.STANDALONE, this::helpCommand));
        commandMap.put("drop", new Command("drop", List.of("place", "put"), "Drop an object from your inventory into your current location", CommandType.TWO_PARTS, this::dropCommand));
        commandMap.put("get", new Command("get", List.of("grab", "pickup", "take"), "Drop an object from your inventory into your current location", CommandType.TWO_PARTS, this::getCommand));
        commandMap.put("talk", new Command("talk", List.of("chat", "speak"), "Talk to another character", CommandType.TWO_PARTS, this::talkCommand));

        // List of entities
        List<String> entities = new ArrayList<>(entityDictionary.keySet());


        // List of entities
        List<String> ignoreList = List.of(
                "the", "at", "an", "a", "of", "around", "to", "with"
        );

        String escapeCommand = "quit";

        consoleView = new CommandConsoleView(List.of(mainText, secondaryText), new ArrayList<>(commandMap.values()), entities, ignoreList);
        while (true){
            String userInput = consoleView.show();
            String[] parts = userInput.split(" ", 2);
            boolean result = false;

            Entity entity = parts.length > 1 ? entityDictionary.get(parts[1]) : null;
            result = commandMap.get(parts[0]).executeCommand(entity);
            mainText.clear();
            mainText.addAll(getViewText());

            if(parts[0].equals(escapeCommand))
                return;
            if(result){
                consoleView.clearErrorMessage();
            }
        }
    }

    private boolean goCommand(Entity target){
        if(target instanceof Room){
            //if the room they are trying to go to is in current location's adjacent rooms
            if (rooms.get(player.getCurrentLocation()).getAdjacentRooms().contains(target)) {
                //mainText.clear();
                secondaryText.clear();
                Room room = (Room) target;
                player.setCurrentLocation(room.getName());
                //mainText.addAll(getViewText());
                return true;
            }
            //if they are trying to go to a room, but not an adjacent one
            consoleView.setErrorMessage(String.format("You can't get to the %s from here.", target.getName()));
            return false;
        }
        //if they are not trying to go to a valid room
        consoleView.setErrorMessage(String.format("%s is not a room, you can't go there.", target.getName()));
        return false;
    }

    private boolean lookCommand(Entity target){
        if(target == null){
            if(lookRoom(rooms.get(player.getCurrentLocation())))
                return true;
        }
        if(target instanceof Room) {
            //This clause is necessary to allow correct error message to print
            if (lookRoom((Room)target)){
                return true;
            }
        }
        else if (target instanceof Item){
            if (lookItem((Item)target)){
                return true;
            }
        }
        else if (target instanceof Character){
            if(lookCharacter((Character)target)){
                return true;
            }
        }
        consoleView.setErrorMessage(String.format("%s is not a valid thing for you to look at" +
                ".", target.getName()));
        return false;
    }

    private boolean lookRoom(Room room){
        secondaryText.clear();
        //If the room they are looking at is the currentLocation
        if(room == rooms.get(player.getCurrentLocation())){
            //print the room description
            secondaryText.add(new ConsoleText(room.getDescription()));
            if(!room.getInventory().getItems().isEmpty()) {
                //print items in room if there are any
                secondaryText.add(new ConsoleText("Items you see: " + room.getInventory()));
            }
            if(!(room.getCharactersInRoom()==null) && !room.getCharactersInRoom().isEmpty()){
                secondaryText.add(new ConsoleText("You see someone you can talk to: " + room.getCharactersInRoom()));
            }
            //print adjacent rooms
            secondaryText.add(new ConsoleText("Rooms you can go to: " + room.getJsonAdjacentRooms()));
            secondaryText.add(new ConsoleText(GameController.DIVIDER, AnsiTextColor.BLUE));
            return true;
        }
        return false;
    }

    private boolean lookItem(Item item){
        secondaryText.clear();
        //if the item is in your inventory or in the inventory of the room are you currently in
        if (player.getInventory().getItems().contains(item) || rooms.get(player.getCurrentLocation()).getInventory().getItems().contains(item)) {
            secondaryText.add(new ConsoleText(item.getDescription()));
            if(!item.getInventory().getItems().isEmpty()) {
                secondaryText.add(new ConsoleText(String.format("The %s contains: %s",item.getName(),item.getInventory())));
            }
            secondaryText.add(new ConsoleText(GameController.DIVIDER, AnsiTextColor.BLUE));
            return true;
        }
        return false;
    }

    private boolean helpCommand(Entity target) {
        secondaryText.clear();
        secondaryText.add(new ConsoleText("Available commands:"));
        for (var command : commandMap.values()) {
            secondaryText.add(new ConsoleText(String.format("%s: \t%s", command.getKeyWord(), command.getDescription())));
        }
        secondaryText.add(new ConsoleText(GameController.DIVIDER, AnsiTextColor.BLUE));
        return true;
    }

    private boolean lookCharacter(Character character){
        if(player.getCurrentLocation().equals(character.getCurrentLocation())){
            secondaryText.add(new ConsoleText(character.getDescription()));
            secondaryText.add(new ConsoleText(GameController.DIVIDER, AnsiTextColor.BLUE));
            return true;
        }
        return false;
    }

    private boolean dropCommand(Entity target){
        secondaryText.clear();
        //if target is instance of Item
        if (target instanceof Item){
            //if target Item is in your inventory
            if (player.getInventory().getItems().contains(target)){
                //add item to current location inventory
                rooms.get(player.getCurrentLocation()).getInventory().add((Item)target);
                //remove item from player inventory
                player.getInventory().getItems().remove((Item)target);
                //mainText.clear();
                //mainText.addAll(getViewText());
                //Tell the player what happened
                secondaryText.add(new ConsoleText(String.format("You dropped the %s",target.getName())));
                secondaryText.add(new ConsoleText(GameController.DIVIDER, AnsiTextColor.BLUE));
                return true;
            }
            //that item is not in your inventory, so you can't drop it
            consoleView.setErrorMessage("You can only drop Items that are in your inventory.");
            return false;
        }
        consoleView.setErrorMessage("You can only drop Items.");
        return false;
    }

    private boolean talkCommand(Entity target) {
        //If the target is a character
        if(target instanceof Character){
            //If the target is in the same room as the player
            if (((Character) target).getCurrentLocation().equals(player.getCurrentLocation())){
                secondaryText.add(new ConsoleText(String.format("%s says:",target.getName())));
                secondaryText.add(new ConsoleText("Hello there Detective! I'm not interested in talking to you right now.", AnsiTextColor.PURPLE));
                secondaryText.add(new ConsoleText(GameController.DIVIDER, AnsiTextColor.BLUE));
                return true;
            }
            consoleView.setErrorMessage(String.format("You can't talk to Characters that are not in the same room as you."));
            return false;
        }
        consoleView.setErrorMessage(String.format("You can only talk to Characters."));
        return false;
    }

    private boolean getCommand(Entity target){
        secondaryText.clear();
        if(target instanceof Item){
            Room currentRoom = rooms.get(player.getCurrentLocation());
            Item item = (Item)target;
            if(!item.isPickUpable())
            {
                consoleView.setErrorMessage(String.format("Nice try, but no, you can't pick up %s", item.getName()));
                return false;
            }
            if(currentRoom.getInventory().contains(item)){
                player.getInventory().add(item);
                currentRoom.getInventory().remove(item);
                mainText.clear();
                mainText.addAll(getViewText());
                secondaryText.add(new ConsoleText(String.format("You added %s to your inventory.",target.getName())));
                secondaryText.add(new ConsoleText(GameController.DIVIDER, AnsiTextColor.BLUE));
                return true;
            }
            consoleView.setErrorMessage("That item is not in this room.");
        }
        consoleView.setErrorMessage("That is not an item.");
        return false;
    }

    private boolean quitCommand(Entity target){
        return true;
    }

    private List<ConsoleText> getViewText(){
        // View text to be passed to our view
        List<ConsoleText> result = new ArrayList<>();
        result.add(new ConsoleText(GameController.DIVIDER, AnsiTextColor.BLUE));
        result.add(new ConsoleText(String.format("Player Location: %s", player.getCurrentLocation())));
        result.add(new ConsoleText(String.format("Inventory: %s", player.getInventory())));
        result.add(new ConsoleText(GameController.DIVIDER, AnsiTextColor.BLUE));
        return result;
    }

    private void fixHasAs(){
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

}
