package com.game.view.terminal;

import com.game.view.View;
import com.game.view.framework.InputCollector;
import com.game.view.framework.InvalidInputException;

import java.util.ArrayList;
import java.util.List;
//import static com.game.view.framework.InputCollector.collectInput;

/**
 * ConsoleView class that contains a list with items that represent each item in the expected
 * output. Once the list is populated with all the text data its passed to the Console class
 * from within the show() method.
 */

public class ConsoleView implements View {
    // INSTANCE VARIABLES
    // This holds the text that will be presented in the screen, a List of List of Strings so we can individualy manage each list
    public List<List<ConsoleText>> textList = new ArrayList<>();
    public String errorMessage;

    // CONSTRUCTORS
    public ConsoleView(){}

    // This constructor is better since it keeps the reference
    public ConsoleView(List<List<ConsoleText>> textList){
        this();
        this.textList = textList;
    }

    // For each ConsoleText in text call Console.print and pass the color and text
    public void show(){
        displayText();
        executeViewLogic();
    }

    // show map
    public void showMap(){
        displayText();
    }

    public void executeViewLogic(){
        // EMPTY ON PURPOSE :D
    }

    public void displayText(){
        // Print all the text on this View
        for(var list : textList) {
            if(list != null) {
                for (ConsoleText t : list) {
                    Console.printNewLine(t);
                }
            }
        }
        // Print any error message
        if(errorMessage != null)
            Console.printNewLine(new ConsoleText(errorMessage, AnsiTextColor.RED));
    }
    
    // Function needed for Display so this one is empty
    public void clearText(){}

    public String collectInput() {
        while(true) {
            try {
                return InputCollector.collectInput();
            } catch (InvalidInputException exception) {
                errorMessage = exception.getMessage();
            }
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void clearErrorMessage(){
        this.errorMessage = null;
    }

    public void setText(List<List<ConsoleText>> text) {
        this.textList = text;
    }

    public List<List<ConsoleText>> getText() {
        return textList;
    }

    public List<List<ConsoleText>> getTextList() {
        return textList;
    }
}