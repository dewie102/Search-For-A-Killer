package com.game.controller;

import com.game.model.Entity;
import com.game.model.Item;
import com.game.model.Player;
import com.game.model.Room;
import com.game.view.AnsiTextColor;
import com.game.view.CommandConsoleView;
import com.game.view.ConsoleText;

import java.util.*;

public class GameController {
    private CommandConsoleView consoleView;
    private Player player;

    public void run(){

        player = new Player("Kitchen");

        Item key = new Item("Key", "A golden key");
        Item knife = new Item("Knife", "A very sharp knife");

        Room kitchen = new Room("Kitchen", "This is a normal kitchen");
        Room livingRoom = new Room("Living Room", "This is a normal Living Room");

        Map<String, Entity> entityDictionary = new HashMap<String, Entity>();
        entityDictionary.put(kitchen.getName().toLowerCase(), kitchen);
        entityDictionary.put(livingRoom.getName().toLowerCase(), livingRoom);
        entityDictionary.put(key.getName().toLowerCase(), key);
        entityDictionary.put(knife.getName().toLowerCase(), knife);

        // TODO: For Robert, here you can put all the Inventory and Location information
        // View text to be passed to our view
        List<ConsoleText> text = List.of(
                new ConsoleText("#################################################", AnsiTextColor.BLUE),
                new ConsoleText(String.format("Player Location: %s", player.getCurrentLocation())),
                new ConsoleText(String.format("Inventory: %s", player.getInventory().toString())),
                new ConsoleText("#################################################", AnsiTextColor.BLUE)
                );

        // Map of Commands
        Map<String, List<String>> commands = Map.of(
                "go", List.of("run", "move", "walk"),
                "look", List.of("see", "inspect"),
                "exit", List.of()
        );

        // List of entities
        List<String> entities = new ArrayList<>(entityDictionary.keySet());


        // List of entities
        List<String> ignoreList = List.of(
                "the", "a", "of", "an"
        );

        String escapeCommand = "quit";
        consoleView = new CommandConsoleView(text, commands, entities, ignoreList, escapeCommand);
        while (true){
            String userInput = consoleView.show();
            String[] parts = userInput.split(" ", 2);
            if(parts[0].equals(escapeCommand))
                return;
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
}
