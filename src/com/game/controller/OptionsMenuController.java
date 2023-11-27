package com.game.controller;

import com.game.view.ConsoleText;
import com.game.view.ConsoleView;
import com.game.view.MultipleChoiceConsoleView;

import java.util.List;

class OptionsMenuController {
    public void run(){
        MultipleChoiceConsoleView consoleView = new MultipleChoiceConsoleView(
                List.of(new ConsoleText("Main Menu: ")),
                List.of(
                        new ConsoleText("Play new game"),
                        new ConsoleText("Load Saved game"),
                        new ConsoleText("How to play?"),
                        new ConsoleText("Quit Game")
        ));
        String test = consoleView.show();
    }

    //TODO DELETE
    public static void main(String[] args) {
        OptionsMenuController controller = new OptionsMenuController();
        controller.run();
    }
}
