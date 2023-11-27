package com.game.controller;

import com.game.view.ConsoleView;
import com.game.view.ConsoleText;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

class IntroController {

    private static final ConsoleView cv = new ConsoleView();
    private static final JSONObject obj = loadJSON();

    public static void printIntro() throws ParseException, IOException {
        printStory();
        printObjective();
        printHowToWin();

        String aString = cv.show();
    }

    private static JSONObject loadJSON(){
        JSONParser parser = new JSONParser();
        try (Reader reader = new FileReader("data/introText.json")) {

            return (JSONObject) parser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void printStory(){
        assert obj != null;
        String story = (String) obj.get("story");
        ConsoleText ctStory = new ConsoleText(story);
        cv.add(ctStory);
    }

    private static void printObjective(){
        assert obj != null;
        String objective = (String) obj.get("objective");
        ConsoleText ctObjective = new ConsoleText(objective);
        cv.add(ctObjective);
    }

    private static void printHowToWin(){
        assert obj != null;
        String howToWin = (String) obj.get("howToWin");
        ConsoleText ctHowToWin = new ConsoleText(howToWin);
        cv.add(ctHowToWin);
    }

}