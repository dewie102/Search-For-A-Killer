package com.game.controller;

import com.game.view.ConsoleText;
import com.game.view.ConsoleView;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;

/**
 * SplashController has the responsibility to read and display the title page from the data/Title.json file.
 * Once the data has been read it's passed to the ConsoleView then outputted through the Console class.
 */

class SplashController {

    // INSTANCE VARIABLES
    private String FILE_NAME;

    // CONSTRUCTOR
    SplashController(String fileName){
        setFILE_NAME(fileName);
    }

    // METHODS
    void displayTitle(){
        JSONParser jsonParser = new JSONParser();

        try (FileReader fileReader = new FileReader(getFILE_NAME())) {
            // Parse the JSON file
            Object obj = jsonParser.parse(fileReader);

            // Check if the parsed object is a JSON object
            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;

                ConsoleView titleView =  new ConsoleView();

                // Iterate through the JSON object's entries and output the key-value pairs
                for (Object value : jsonObject.values()) {
                    titleView.add(new ConsoleText((String) value));
                }
                Collections.reverse(titleView.text);
                titleView.show();
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error reading/parsing the file: " + e.getMessage());
        }
    }

    // GETTERS AND SETTERS
    String getFILE_NAME() {
        return FILE_NAME;
    }
    void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME;
    }
}