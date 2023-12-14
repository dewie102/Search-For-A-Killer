package com.game.view.gui;

import com.game.controller.Command;
import com.game.view.framework.InputCollector;
import com.game.view.terminal.ConsoleText;

import javax.swing.text.JTextComponent;
import java.util.ArrayList;
import java.util.List;

public class CommandDisplayView extends DisplayView {
    List<Command> commands = new ArrayList<>();
    private List<String> entities = new ArrayList<>();
    private List<String> ignoreList = new ArrayList<>();
    private List<String> standaloneCommands = new ArrayList<>();
    private String escapeCommand = "quit";
    
    public CommandDisplayView(List<List<ConsoleText>> text, JTextComponent textComponent, List<Command> commands, List<String> entities, List<String> ignoreList){
        super(text, textComponent);
        this.commands = commands;
        this.entities = entities;
        this.ignoreList = ignoreList;
    }
    
    @Override
    String collectInput() {
        return InputCollector.collectInput(commands, entities, ignoreList);
    }
    
    public String validateInput(String input) {
        return InputCollector.validateCommandInput(input, commands, entities, ignoreList);
    }
}