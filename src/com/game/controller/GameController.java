package com.game.controller;

import com.game.controller.commands.CommandType;
import com.game.controller.controllers.ConversationController;
import com.game.controller.controllers.QuitGameController;
import com.game.controller.io.JsonMessageParser;
import com.game.model.*;
import com.game.view.View;
import com.game.view.framework.InvalidInputException;
import com.game.view.gui.CommandDisplayView;
import com.game.view.gui.DisplayView;
import com.game.view.gui.GameWindow;
import com.game.view.terminal.AnsiTextColor;
import com.game.view.terminal.CommandConsoleView;
import com.game.view.terminal.ConsoleText;
import com.game.model.Character;

import java.util.*;

public class GameController {
    // This is used to select the storyline to play
    public int STORYLINE = 0;
    
    private View commandView;
    
    // DisplayViews available to the command and other functions
    private DisplayView mainView;
    private DisplayView roomView;
    private DisplayView playerView;
    private final List<ConsoleText> roomText = new ArrayList<>();
    private final List<ConsoleText> playerText = new ArrayList<>();
    private boolean titleScreenShown = true;
    
    public final Player player = LoadController.getPlayer();
    public Character character = null;
    private final Map<String, Room> rooms = LoadController.getRooms();
    private final Map<String, Item> items = LoadController.getItems();
    private final Map<String, Character> characters = LoadController.getCharacters();
    // gameText is an object that has multiple Lists/Maps [general, error, info]
    //      that contain text used in the game
    private static final JsonMessageParser gameText = new JsonMessageParser();
    private List<ConsoleText> mainText = new ArrayList<>();
    private final List<ConsoleText> secondaryText = new ArrayList<>();
    private final Map<String, Command> commandMap = new TreeMap<>();
    private final MapLoaderController mapLoaderController = new MapLoaderController();
    public final ConversationController conversationController = new ConversationController(mainText, this::checkForWinningConditions);
    Character reportedMurder = null;
    Item reportedMurderWeapon = null;
    private final Map<String, Entity> entityDictionary = new HashMap<>();
    private List<String> entities;
    private List<String> ignoreList;


    private static GameController instance;
    
    public static GameController getInstance() {
        if(instance == null) {
            instance = new GameController();
        }
        
        return instance;
    }
    
    private GameController() {
        setDetectiveEndGameConversation();
    }
    
    public void initialize() {
        LoadController.loadAllEntities();
        
        // load the game map from json
        mapLoaderController.loadMap();
    
        //Map<String, Entity> entityDictionary = new HashMap<>();
        entityDictionary.putAll(rooms);
        entityDictionary.putAll(items);
        entityDictionary.putAll(characters);
        commandMap.put("go", new Command("go", List.of("run", "move", "walk", "travel"), "Go to a room. e.g. go kitchen", CommandType.TWO_PARTS, this::goCommand));
        commandMap.put("look", new Command("look", List.of("see", "inspect", "read", ""), "Look at an object or room. e.g. look knife", CommandType.HYBRID, this::lookCommand));
        commandMap.put("drop", new Command("drop", List.of("place", "put"), "Drop an object from your inventory into your current location", CommandType.TWO_PARTS, this::dropCommand));
        commandMap.put("get", new Command("get", List.of("grab", "pickup", "take"), "Get an object from your current location put into your inventory", CommandType.TWO_PARTS, this::getCommand));
        commandMap.put("talk", new Command("talk", List.of("chat", "speak"), "Talk to another character", CommandType.TWO_PARTS, this::talkCommand));
        commandMap.put("volume", new Command("volume", List.of("sound", "vol"), "Change the volume settings", CommandType.STANDALONE, this::volCommand));
        if(!MainController.PLAY_IN_GUI) {
            commandMap.put("quit", new Command("quit", List.of("exit"), "Quits the game, no questions asked.", CommandType.STANDALONE, this::quitCommand));
            commandMap.put("help", new Command("help", List.of(), "It displays this menu.", CommandType.STANDALONE, this::helpCommand));
        }
    
        // List of entities
        entities = new ArrayList<>(entityDictionary.keySet());
    
        // List of words to ignore
        ignoreList = gameText.getIgnoreList();
        
        if(!MainController.PLAY_IN_GUI) {
            commandView = new CommandConsoleView(List.of(mainText, secondaryText), new ArrayList<>(commandMap.values()), entities, ignoreList);
        } else {
            commandView = new CommandDisplayView(null, null, new ArrayList<>(commandMap.values()), entities, ignoreList);
        }
    
        STORYLINE = LoadController.selectRandomStory();
    }
    
