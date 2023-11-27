package com.game.controller;

import com.game.model.Player;
import org.json.simple.parser.ParseException;

import java.io.IOException;

class MainController {
    public static void main(String[] args) throws ParseException, IOException {

        // Create the titlePage and devTitlePage objects that will take in the json data and pass it
        // to the ConsoleText class and the Console class, so it's outputted.
        SplashController titlePage = new  SplashController("data/Title.json");
        SplashController devTitlePage = new SplashController("data/DevelopmentTitle.json");
        titlePage.displayTitle();
        devTitlePage.displayTitle();

        //call splash screen here
        IntroController.printIntro();

        // create the player
        Player player1 = new Player("Kitchen");
        // create the game status area
        GameStatusController gameStatusController = new GameStatusController(player1.getCurrentLocation(), player1.getInventory());
        gameStatusController.displayGameStatus();
    }

}