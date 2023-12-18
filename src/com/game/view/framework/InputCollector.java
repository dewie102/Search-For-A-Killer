package com.game.view.framework;

import com.game.controller.Command;
import com.game.controller.MainController;
import com.game.controller.commands.CommandType;
import com.game.view.terminal.ConsoleText;
import com.game.view.terminal.Console;

import java.util.List;
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
        // If playing in terminal actually wait for input using scanner
        if(!MainController.PLAY_IN_GUI) {
            return SCANNER.nextLine();
        } else {
            return "";
        }
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

        return validateCommandInput(line, commands, entities, ignoreList);
    }
    
    public static String validateCommandInput(String input, List<Command> commands, List<String> entities, List<String> ignoreList) throws InvalidInputException {
        // Trim the input then replace multiple spaces with a single space
        input = input.trim().toLowerCase().replaceAll(" +", " ");
    
        // Getting rid of any word that matches the ignoreList
        String[] lineArray = input.split(" ");
        input = "";
        for (String word : lineArray) {
            if (ignoreList == null || !ignoreList.contains(word.toLowerCase())) {
                input += " " + word;
            }
        }
        // Trimming one more time to remove the leading space we introduced
        input = input.trim();
        String[] parts = input.split(" ", 2);
        if(parts.length == 0)
            throw new InvalidInputException(INVALID_COMMAND_ERROR_MESSAGE);
        String commandString = parts[0];
        String targetString = null;
        Command command = null;
        for (Command value : commands){
            if(value.isAMatch(commandString)){
                //System.out.println("here");
                command = value;
                commandString = command.getKeyWord();
                break;
            }
        }
        // Making sure the command is not null
        if(command == null)
            throw new InvalidInputException(INVALID_COMMAND_ERROR_MESSAGE);
        if(parts.length > 1 && entities != null){
            for (var tar : entities){
                if(tar.toLowerCase().equals(parts[1])){
                    targetString = tar;
                    break;
                }
            }
        }
        // If the command is standalone, but we got two parts then we throw an exception
        if(parts.length > 1 && command.getCommandType() == CommandType.STANDALONE)
            throw new InvalidInputException(INVALID_COMMAND_ERROR_MESSAGE);
        // If the command is two parts, but the user only entered one then we also throw an exception
        if(parts.length < 2 && command.getCommandType() == CommandType.TWO_PARTS)
            throw new InvalidInputException(INVALID_COMMAND_ERROR_MESSAGE);
    
        String result = commandString;
        if(targetString != null)
            result += " " + targetString;
    
        return result;
    }

    /*
     * This method will make sure the user enters a number from 1 to options.size()
     * The method will also accept if the user types the option
     * The method returns the index of the option selected by the user
     */
    public static String collectInput(List<ConsoleText> options) throws InvalidInputException {
        String line = collectInput().trim().toLowerCase();
        
        return validateNumberChoice(line, options);
    }
    
    public static String validateNumberChoice(String input, List<ConsoleText> options) throws InvalidInputException {
        int optionNumber;
        try{
            optionNumber = Integer.parseInt(input);
        }catch (Exception e){
            for (int i = 0; i < options.size(); i++){
                if(options.get(i).getText().toLowerCase().equals(input)) {
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