    public void initializeGUIComponents() {
        // Different displayViews for different GUI areas
        mainView = new DisplayView(List.of(mainText, secondaryText), GameWindow.gameTextArea);
        roomView = new DisplayView(List.of(roomText), GameWindow.roomInformationArea);
        playerView = new DisplayView(List.of(playerText), GameWindow.playerInformationArea);
        mapLoaderController.buildUIMap();
        
        addPlayerAdjacentRoomToHistory();
    }

    // This is only called for terminal run
    public GameResult run() {
        mainText = getViewText();
        GameResult gameResult = GameResult.UNDEFINED;
        while (gameResult == GameResult.UNDEFINED){
            commandView.show();
            String userInput = commandView.collectInput();
            String[] parts = userInput.split(" ", 2);
            boolean result = false;

            Entity entity = parts.length > 1 ? entityDictionary.get(parts[1]) : null;

            if ((commandMap.get(parts[0]).getCommandType() == CommandType.TWO_PARTS)) {
                if (entity == null) {
                    commandView.setErrorMessage(gameText.getErrorMessages().get("invalidAction"));
                    continue;
                }
            }

            result = commandMap.get(parts[0]).executeCommand(entity);
            mainText.clear();
            mainText.addAll(getViewText());

            if(result){
                commandView.clearErrorMessage();
            }
            gameResult = checkForWinningConditions();
        }
        player.getPlayerHistory().clear();
        mapLoaderController.buildMap(player.getCurrentLocation(), player.getPlayerHistory());
        mapLoaderController.displayMap();
        return gameResult;
    }
    
    // The idea here is whenever a command is entered in the GUI it runs this function
    public void runCommand(String command) {
        if(titleScreenShown) {
            titleScreenShown = false;
            mainView.clearText();
        }
        
        try {
            command = ((CommandDisplayView) commandView).validateInput(command);
        } catch(InvalidInputException e) {
            mainView.clearText();
            mainView.setErrorMessage("\n\n" + e.getMessage());
            mainView.show();
            return;
        }
        
        String[] parts = command.split(" ", 2);
        boolean result = false;

        Entity entity = parts.length > 1 ? entityDictionary.get(parts[1]) : null;

        if ((commandMap.get(parts[0]).getCommandType() == CommandType.TWO_PARTS)) {
            if (entity == null) {
                mainView.setErrorMessage(gameText.getErrorMessages().get("invalidAction"));
            }
        }

        result = commandMap.get(parts[0]).executeCommand(entity);
        // Clear each text array and fill it with the right information
        mainText.clear();
        mainText.add(new ConsoleText(rooms.get(player.getCurrentLocation()).getDescription()));
    
        roomText.clear();
        roomText.addAll(getCurrentRoomInformation());
    
        playerText.clear();
        playerText.addAll(getPlayerInformation());

        if(result){
            mainView.clearErrorMessage();
        } else {
            mainView.setErrorMessage(commandView.getErrorMessage());
        }
        
        // Clear the component and display the text
        mainView.clearText();
        mainView.show();
        
        roomView.clearText();
        roomView.show();
        
        playerView.clearText();
        playerView.show();
        
        mapLoaderController.updateMap(player.getCurrentLocation(), player.getPlayerHistory());
        // This handles the map displaying and building
        //mapLoaderController.buildMap(player.getCurrentLocation(), player.getPlayerHistory());
        //mapLoaderController.displayMap(GameWindow.mapArea);
    }

    private boolean goCommand(Entity target) {
        secondaryText.add(new ConsoleText(""));
        if(target instanceof Room){
            //if the room they are trying to go to is in current location's adjacent rooms
            if (rooms.get(player.getCurrentLocation()).getAdjacentRooms().contains(target)) {
                //mainText.clear();
                AudioController.playSFX(2);
                secondaryText.clear();
                Room room = (Room) target;
                player.setCurrentLocation(room.getName());
                // add the new room to the player's location history for map rendering
                if(!player.getPlayerHistory().contains(room.getName())) {
                    player.addToPlayerHistory(room.getName());
                }
    
                if(MainController.PLAY_IN_GUI) {
                    addPlayerAdjacentRoomToHistory();
                }
                //mainText.addAll(getViewText());
                return true;
            }
            //if they are trying to go to a room, but not an adjacent one
            commandView.setErrorMessage(String.format(gameText.getErrorMessages().get("invalidRoomTravers"), target.getName()));
            return false;
        }
        //if they are not trying to go to a valid room
        commandView.setErrorMessage(String.format(gameText.getErrorMessages().get("invalidRoomName"), target != null ? target.getName() : gameText.getErrorMessages().get("invalidDefaultThat")));
        return false;
    }

