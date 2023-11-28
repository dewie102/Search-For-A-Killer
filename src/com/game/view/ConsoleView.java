package com.game.view;

import com.game.view.framework.InputCollector;
import com.game.view.framework.InvalidInputException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import static com.game.view.framework.InputCollector.collectInput;

/**
 * ConsoleView class that contains a list with items that represent each item in the expected
 * output. Once the list is populated with all the text data its passed to the Console class
 * from within the show() method.
 */

public class ConsoleView {

    // INSTANCE VARIABLES
        // text list that holds all the ConsoleText objects
    public List<ConsoleText> text = new ArrayList<>();
    public String errorMessage;

    // CONSTRUCTORS
    public ConsoleView(){}

    // This constructor is better since it keeps the reference
    public ConsoleView(List<ConsoleText> text){
        this();
        this.text = text;
    }

    // DEPRECATED
    public ConsoleView(ConsoleText ...textArray){
        this();
        this.text.addAll(Arrays.asList(textArray));
    }

    // METHODS
        // Add ConsoleText to the text list
    public void add(ConsoleText text){
    	this.text.add(text);
    }

        // For each ConsoleText in text call Console.print and pass the color and text
    public String show(){
        while (true){
            displayText();
            executeViewLogic();
            try{
                return collectInput();
            }catch (InvalidInputException exception){
                errorMessage = exception.getMessage();
            }
        }
    }

    void executeViewLogic(){
        // EMPTY ON PURPOSE :D
    }

    void displayText(){
        // Print all the text on this View
        for(ConsoleText t : text){
            Console.printNewLine(t);
        }
        // Print any error message
        if(errorMessage != null)
            Console.printNewLine(new ConsoleText(errorMessage, AnsiTextColor.RED));
    }

    String collectInput(){
        return InputCollector.collectInput();
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
}