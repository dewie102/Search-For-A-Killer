package com.game.view.terminal;

import com.game.controller.Command;
import com.game.view.framework.InputCollector;

import java.util.ArrayList;
import java.util.List;

public class CommandConsoleView extends ConsoleView {

    List<Command> commands;
    private final List<String> entities;
    private final List<String> ignoreList;
    
    public CommandConsoleView(List<List<ConsoleText>> text, List<Command> commands, List<String> entities, List<String> ignoreList){
        super(text);
        this.commands = commands;
        this.entities = entities;
        this.ignoreList = ignoreList;
    }

    @Override
    public String collectInput() {
        return InputCollector.collectInput(commands, entities, ignoreList);
    }
}
