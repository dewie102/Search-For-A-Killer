package com.game.controller;

import org.json.simple.parser.ParseException;

import javax.swing.text.html.Option;
import java.io.IOException;

class MainController {
    public static void main(String[] args) throws ParseException, IOException {

        SplashController titlePage = new  SplashController("data/Title.json");
        SplashController devTitlePage = new SplashController("data/DevelopmentTitle.json");

        titlePage.displayTitle();
        devTitlePage.displayTitle();
        //call splash screen here
        IntroController.printIntro();
        //call help screen here
        //start game loop

        OptionsMenuController optionsMenuController = new OptionsMenuController();
        optionsMenuController.run();
    }
}