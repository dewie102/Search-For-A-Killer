package com.game.controller;

import com.game.view.gui.TitleWindow;

import javax.swing.*;
import java.io.IOException;

public class MainController {
    // "Feature toggle" for whether to play in the GUI or terminal
    public static boolean PLAY_IN_GUI = true;
    
    public static void main(String[] args) throws  IOException {

        AudioController.loadMusic();
        AudioController.loopMusic();
        AudioController.setMusicVol(-58.49485f);
        
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
}