    private boolean lookCommand(Entity target) {
        if(target == null && !MainController.PLAY_IN_GUI){
            if(lookRoom(rooms.get(player.getCurrentLocation())))
                return true;
        }
        if(target instanceof Room && !MainController.PLAY_IN_GUI) {
            //This clause is necessary to allow correct error message to print
            return lookRoom((Room) target);
        }
        else if (target instanceof Item){
            return lookItem((Item) target);
        }
        else if (target instanceof Character){
            return lookCharacter((Character) target);
        } else if(MainController.PLAY_IN_GUI) {
            return true;
        }
        commandView.setErrorMessage(String.format(gameText.getErrorMessages().get("invalidLook"), target != null ? target.getName() : gameText.getErrorMessages().get("invalidDefaultThat")));
        return false;
    }

    private boolean lookRoom(Room room) {
        secondaryText.clear();
        secondaryText.add(new ConsoleText(""));
        //If the room they are looking at is the currentLocation
        if(room == rooms.get(player.getCurrentLocation())){
            //print the room description
            secondaryText.add(new ConsoleText(room.getDescription()));
            if(!room.getInventory().getItems().isEmpty()) {
                //print items in room if there are any
                secondaryText.add(new ConsoleText(gameText.getInfoMessages().get("visibleItems") + room.getInventory()));
            }
            if(!(room.getCharactersInRoom()==null) && !room.getCharactersInRoom().isEmpty()){
                secondaryText.add(new ConsoleText(gameText.getInfoMessages().get("personVisible"), room.getCharactersInRoomToString()));
            }
            //print adjacent rooms
            secondaryText.add(new ConsoleText(gameText.getInfoMessages().get("traversableRooms"), room.getJsonAdjacentRooms()));
            if(!MainController.PLAY_IN_GUI) {
                secondaryText.add(new ConsoleText(gameText.getGeneralMessages().get("divider"), AnsiTextColor.BLUE));
            }
            return true;
        }
        return false;
    }

    private boolean lookItem(Item item) {
        secondaryText.clear();
        secondaryText.add(new ConsoleText(""));
        //if the item is in your inventory
        // or in the inventory of the room are you currently in
        if ((player.getInventory().getItems().contains(item) || rooms.get(player.getCurrentLocation()).getInventory().getItems().contains(item)) && !item.isHidden()) {
            secondaryText.add(new ConsoleText(item.getDescription()));
            
            if(!"".equals(item.getJsonItemInside())) {
                Item hiddenItem = items.get(item.getJsonItemInside());
                if(hiddenItem != null && hiddenItem.isHidden()) {
                    hiddenItem.setHidden(false);
                    secondaryText.add(new ConsoleText(String.format("\nInside %s you found %s", item, hiddenItem)));
                }
            }
            
            if(!item.getInventory().getItems().isEmpty()) {
                secondaryText.add(new ConsoleText(String.format(gameText.getInfoMessages().get("observeItem"),item.getName(),item.getInventory())));
            }
            if(!MainController.PLAY_IN_GUI) {
                secondaryText.add(new ConsoleText(gameText.getGeneralMessages().get("divider"), AnsiTextColor.BLUE));
            }
            return true;
        }
        return false;
    }
    
    private boolean lookCharacter(Character character) {
        if(player.getCurrentLocation().equals(character.getCurrentLocation())){
            secondaryText.add(new ConsoleText(character.getDescription()));
            if(!MainController.PLAY_IN_GUI) {
                secondaryText.add(new ConsoleText(gameText.getGeneralMessages().get("divider"), AnsiTextColor.BLUE));
            }
            return true;
        }
        return false;
    }

    private boolean helpCommand(Entity target) {
        secondaryText.clear();
        secondaryText.add(new ConsoleText(gameText.getInfoMessages().get("availableCommands")));
        for (var command : commandMap.values()) {
            secondaryText.add(new ConsoleText(String.format("%s: \t%s", command.getKeyWord(), command.getDescription())));
        }
        secondaryText.add(new ConsoleText(gameText.getGeneralMessages().get("divider"), AnsiTextColor.BLUE));
        return true;
    }
    
