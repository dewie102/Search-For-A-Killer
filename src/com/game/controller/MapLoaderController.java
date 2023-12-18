package com.game.controller;

import com.game.view.gui.DisplayView;
import com.game.view.gui.GameWindow;
import com.game.view.terminal.AnsiTextColor;
import com.game.view.terminal.ConsoleText;
import com.game.view.terminal.ConsoleView;
import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.io.FileReader;
import java.util.*;

/**
 * Creates a class object that loads in the game map from the game map json file.
 * When the build map method is called the object Strings are built and passed to the console.
 */

class MapLoaderController {

    // INSTANCE VARIABLES
    private Map<String, Map<String, Map<String, Map<String, String>>>> gameMap = new LinkedHashMap<>();

    // StringBuilders that are used to create the map UI layout
    // each map layout is made up of a List<StringBuilder> with 7 StringBuilders
    // the layout goes from 1 on top to 3 on the bottom
    private List<StringBuilder> mapLayout1 = new ArrayList<>();
    private List<StringBuilder> mapLayout2 = new ArrayList<>();
    private List<StringBuilder> mapLayout3 = new ArrayList<>();
    
    private final Map<String, ImageIcon> roomIcons = new HashMap<>();
    
    private final int rows = 3;
    private int columns = 0;

    // METHODS
    public void loadMap(){

        // map json file path
        String mapPath = "data/Map.json";

        // try to read the map json file and populate the gameMap variable with each location and map layout
        try (FileReader reader = new FileReader(mapPath)) {
            // create the gson object
            Gson gson = new Gson();
            // read the json file and convert it to a list of maps of maps of strings
            List<Map<String, Map<String, Map<String, Map<String, String>>>>> mapList = gson.fromJson(reader, List.class);
            // iterate through the list of maps of maps of strings and add each map to the gameMap variable
            mapList.forEach(map -> getGameMap().putAll(map));
        } catch (Exception e) {
            System.out.printf("Error loading the game map: %s", e.getMessage());
        }
        
        // Get the number of columns from the recently loaded map, used for later index calculations
        columns = gameMap.get("1").size();
    }

