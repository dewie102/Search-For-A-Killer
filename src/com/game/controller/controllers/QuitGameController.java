package com.game.controller.controllers;

import com.game.controller.Command;
import com.game.view.CommandConsoleView;
import com.game.view.ConsoleText;
import com.game.view.MultipleChoiceConsoleView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class QuitGameController {
    private CommandConsoleView consoleView;
    private List<ConsoleText> mainText;
    private List<ConsoleText> secondaryText;
    private List<ConsoleText> quitGameText = new ArrayList<>();
    private final Map<String, Command> commandMap = new TreeMap<>();

    public QuitGameController(List<ConsoleText> mainText, List<ConsoleText> secondaryText){
        this.mainText = mainText;
        this.secondaryText = secondaryText;
        consoleView = new CommandConsoleView(List.of(mainText, secondaryText, quitGameText), new ArrayList<>(commandMap.values()), null, null);
    }

    public void run(){

    }
}