    public void displayHelpMessage() {
        GameWindow.helpTextArea.setText("");
        GameWindow.helpTextArea.append(gameText.getInfoMessages().get("availableCommands") + "\n\n");
        for (var command : commandMap.values()) {
            GameWindow.helpTextArea.append(String.format("%s: \t%s\n", command.getKeyWord(), command.getDescription()));
        }
        GameWindow.helpTextArea.append("\n");
        GameWindow.helpTextArea.append("-> Click on the rooms on the map to traverse the level");
    }

    private boolean dropCommand(Entity target) {
        secondaryText.clear();
        secondaryText.add(new ConsoleText(""));
        //if target is instance of Item
        if (target instanceof Item){
            //if target Item is in your inventory
            if (player.getInventory().getItems().contains(target)){
                AudioController.playSFX(4);
                //add item to current location inventory
                rooms.get(player.getCurrentLocation()).getInventory().add((Item)target);
                //remove item from player inventory
                player.getInventory().getItems().remove((Item)target);
                //Tell the player what happened
                secondaryText.add(new ConsoleText(String.format(gameText.getInfoMessages().get("dropItem"),target.getName())));
                if(!MainController.PLAY_IN_GUI) {
                    secondaryText.add(new ConsoleText(gameText.getGeneralMessages().get("divider"), AnsiTextColor.BLUE));
                }
                return true;
            }
            //that item is not in your inventory, so you can't drop it
            commandView.setErrorMessage(gameText.getErrorMessages().get("invalidInventoryDropItem"));
            return false;
        }
        commandView.setErrorMessage(gameText.getErrorMessages().get("invalidTypeDropItem"));
        return false;
    }

    private boolean talkCommand(Entity target) {
        //If the target is a character
        if(target instanceof Character){
            Character character = (Character) target;
            //If the target is in the same room as the player
            if (((Character) target).getCurrentLocation().equals(player.getCurrentLocation())){
                this.character = character;
                conversationController.run(player, character);
                return true;
            }
            commandView.setErrorMessage(String.format(gameText.getErrorMessages().get("invalidCharacterPresence")));
            return false;
        }
        commandView.setErrorMessage(String.format(gameText.getErrorMessages().get("invalidCharacterType")));
        return false;
    }

    private boolean getCommand(Entity target) {
        secondaryText.clear();
        secondaryText.add(new ConsoleText(""));
        if(target instanceof Item){
            Room currentRoom = rooms.get(player.getCurrentLocation());
            Item item = (Item)target;
            if(!item.isPickUpable())
            {
                commandView.setErrorMessage(String.format(gameText.getErrorMessages().get("invalidItemPickup"), item.getName()));
                return false;
            }
            if(currentRoom.getInventory().contains(item) && !item.isHidden()){
                AudioController.playSFX(3);
                player.getInventory().add(item);
                currentRoom.getInventory().remove(item);
                mainText.clear();
                mainText.addAll(getViewText());
                secondaryText.add(new ConsoleText(String.format(gameText.getInfoMessages().get("itemPickup"),target.getName())));
                if(!MainController.PLAY_IN_GUI) {
                    secondaryText.add(new ConsoleText(gameText.getGeneralMessages().get("divider"), AnsiTextColor.BLUE));
                }
                return true;
            }
            commandView.setErrorMessage(gameText.getErrorMessages().get("invalidItemNotPresent"));
        }
        commandView.setErrorMessage(gameText.getErrorMessages().get("invalidNotAnItem"));
        return false;
    }

    private boolean quitCommand(Entity target) {
        QuitGameController quitGameController = new QuitGameController(mainText, secondaryText);
        if(quitGameController.run()){
            System.exit(0);
        }
        return true;
    }

    private boolean volCommand(Entity target) {
        boolean success;
        if (!MainController.PLAY_IN_GUI) {
            success = AudioController.volMenu();
        } else {
            success = AudioController.volControl();
        }
        if(success){
            return true;
        }
        commandView.setErrorMessage("The action could not be taken.");
        return false;

    }

    // Extracted the player and room information out, allowing the terminal to work as expected but GUI can call
    // the separate methods
    private List<ConsoleText> getViewText() {

        // View text to be passed to our view
        List<ConsoleText> result = new ArrayList<>();

        // build the map based on the player location history and current location
        mapLoaderController.buildMap(player.getCurrentLocation(), player.getPlayerHistory());
        // display the updated map that was built using the buildMap method
        mapLoaderController.displayMap();

        result.add(new ConsoleText(gameText.getGeneralMessages().get("divider"), AnsiTextColor.BLUE));
        result.addAll(getPlayerInformation());
        result.add(new ConsoleText(gameText.getGeneralMessages().get("divider"), AnsiTextColor.BLUE));
        result.addAll(getConnectedRoomInformation());
        result.add(new ConsoleText(gameText.getGeneralMessages().get("divider"), AnsiTextColor.BLUE));
        return result;
    }
    
