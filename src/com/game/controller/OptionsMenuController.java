package com.game.controller;

import com.game.model.NotImplementedException;
import com.game.view.ConsoleText;
import com.game.view.ConsoleView;
import com.game.view.MultipleChoiceConsoleView;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

class OptionsMenuController {
    public void run(){
        MultipleChoiceConsoleView consoleView = new MultipleChoiceConsoleView(
                List.of(new ConsoleText("Main Menu: ")),
                List.of(
                        new ConsoleText("Play new game."),
                        new ConsoleText("Load Saved game."),
                        new ConsoleText("How to play?"),
                        new ConsoleText("About this game."),
                        new ConsoleText("Quit Game.")
        ));
        String userInput = consoleView.show();
        switch (userInput){
            case "0":
                newGame();
                break;
            case "1":
                loadSavedGame();
                break;
            case "2":
                howToPlay();
                break;
            case "3":
                aboutThisGame();
                break;
            case "4":
                quitGame();
                break;
        }
    }

    private void newGame(){
        GameController gameController = new GameController();
        gameController.run();
    }

    private void loadSavedGame(){
        throw new NotImplementedException("OptionsMenuController.loadSavedGame() is not implemented");
    }

    private void howToPlay(){
        throw new NotImplementedException("OptionsMenuController.howToPlay() is not implemented");
    }

    private void aboutThisGame(){
        throw new NotImplementedException("OptionsMenuController.aboutThisGame() is not implemented");
    }

    private void quitGame(){
        System.exit(0);
    }
}
