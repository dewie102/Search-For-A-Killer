package com.game.controller;

import java.io.IOException;

public class MainController {
    public static boolean PLAY_IN_GUI = false;
    
    public static void main(String[] args) throws  IOException {

        AudioController.loadMusic();
        AudioController.musicVolDown(30f);
        //AudioController.loopMusic(); //<<<<Uncomment to have music play by default

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
    }
}