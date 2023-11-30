package com.game.controller;

import com.game.model.*;
import com.game.view.AnsiTextColor;
import com.game.view.CommandConsoleView;
import com.game.view.ConsoleText;

//TODO: remove import if we rename class
import com.game.model.Character;

import java.util.*;

public class GameController {
    private CommandConsoleView consoleView;
    private Player player;
    List<Command> commandList = new ArrayList<>();
    private final Map<String, Room> rooms = LoadController.loadRooms();
    private final Map<String, Item> items = LoadController.loadItems();
    //TODO: assign to LoadController once character JSON is implemented
    private final Map<String, Character> characters = new HashMap<>();

    public GameController(){
        fixHasAs();
    }

    public void run(){

        //TODO: remove test character after NPCs loaded from JSON
        Character testCharacter = new Character("Sean Bean", "He looks alive", rooms.get("Kitchen"));
        characters.put(testCharacter.getName(), testCharacter);

        player = new Player(rooms.get("Kitchen").getName()); //Kind of roundabout but you get the idea!

        Map<String, Entity> entityDictionary = new HashMap<>();
        entityDictionary.putAll(rooms);
        entityDictionary.putAll(items);
        entityDictionary.putAll(characters);

        player.getInventory().add(items.get("Pen"));
        player.getInventory().add(items.get("Glove"));



        commandList.add(new Command("go", List.of("run", "move", "walk"), "Go to a room. e.g. go kitchen", false));
        commandList.add(new Command("look", List.of("see", "inspect"), "Look at an object. e.g. look knife", false));
        commandList.add(new Command("quit", List.of(), "Quits the game, no questions asked.", true));
        commandList.add(new Command("help", List.of(), "It displays this menu.", true));
        commandList.add(new Command("drop", List.of("place"), "Drop an object from your inventory into your current location", false));
        commandList.add(new Command("talk", List.of("chat", "speak"), "Talk to another character", false));

        // Map of Commands that require a target Entity, for these to be valid they must have two parts, the command itself
        // and a target. e.g. <go there>, <get that>
        Map<String, List<String>> commands = Map.of(
                "go", List.of("run", "move", "walk"),
                "look", List.of("see", "inspect"),
                "drop", List.of(),
                "talk", List.of("chat", "speak")
        );

        // Standalone commands, these commands don't require a target. e.g. <quit>, <help>
        List<String> standaloneCommands = List.of(
                "quit", "help"
        );

        // List of entities
        List<String> entities = new ArrayList<>(entityDictionary.keySet());


        // List of entities
        List<String> ignoreList = List.of(
                "the", "at", "an", "a", "of", "around", "to", "with"
        );

        String escapeCommand = "quit";
        consoleView = new CommandConsoleView(getViewText(), commands, standaloneCommands, entities, ignoreList, escapeCommand);
        while (true){
            String userInput = consoleView.show();
            String[] parts = userInput.split(" ", 2);
            boolean result = false;

            if(parts.length > 1 && parts[1] != null) {
                consoleView.setText(getViewText());
                Entity entity = entityDictionary.get(parts[1]);
                switch (parts[0]) {
                    case "go":
                        result = goCommand(entity);
                        consoleView.setText(getViewText());
                        break;
                    case "look":
                        result = lookCommand(entity);
                        break;
                    case "drop":
                        result = dropCommand(entity);
                        break;
                    case "talk":
                        result = talkCommand(entity);
                        break;
                }

            }
            if(parts[0].equals(escapeCommand))
                return;
            if(parts[0].equals("help")) {
                consoleView.getText().addAll(helpCommand());
                result = true;
            }
            if(result){
                consoleView.clearErrorMessage();
            }
        }
    }

