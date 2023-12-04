package com.game.controller;

import com.game.model.Player;

import com.game.controller.AudioController;
import javax.swing.text.html.Option;
import java.io.IOException;

class MainController {
    public static void main(String[] args) throws  IOException {

        AudioController.loadMusic();
        //AudioController.loopMusic();
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