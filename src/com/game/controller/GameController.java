package com.game.controller;

import com.game.model.*;
import com.game.view.AnsiTextColor;
import com.game.view.CommandConsoleView;
import com.game.view.ConsoleText;

import java.util.*;

public class GameController {
    private CommandConsoleView consoleView;
    private Player player;
    List<Command> commandList = new ArrayList<>();
    private final Map<String, Room> rooms = LoadController.loadRooms();
    private final Map<String, Item> items = LoadController.loadItems();

    public GameController(){
        fixHasAs();
    }

    public void run(){

        player = new Player(rooms.get("Kitchen").getName()); //Kind of roundabout but you get the idea!

        Map<String, Entity> entityDictionary = new HashMap<>();
        entityDictionary.putAll(rooms);
        entityDictionary.putAll(items);

        player.getInventory().add(items.get("Pen"));
        player.getInventory().add(items.get("Glove"));
        player.getInventory().add(items.get("TV"));



        commandList.add(new Command("go", List.of("run", "move", "walk"), "Go to a room. e.g. go kitchen", false));
        commandList.add(new Command("look", List.of("see", "inspect"), "Look at an object. e.g. look knife", false));
        commandList.add(new Command("quit", List.of(), "Quits the game, no questions asked.", true));
        commandList.add(new Command("help", List.of(), "It displays this menu.", true));

        // Map of Commands that require a target Entity, for these to be valid they must have two parts, the command itself
        // and a target. e.g. <go there>, <get that>
        Map<String, List<String>> commands = Map.of(
                "go", List.of("run", "move", "walk"),
                "look", List.of("see", "inspect")
        );

        // Standalone commands, these commands don't require a target. e.g. <quit>, <help>
        List<String> standaloneCommands = List.of(
                "quit", "help"
        );

        // List of entities
        List<String> entities = new ArrayList<>(entityDictionary.keySet());


        // List of entities
        List<String> ignoreList = List.of(
                "the", "at", "an", "a", "of"
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
            Room room = (Room)target;
            player.setCurrentLocation(room.getName());
            return true;
        }
        consoleView.setErrorMessage(String.format("%s is not a room, you can't go there.", target.getName()));
        return false;
    }

    private boolean lookCommand(Entity target){
        if(target instanceof Item){
            consoleView.add(new ConsoleText(target.getDescription()));
            consoleView.add(new ConsoleText("#################################################", AnsiTextColor.BLUE));
            return true;
        }
        consoleView.setErrorMessage(String.format("%s is not an item, you can't inspect that.", target.getName()));
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

    private List<ConsoleText> getViewText(){
        // View text to be passed to our view
        List<ConsoleText> result = new ArrayList<>();
        result.add(new ConsoleText("#################################################", AnsiTextColor.BLUE));
        result.add(new ConsoleText(String.format("Player Location: %s", player.getCurrentLocation())));
        result.add(new ConsoleText(String.format("Inventory: %s", player.getInventoryString())));
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