    private boolean goCommand(Entity target){
        if(target instanceof Room){
            //if the room they are trying to go to is in current location's adjacent rooms
            if (rooms.get(player.getCurrentLocation()).getAdjacentRooms().contains(target)) {
                Room room = (Room) target;
                player.setCurrentLocation(room.getName());
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

    //TODO: Provide ability to look at Characters
    private boolean lookCommand(Entity target){
        if(target instanceof Room) {
            if (lookRoom((Room)target)){
                return true;
            };
        }
        else if (target instanceof Item){
            if (lookItem((Item)target)){
                return true;
            }
        }
        consoleView.setErrorMessage(String.format("%s is not a valid thing for you to look at" +
                ".", target.getName()));
        return false;
    }

    //TODO: Display characters in the room that are available to speak with
    private boolean lookRoom(Room room){
        //If the room they are looking at is the currentLocation
        if(room == rooms.get(player.getCurrentLocation())){
            //print the room description
            consoleView.add(new ConsoleText(room.getDescription()));
            if(!room.getInventory().getItems().isEmpty()) {
                //print items in room if there are any
                consoleView.add(new ConsoleText("Items you see: " + room.getInventory()));
            }
            //print adjacent rooms
            consoleView.add(new ConsoleText("Rooms you can go to: " + room.getJsonAdjacentRooms()));
            consoleView.add(new ConsoleText("#################################################", AnsiTextColor.BLUE));
            return true;
        }
        return false;
    }

    private boolean lookItem(Item item){

        //if the item is in your inventory or in the inventory of the room are you currently in
        if (player.getInventory().getItems().contains(item) || rooms.get(player.getCurrentLocation()).getInventory().getItems().contains(item)) {
            consoleView.add(new ConsoleText(item.getDescription()));
            if(!item.getInventory().getItems().isEmpty()) {
                consoleView.add(new ConsoleText(String.format("The %s contains: %s",item.getName(),item.getInventory())));
            }
            consoleView.add(new ConsoleText("#################################################", AnsiTextColor.BLUE));
            return true;
        }
        return false;
    }

    private List<ConsoleText> helpCommand(){
        List<ConsoleText> result = new ArrayList<>();
        result.add(new ConsoleText("Available commands:"));
        for (var command : commandList){
            result.add(new ConsoleText(String.format("%s: \t%s", command.getKeyWord(), command.getDescription())));
        }
        result.add(new ConsoleText("#################################################", AnsiTextColor.BLUE));
        return result;
    }

    private boolean dropCommand(Entity target){

        //if target is instance of Item
        if (target instanceof Item){
            //if target Item is in your inventory
            if (player.getInventory().getItems().contains(target)){
                //add item to current location inventory
                rooms.get(player.getCurrentLocation()).getInventory().add((Item)target);
                //remove item from player inventory
                player.getInventory().getItems().remove((Item)target);
                //Tell the player what happened
                consoleView.add(new ConsoleText(String.format("You dropped the %s",target.getName())));
                consoleView.add(new ConsoleText("#################################################", AnsiTextColor.BLUE));
                return true;
            }
            //that item is not in your inventory, so you can't drop it
            consoleView.setErrorMessage(String.format("You can only drop Items that are in your inventory."));
            return false;
        }
        consoleView.setErrorMessage(String.format("You can only drop Items."));
        return false;
    }

    private boolean talkCommand(Entity target) {
        //If the target is a character
        if(target instanceof Character){
            //If the target is in the same room as the player
            if (((Character) target).getCurrentRoom() == rooms.get(player.getCurrentLocation())){
                consoleView.add(new ConsoleText(String.format("%s says:",target.getName())));
                consoleView.add(new ConsoleText("Oh woe is me, for I am merely a test character, and soon I will be deleted", AnsiTextColor.PURPLE));
                consoleView.add(new ConsoleText("#################################################", AnsiTextColor.BLUE));
                return true;
            }
            consoleView.setErrorMessage(String.format("You can't talk to Characters that are not in the same room as you."));
            return false;
        }
        consoleView.setErrorMessage(String.format("You can only talk to Characters."));
        return false;
    }

    private List<ConsoleText> getViewText(){
        // View text to be passed to our view
        List<ConsoleText> result = new ArrayList<>();
        result.add(new ConsoleText("#################################################", AnsiTextColor.BLUE));
        result.add(new ConsoleText(String.format("Player Location: %s", player.getCurrentLocation())));
        result.add(new ConsoleText(String.format("Inventory: %s", player.getInventory())));
        result.add(new ConsoleText("#################################################", AnsiTextColor.BLUE));
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
    }

}
