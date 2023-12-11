package com.game.controller;

import com.game.view.gui.TitleWindow;

import javax.swing.*;
import java.io.IOException;

public class MainController {
    public static boolean PLAY_IN_GUI = true;
    
    public static void main(String[] args) throws  IOException {

        AudioController.loadMusic();
        AudioController.musicVolDown(30f);
        //AudioController.loopMusic(); //<<<<Uncomment to have music play by default

        // Create the titlePage and devTitlePage objects that will take in the json data and pass it
        // to the ConsoleText class and the Console class, so it's outputted.
        if(!PLAY_IN_GUI) {
            GsonParserController titlePage = new GsonParserController("data/Title.json");
            GsonParserController developmentPage = new GsonParserController("data/DevelopmentTitle.json");
            GsonParserController introText = new GsonParserController("data/IntroText.json");
    
            titlePage.printJson();
            developmentPage.printJson();
            introText.printJson();
            OptionsMenuController optionsMenuController = new OptionsMenuController();
            optionsMenuController.run();
        } else {
            OptionsMenuController optionsMenuController = new OptionsMenuController();
            SwingUtilities.invokeLater(() -> new TitleWindow(optionsMenuController));
        }
    }
}