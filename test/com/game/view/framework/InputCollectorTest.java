package com.game.view.framework;

import com.game.controller.Command;
import com.game.controller.GameController;
import com.game.controller.MainController;
import com.game.controller.commands.CommandType;
import com.sun.tools.javac.Main;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class InputCollectorTest {
    
    List<Command> commands;
    List<String> entities;
    List<String> ignoreList;
    
    @Before
    public void setUp() {
        commands = new ArrayList<>();
        // CommandName, synonyms, description, commandType, callback
        commands.add(new Command("go", new ArrayList<>(), "test go", CommandType.TWO_PARTS, null));
        commands.add(new Command("quit", new ArrayList<>(), "quitting or something", CommandType.STANDALONE, null));
        
        entities = new ArrayList<>();
        entities.add("testEntity");
        entities.add("test entity with spaces");
        
        ignoreList = new ArrayList<>();
    }
    
    @Test
    public void collectInput_ReturnsEmptyString_whenPlayingInGUI() {
        MainController.PLAY_IN_GUI = true;
        assertEquals("", InputCollector.collectInput());
    }
    
    @Test
    public void validateCommandInput_ReturnsSuccessful_whenSuppliedProperCommandAndEntity() {
        assertEquals("go testEntity", InputCollector.validateCommandInput("go testEntity", commands, entities, ignoreList));
        assertEquals("go testEntity", InputCollector.validateCommandInput(" go  testEntity ", commands, entities, ignoreList));
        assertEquals("go test entity with spaces", InputCollector.validateCommandInput("go test entity with spaces", commands, entities, ignoreList));
        assertEquals("go test entity with spaces", InputCollector.validateCommandInput(" go  test entity with spaces ", commands, entities, ignoreList));
    }
    
    @Test (expected=InvalidInputException.class)
    public void validateCommandInput_ReturnsInvalidInputException_whenSuppliedIncorrectParameterCount() {
        InputCollector.validateCommandInput("go", commands, entities, ignoreList);
        InputCollector.validateCommandInput("quit now!", commands, entities, ignoreList);
    }
    
    @Test (expected=NullPointerException.class)
    public void validateCommandInput_ReturnsInvalidInputException_whenSuppliedNULLCommand() {
        InputCollector.validateCommandInput(null, commands, entities, ignoreList);
    }
    
    @Test (expected=InvalidInputException.class)
    public void validateCommandInput_ReturnsInvalidInputException_whenSuppliedEmptyCommand() {
        InputCollector.validateCommandInput("test", commands, entities, ignoreList);
    }
}