package com.game.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static com.game.view.framework.InputCollector.collectInput;

/**
 * ConsoleView class that contains a list with items that represent each item in the expected
 * output. Once the list is populated with all the text data its passed to the Console class
 * from within the show() method.
 */

public class ConsoleView {

    // INSTANCE VARIABLES
        // text list that holds all the ConsoleText objects
    List<ConsoleText> text = new ArrayList<>();

    // CONSTRUCTORS
    public ConsoleView(){}

    public ConsoleView(ConsoleText ...textArray){
        this();
        this.text.addAll(Arrays.asList(textArray));
    }

    // METHODS
        // Add ConsoleText to the text list
    void add(ConsoleText text){
    	this.text.add(text);
    }

        // For each ConsoleText in text call Console.print and pass the color and text
    public String show(){
        for(ConsoleText t : text){
            Console.print(t);
        }

        // INPUT COLLECTOR
        // call collectInput with an empty ignoreList
        // returns the collected input String
        return collectInput();
    }
}