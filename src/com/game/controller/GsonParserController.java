package com.game.controller;

import com.game.view.View;
import com.game.view.gui.DisplayView;
import com.game.view.gui.GameWindow;
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
            // Parse the JSON file
            JsonElement jsonElement = JsonParser.parseReader(reader);

            // Check if the root element is an object
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                List<ConsoleText> mainText = new ArrayList<>();
                
                // Initialize these to null so one is actually initialized whether playing in GUI or terminal
                View view = null;
                if(!MainController.PLAY_IN_GUI) {
                    view = new ConsoleView(List.of(mainText));
                } else {
                    view = new DisplayView(List.of(mainText), (textArea != null) ? textArea : GameWindow.gameTextArea);
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
                
                // Call the appropriate show command
                view.show();
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
