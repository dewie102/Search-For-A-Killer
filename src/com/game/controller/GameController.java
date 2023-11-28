package com.game.controller;

import com.game.model.Entity;
import com.game.model.Item;
import com.game.model.Player;
import com.game.model.Room;
import com.game.view.AnsiTextColor;
import com.game.view.CommandConsoleView;
import com.game.view.ConsoleText;
import com.google.gson.Gson;

import java.util.*;

public class GameController {
    private CommandConsoleView consoleView;
    private Player player;
    List<Command> commandList = new ArrayList<>();
    private final Map<String, Room> rooms = LoadController.loadRooms();
    private final Map<String, Item> items = LoadController.loadItems();


    public void run(){

        player = new Player(rooms.get("Kitchen").getName()); //Kind of roundabout but you get the idea!

        Item key = new Item("Key", "A golden key");
        Item knife = new Item("Knife", "A very sharp knife");

        Room kitchen = new Room("Kitchen", "This is a normal kitchen");
        Room livingRoom = new Room("Living Room", "This is a normal Living Room");

        Map<String, Entity> entityDictionary = new HashMap<String, Entity>();
        entityDictionary.put(kitchen.getName().toLowerCase(), kitchen);
        entityDictionary.put(livingRoom.getName().toLowerCase(), livingRoom);
        entityDictionary.put(key.getName().toLowerCase(), key);
        entityDictionary.put(knife.getName().toLowerCase(), knife);

        commandList.add(new Command("go", List.of("run", "move", "walk"), "Go to a room. e.g. go kitchen", false));
        commandList.add(new Command("look", List.of("run", "move", "walk"), "Look at an object. e.g. look knife", false));
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
                "the", "a", "of", "an"
        );

        List<ConsoleText> text = getViewText();
        String escapeCommand = "quit";
        consoleView = new CommandConsoleView(text, commands, standaloneCommands, entities, ignoreList, escapeCommand);
        while (true){
            String userInput = consoleView.show();
            String[] parts = userInput.split(" ", 2);
            if(parts[0].equals(escapeCommand))
                return;
            if(parts[0].equals("help")) {
                text.addAll(helpCommand());
                continue;
            }
            Entity entity = entityDictionary.get(parts[1]);
            boolean result = false;
            switch (parts[0]){
                case "go":
                    result = goCommand(entity);
                    break;
                case "look":
                    result = lookCommand(entity);
                    break;
            }
            if(result){
                consoleView.clearErrorMessage();
            }
        }
    }

    private boolean goCommand(Entity target){
        if(target instanceof Room){
            // IMPLEMENT GO TO ROOM
            return true;
        }
        consoleView.setErrorMessage(String.format("%s is not a room, you can't go there.", target.getName()));
        return false;
    }

    private boolean lookCommand(Entity target){
        if(target instanceof Item){
            // IMPLEMENT GO TO ROOM
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
        new ConsoleText("#################################################", AnsiTextColor.BLUE);
        return result;
    }

    private List<ConsoleText> getViewText(){
        // View text to be passed to our view
        List<ConsoleText> result = new ArrayList<>();
        result.add(new ConsoleText("#################################################", AnsiTextColor.BLUE));
        result.add(new ConsoleText(String.format("Player Location: %s", player.getCurrentLocation())));
        result.add(new ConsoleText(String.format("Inventory: %s", player.getInventory().toString())));
        result.add(new ConsoleText("#################################################", AnsiTextColor.BLUE));
        return result;
    }

}