    private List<ConsoleText> getPlayerInformation() {
        List<ConsoleText> result = new ArrayList<>();
        result.add(new ConsoleText(String.format(gameText.getInfoMessages().get("playerLocation"), player.getCurrentLocation())));
        result.add(new ConsoleText(String.format(gameText.getInfoMessages().get("playerInventory"), player.getInventory())));
        
        return result;
    }
    
    private List<ConsoleText> getConnectedRoomInformation() {
        List<ConsoleText> result = new ArrayList<>();
        result.add(new ConsoleText(String.format(gameText.getInfoMessages().get("connectedRooms"), rooms.get(player.getCurrentLocation()).adjacentRoomToString())));
    
        return result;
    }
    
    private List<ConsoleText> getCurrentRoomInformation() {
        List<ConsoleText> result = new ArrayList<>();
        
        Room room = rooms.get(player.getCurrentLocation());
        
        if (!room.getInventory().getItems().isEmpty()) {
            //print items in room if there are any and if they are visible
            Inventory visibleItems = new Inventory();
    
            for(Item item : room.getInventory().getItems()) {
                if(!item.isHidden()) {
                    visibleItems.add(item);
                }
            }
    
            result.add(new ConsoleText(gameText.getInfoMessages().get("visibleItems") + visibleItems));
        }
        if (!(room.getCharactersInRoom() == null) && !room.getCharactersInRoom().isEmpty()) {
            result.add(new ConsoleText(gameText.getInfoMessages().get("personVisible"), room.getCharactersInRoomToString()));
        }
        //print adjacent rooms
        result.add(new ConsoleText(gameText.getInfoMessages().get("traversableRooms"), room.getJsonAdjacentRooms()));
        
        return result;
    }
    
    private void addPlayerAdjacentRoomToHistory() {
        for(Room adjacentRoom : rooms.get(player.getCurrentLocation()).getAdjacentRooms()) {
            player.addToPlayerHistory(adjacentRoom.getName());
        }
    }

    private boolean reportCommand(Entity target){
        if(target instanceof Item){
            reportedMurderWeapon = (Item)target;
        }
        if(target instanceof Character){
            reportedMurder = (Character)target;
        }
        return true;
    }

    GameResult checkForWinningConditions(){
        if(reportedMurder == null || reportedMurderWeapon == null)
            return GameResult.UNDEFINED;
        else{
            return (Objects.equals(reportedMurder.getName(), LoadController.getMurderer().getName()) && Objects.equals(reportedMurderWeapon.getName(), LoadController.getMurderWeapon().getName()))
                    ? GameResult.WIN : GameResult.LOSS;
        }
    }

    private void setDetectiveEndGameConversation() {
        Dialog murdererDialog = new Dialog("I know who the murderer is", "Perfect, who do you think committed the murder?");
        Dialog murdererWeaponDialog = new Dialog("I know which one was the murder weapon", "Perfect, which was the murder weapon?");

        Character detective = LoadController.getDetective();

        Conversation murdererConversation = new Conversation();
        for (var suspect : LoadController.getSuspects().values()) {
            Dialog dialog = new Dialog(suspect.getName(), "Noted, you think the murderer was " + suspect.getName());
            dialog.setReport(suspect);
            dialog.setCallBack(this::reportCommand);
            dialog.setEndsConversation(true);
            murdererConversation.addDialog(dialog);
        }
        murdererConversation.addDialog(new Dialog("On the other hand.", ""));
        murdererDialog.setFollowUpConversation(murdererConversation);

        Conversation murdererWeaponConversation = new Conversation();
        for (var weapon : LoadController.getWeapons().values()) {
            Dialog dialog = new Dialog(weapon.getName(), "Noted, you think the murder weapon was " + weapon.getName());
            dialog.setReport(weapon);
            dialog.setCallBack(this::reportCommand);
            dialog.setEndsConversation(true);
            murdererWeaponConversation.addDialog(dialog);
        }
        murdererWeaponConversation.addDialog(new Dialog("On the other hand.", ""));
        murdererWeaponDialog.setFollowUpConversation(murdererWeaponConversation);

        detective.getConversation().insertDialog(murdererDialog);
        detective.getConversation().insertDialog(murdererWeaponDialog);
    }
    
    public static void clearInstance() {
        instance = null;
    }
}