    // build the map layout
    public void buildMap(String playerLocation, List<String> playerHistory){

        // clear the map StringBuilders
        getMapLayout1().clear();
        getMapLayout2().clear();
        getMapLayout3().clear();


        // create 7 empty StringBuilders for each map layout and add them to
        //      the mapLayout1, mapLayout2, and mapLayout3 variables
        for(int i = 0; i < 7; i++){
            getMapLayout1().add(new StringBuilder());
            getMapLayout2().add(new StringBuilder());
            getMapLayout3().add(new StringBuilder());
        }

        // iterate through the gameMap variable for each layer of the map (total of 3)
        for(String layerKey : getGameMap().keySet()){
            // iterate through the gameMap for each level of the map (3 total levels)
            for(String levelKey : getGameMap().get(layerKey).keySet()){
                // int variable that keeps track of the current line of the map layout
                // it's used to print each line in each layer
                int countLine = 0;
                // iterate through the gameMap for each room and display them in order based on
                //      the layer and level of the map
                for(String roomKey : getGameMap().get(layerKey).get(levelKey).keySet()) {
                    // if the layer is 1 and the room is the player's current location or the room is blank
                    if (layerKey.equals("1") && (playerHistory.contains(roomKey) || roomKey.equals("blank"))) {
                        // iterate through the gameMap for each line of the room and print them in order
                        for(String line : getGameMap().get(layerKey).get(levelKey).get(roomKey).values()){
                            // player location is equal to the room key print it out in green to indicate
                            //      that the player is in this room
                            if(playerLocation.equals(roomKey)){
                                if(!MainController.PLAY_IN_GUI) {
                                    // turn on the green font and turn it off with RESET
                                    getMapLayout1().get(countLine).append(AnsiTextColor.GREEN.getColor());
                                    getMapLayout1().get(countLine).append(line);
                                    getMapLayout1().get(countLine).append(AnsiTextColor.RESET.getColor());
                                } else {
                                    if(countLine != 5) {
                                        getMapLayout1().get(countLine).append(line);
                                    } else {
                                        getMapLayout1().get(countLine).append("| (Current Location)|");
                                    }
                                }
                            } else {
                                // if the player is not in the room print it out in standard color (white)
                                getMapLayout1().get(countLine).append(line);
                            }
                            // increment the line count to keep track of which lines are being rendered
                            countLine ++;
                        }
                        // if the layer is 2 and the room is the player's current location or the room is blank
                    } else if (layerKey.equals("2") && (playerHistory.contains(roomKey) || roomKey.equals("blank"))) {
                        // iterate through the gameMap for each line of the room and print them in order
                        for(String line : getGameMap().get(layerKey).get(levelKey).get(roomKey).values()){
                            // if the player location is equal to the room key print it out in green to indicate
                            //      that the player is in this room
                            if(playerLocation.equals(roomKey)){
                                if(!MainController.PLAY_IN_GUI) {
                                    // turn on the green font and turn it off with RESET
                                    getMapLayout2().get(countLine).append(AnsiTextColor.GREEN.getColor());
                                    getMapLayout2().get(countLine).append(line);
                                    getMapLayout2().get(countLine).append(AnsiTextColor.RESET.getColor());
                                } else {
                                    if(countLine != 5) {
                                        getMapLayout2().get(countLine).append(line);
                                    } else {
                                        getMapLayout2().get(countLine).append("| (Current Location)|");
                                    }
                                }
                            } else {
                                // if the player is not in the room print it out in standard color (white)
                                getMapLayout2().get(countLine).append(line);
                            }
                            // increment the line count to keep track of which lines are being rendered
                            countLine ++;
                        }
                        // if the layer is 3 and the room is the player's current location or the room is blank
                    } else if (layerKey.equals("3") && (playerHistory.contains(roomKey) || roomKey.equals("blank"))) {
                        // iterate through the gameMap for each line of the room and print them in order
                        for(String line : getGameMap().get(layerKey).get(levelKey).get(roomKey).values()){
                            // if the player location is equal to the room key print it out in green to indicate
                            //      that the player is in this room
                            if(playerLocation.equals(roomKey)){
                                if(!MainController.PLAY_IN_GUI) {
                                    // turn on the green font and turn it off with RESET
                                    getMapLayout3().get(countLine).append(AnsiTextColor.GREEN.getColor());
                                    getMapLayout3().get(countLine).append(line);
                                    getMapLayout3().get(countLine).append(AnsiTextColor.RESET.getColor());
                                } else {
                                    if(countLine != 5) {
                                        getMapLayout3().get(countLine).append(line);
                                    } else {
                                        getMapLayout3().get(countLine).append("| (Current Location)|");
                                    }
                                }
                            } else {
                                // if the player is not in the room print it out in standard color (white)
                                getMapLayout3().get(countLine).append(line);
                            }
                            // increment the line count to keep track of which lines are being rendered
                            countLine ++;
                        }
                    }
                }
            }
        }
    }
    
    // Should only need to build the map once, we will enable and disable the buttons as necessary
    public void buildUIMap() {
        loadIcons();
        // iterate through the gameMap variable for each layer of the map (total of 3)
        for(String layerKey : getGameMap().keySet()){
            // iterate through the gameMap for each level of the map (3 total levels)
            for(String levelKey : getGameMap().get(layerKey).keySet()){
                // iterate through the gameMap for each room and display them in order based on
                //      the layer and level of the map
                for(String roomKey : getGameMap().get(layerKey).get(levelKey).keySet()) {
                    setButtonRoomProperties(layerKey, levelKey, roomKey);
                }
            }
        }
    }

    // display the built map layout
    public void displayMap(){
        // create the ConsoleView object
        ConsoleView consoleView = new ConsoleView();

        // add the map layout to the ConsoleView object one at a time for each layer, level, and position
        // the map is 3x3 in size (3 rooms horizontally and 3 rooms vertically) (check out the Map.json file)
        for(StringBuilder line : getMapLayout1()) {
            consoleView.getTextList().add(List.of(new ConsoleText(line.toString())));
        }
        for (StringBuilder line : getMapLayout2()) {
            consoleView.getTextList().add(List.of(new ConsoleText(line.toString())));
        }
        for (StringBuilder line : getMapLayout3()) {
            consoleView.getTextList().add(List.of(new ConsoleText(line.toString())));
        }
        // print the consoleView that contains the Map
        consoleView.showMap();
    }
    
