package com.game.controller;

import com.game.controller.io.JsonMessageParser;
import com.game.model.NotImplementedException;
import com.game.view.Console;
import com.game.view.ConsoleText;
import com.game.view.MultipleChoiceConsoleView;

import java.util.ArrayList;
import java.util.List;

class OptionsMenuController {
    List<ConsoleText> options = new ArrayList<>();

    private final List<ConsoleText> gameResult = new ArrayList<>();

    public void run(){
        JsonMessageParser.loadPlayerOptions();
        if(options.isEmpty()) {
            for (String option : JsonMessageParser.getPlayerOptions()) {
                options.add(new ConsoleText(option));
            }
        }
        MultipleChoiceConsoleView consoleView = new MultipleChoiceConsoleView(
                List.of(List.of(new ConsoleText("Main Menu: "))), options);
        while (true) {
            String userInput = consoleView.show();
            switch (userInput) {
                case "0":
                    newGame();
                    break;
                case "1":
                    quitGame();
                    break;
            }
        }
    }

    private void newGame(){
        GameController gameController = new GameController();
        GameResult gameResult = gameController.run();
        if (gameResult == GameResult.LOSS){
            Console.print("You lost the game");
        }else {
            Console.print("You won");
        }
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
