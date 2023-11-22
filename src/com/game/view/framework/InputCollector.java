package com.game.view.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 * Static class that handles input entered by the user, it will do basic validations
 */
class InputCollector {
    // Instance of java.util.Scanner that will collect input from the Console
    private static final Scanner SCANNER = new Scanner(System.in);

    // private constructor, class can't be instantiated
    private InputCollector(){}

    // This method will collect input from the console and return it back, no validations performed
    public static String collectInput(){
        return SCANNER.nextLine();
    }

    // We call collectInput with an empty ignoreList
    public static String collectInput(Map<String, List<String>> commands, List<String> entities) throws InvalidInputException {
        return collectInput(commands, entities, new ArrayList<>());
    }

    /*
     * Here we collect input from the user in the form of <command target> where:
     *      command matches one of the commands in the commands map or a synonym of said command
     *      target matches a target in the entities list
     * The ignoreList is a list of words that will be ignored by the validation, for example "go to the kitchen" will be taken as "go kitchen"
     * The method will return a parsed string in the form of <command target>
     */
    public static String collectInput(Map<String, List<String>> commands, List<String> entities, List<String> ignoreList) throws InvalidInputException {
        String line = collectInput();
        line = line.trim().toLowerCase().replaceAll(" +", " ");
        for (var ignore : ignoreList){
            line = line.replace(" " + ignore + " ", " ");
            line = line.replace(ignore + " ", " ");
            line = line.replace(" " + ignore, " ");
        }
        line = line.replaceAll(" +", " ");
        String[] parts = line.split(" ", 2);
        String command = null;
        String target = null;

        for (var com : commands.entrySet()){
            if(com.getKey().toLowerCase().equals(parts[0]) || com.getValue().contains(parts[0])) {
                command = com.getKey();
                break;
            }
        }
        for (var tar : entities){
            if(tar.toLowerCase().equals(parts[1])){
                target = tar;
            }
        }
        if(target == null || command == null)
            throw new InvalidInputException("The command entered by the user is not valid.");

        return command + " " + target;
    }

    /*
     * This method will make sure the user enters a number from 1 to options.size()
     * The method will also accept if the user types the option
     * The method returns the index of the option selected by the user
     */
    public static String collectInput(List<String> options) throws InvalidInputException {
        String line = collectInput().trim().toLowerCase();
        String result = null;
        int optionNumber;
        try{
            optionNumber = Integer.parseInt(line);
        }catch (Exception e){
            for (int i = 0; i < options.size(); i++){
                if(options.get(i).toLowerCase().equals(line)) {
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