    // display the built map layout
    public void displayMap(JTextComponent textComponent){
        // create the DisplayView object
        DisplayView displayView = new DisplayView();
        displayView.setDisplayComponent(textComponent);
        displayView.clearText();
        
        // add the map layout to the ConsoleView object one at a time for each layer, level, and position
        // the map is 3x3 in size (3 rooms horizontally and 3 rooms vertically) (check out the Map.json file)
        for(StringBuilder line : getMapLayout1()) {
            displayView.getTextList().add(List.of(new ConsoleText(line.toString())));
        }
        for (StringBuilder line : getMapLayout2()) {
            displayView.getTextList().add(List.of(new ConsoleText(line.toString())));
        }
        for (StringBuilder line : getMapLayout3()) {
            displayView.getTextList().add(List.of(new ConsoleText(line.toString())));
        }
        // print the displayView that contains the Map
        displayView.showMap();
    }
    
    public void updateMap(String playerLocation, List<String> playerHistory) {
        // Take in play location to update player current room status indicator (somehow?)
    
        // iterate through the gameMap variable for each layer of the map (total of 3)
        for(String layerKey : getGameMap().keySet()){
            // iterate through the gameMap for each level of the map (3 total levels)
            for(String levelKey : getGameMap().get(layerKey).keySet()) {
                for(String roomName : getGameMap().get(layerKey).get(levelKey).keySet()) {
                    if(playerHistory.contains(roomName)) {
                        enableButton(layerKey, levelKey, roomName, true);
                    }
                }
            }
        }
    }
    
    private void loadIcons() {
        String path = "data/icons";
    
        // iterate through the gameMap variable for each layer of the map (total of 3)
        for(String layerKey : getGameMap().keySet()){
            // iterate through the gameMap for each level of the map (3 total levels)
            for(String levelKey : getGameMap().get(layerKey).keySet()) {
                for(String roomName : getGameMap().get(layerKey).get(levelKey).keySet()) {
                    ImageIcon roomIcon = new ImageIcon(String.format("%s/%s.png", path, roomName));
                    roomIcons.put(roomName, roomIcon);
                }
            }
        }
    }
    
    private void setButtonRoomProperties(String layerKey, String levelKey, String roomName) {
        int row = Integer.parseInt(layerKey) - 1;
        int column = Integer.parseInt(levelKey) - 1;
        
        JButton btn = (JButton)GameWindow.mapButtonPanel.getComponent(columns * row + column);
        
        if("blank".equals(roomName)) {
            btn.setEnabled(false);
        } else {
            btn.setActionCommand(roomName);
            btn.setEnabled(false);
            btn.setVisible(false);
        }
    }
    
    private void enableButton(String layerKey, String levelKey, String roomName, boolean enable) {
        int row = Integer.parseInt(layerKey) - 1;
        int column = Integer.parseInt(levelKey) - 1;
        
        JButton btn = (JButton)GameWindow.mapButtonPanel.getComponent(columns * row + column);
    
        btn.setIcon(roomIcons.get(roomName));
        btn.setEnabled(enable);
        btn.setVisible(enable);
    }

    // GETTERS AND SETTERS
    public Map<String, Map<String, Map<String, Map<String, String>>>> getGameMap() {
        return gameMap;
    }

    public void setGameMap(Map<String, Map<String, Map<String, Map<String, String>>>> gameMap) {
        this.gameMap = gameMap;
    }

    public List<StringBuilder> getMapLayout1() {
        return mapLayout1;
    }

    public void setMapLayout1(List<StringBuilder> mapLayout1) {
        this.mapLayout1 = mapLayout1;
    }

    public List<StringBuilder> getMapLayout2() {
        return mapLayout2;
    }

    public void setMapLayout2(List<StringBuilder> mapLayout2) {
        this.mapLayout2 = mapLayout2;
    }

    public List<StringBuilder> getMapLayout3() {
        return mapLayout3;
    }

    public void setMapLayout3(List<StringBuilder> mapLayout3) {
        this.mapLayout3 = mapLayout3;
    }
}