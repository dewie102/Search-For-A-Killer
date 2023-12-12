package com.game.controller;

import com.game.view.gui.DisplayView;
import com.game.view.gui.NewGameWindow;
import com.game.view.terminal.ConsoleText;
import com.game.view.terminal.ConsoleView;
import com.google.gson.*;

import javax.swing.text.JTextComponent;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Generic GSON parser that reads in a json file and outputs the content.
 * The call to the method will take in a String filePath to locate the json file.
 */

public class GsonParserController {

    //INSTANCE VARIABLES
    private String filePath;

    // CONSTRUCTOR
    public GsonParserController(String filePath) {
        setFilePath(filePath);
    }

    // METHOD
    public void printJson() {
        printJson(null);
    }

    public void printJson(JTextComponent textArea) {
        try (FileReader reader = new FileReader(getFilePath())) {
            // Create a JSON parser
            JsonParser parser = new JsonParser();

            // Parse the JSON file
            JsonElement jsonElement = parser.parse(reader);

            // Check if the root element is an object
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                List<ConsoleText> mainText = new ArrayList<>();
                ConsoleView consoleView = null;
                DisplayView displayView = null;
                if(!MainController.PLAY_IN_GUI) {
                    consoleView = new ConsoleView(List.of(mainText));
                } else {
                    displayView = new DisplayView(List.of(mainText), (textArea != null) ? textArea : NewGameWindow.gameTextArea);
                }

                // Iterate through the JSON object
                Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();

                for (Map.Entry<String, JsonElement> entry : entries) {
                    JsonElement value = entry.getValue();
                    
                    // This stops the press enter to continue
                    if(MainController.PLAY_IN_GUI && "enter".equals(entry.getKey())) {
                        continue;
                    }
                    
                    // Check the value type and print accordingly
                    if (value.isJsonPrimitive()) {
                        mainText.add(new ConsoleText(value.getAsString()));
                    } else if (value.isJsonObject()) {
                        JsonObject nestedObject = value.getAsJsonObject();
                        mainText.add(new ConsoleText(nestedObject.toString()));
                    } else {
                        mainText.add(new ConsoleText(value.toString()));
                    }
                }
                if(!MainController.PLAY_IN_GUI) {
                    consoleView.show();
                } else {
                    displayView.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GETTER AND SETTERS

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
