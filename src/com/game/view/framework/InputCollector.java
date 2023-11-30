package com.game.view.framework;

import com.game.controller.Command;
import com.game.view.ConsoleText;
import com.game.view.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 * Static class that handles input entered by the user, it will do basic validations
 */
public class InputCollector {
    // Instance of java.util.Scanner that will collect input from the Console
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final String PROMPT = "> ";
    private static final String INVALID_COMMAND_ERROR_MESSAGE = "The command entered by the user is not valid.";

    // private constructor, class can't be instantiated
    private InputCollector(){}

    // This method will collect input from the console and return it back, no validations performed
    public static String collectInput(){
        Console.print(PROMPT);
        return SCANNER.nextLine();
    }

    /*
     * Here we collect input from the user in the form of <command target> where:
     *      command matches one of the commands in the commands map or a synonym of said command
     *      target matches a target in the entities list
     * The ignoreList is a list of words that will be ignored by the validation, for example "go to the kitchen" will be taken as "go kitchen"
     * The method will return a parsed string in the form of <command target>
     */
    public static String collectInput(List<Command> commands, List<String> entities, List<String> ignoreList) throws InvalidInputException {
        String line = collectInput();

        // Trim the input then replace multiple spaces with a single space
        line = line.trim().toLowerCase().replaceAll(" +", " ");
        // TODO DELETE THIS FOR LOOP, DEPRECATED
//        for (var ignore : ignoreList){
//            line = line.replace(" " + ignore + " ", " ");
//        }
        // Getting rid of any word that matches the ignoreList
        String[] lineArray = line.split(" ");
        line = "";
        for (String word : lineArray){
            if(!ignoreList.contains(word.toLowerCase())){
                line += " " + word;
            }
        }
        // Trimming one more time to remove the leading space we introduced
        line = line.trim();
        String[] parts = line.split(" ", 2);
        if(parts.length == 0)
            throw new InvalidInputException(INVALID_COMMAND_ERROR_MESSAGE);
        String commandString = parts[0];
        String targetString = null;
        Command command = null;
        for (Command value : commands){
            if(value.isAMatch(commandString)){
                command = value;
                commandString = command.getKeyWord();
                break;
            }
        }

        if(command == null)
            throw new InvalidInputException(INVALID_COMMAND_ERROR_MESSAGE);
        if(command.isStandalone())
            return command.getKeyWord();

        for (var tar : entities){
            if(tar.toLowerCase().equals(parts[1])){
                targetString = tar;
            }
        }
        if(targetString == null || commandString == null)
            throw new InvalidInputException("The command entered by the user is not valid.");

        return command.getKeyWord() + " " + targetString;
    }

    /*
     * This method will make sure the user enters a number from 1 to options.size()
     * The method will also accept if the user types the option
     * The method returns the index of the option selected by the user
     */
    public static String collectInput(List<ConsoleText> options) throws InvalidInputException {
        String line = collectInput().trim().toLowerCase();
        String result = null;
        int optionNumber;
        try{
            optionNumber = Integer.parseInt(line);
        }catch (Exception e){
            for (int i = 0; i < options.size(); i++){
                if(options.get(i).getText().toLowerCase().equals(line)) {
                    return ((Integer)i).toString();
                }
            }
            throw new InvalidInputException("The selected option is not valid.");
        }
        if(optionNumber < 1 || optionNumber > options.size())
            throw new InvalidInputException("The selected option is not valid.");
        optionNumber = optionNumber - 1;
        return ((Integer)optionNumber).toString();
    }
}
