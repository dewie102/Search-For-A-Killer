package com.game.controller;

import com.game.view.gui.TitleWindow;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MainController {
    // "Feature toggle" for whether to play in the GUI or terminal
    public static boolean PLAY_IN_GUI = true;
    
    public static void main(String[] args) throws  IOException {
        readInConfig();
        
        startNewGame();
    }
    
    public static void startNewGame() {
        AudioController.loadMusic();
        AudioController.loopMusic();
        AudioController.setMusicVol(-43.5f);
        AudioController.setSfxVol(-27.0f);
    
        GameController.clearInstance();
        
        // Initialize all game components before doing specific terminal and GUI stuff
        GameController.getInstance().initialize();
    
        if(!PLAY_IN_GUI) {
            // Create the titlePage and devTitlePage objects that will take in the json data and pass it
            // to the ConsoleText class and the Console class, so it's outputted.
            GsonParserController titlePage = new GsonParserController("data/Title.json");
            GsonParserController developmentPage = new GsonParserController("data/DevelopmentTitle.json");
            GsonParserController introText = new GsonParserController("data/IntroText.json");
        
            titlePage.printJson();
            developmentPage.printJson();
            introText.printJson();
            OptionsMenuController optionsMenuController = new OptionsMenuController();
            optionsMenuController.run();
        } else {
            // Use this if you want/ need to add parameters
            //SwingUtilities.invokeLater(() -> new TitleWindow());
            SwingUtilities.invokeLater(TitleWindow::new);
        }
    }
    
    private static void readInConfig() {
        try {
            List<String> lines = Files.readAllLines(Path.of("data/config.txt"));
            for(String line : lines) {
                line = line.toLowerCase();
                if(line.contains("play_in_gui")) {
                    String[] parts = line.split(":");
                    if(parts.length > 1 && parts[1].contains("true")) {
                        PLAY_IN_GUI = true;
                    } else if(parts.length > 1 && parts[1].contains("false")) {
                        PLAY_IN_GUI = false;
                    }
                }
            }
        } catch(IOException e) {
            System.out.println("WARNING: config file not found, running with defaults");
        }
    }
}