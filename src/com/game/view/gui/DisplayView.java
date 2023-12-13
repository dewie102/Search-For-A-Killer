package com.game.view.gui;

import com.game.view.framework.InputCollector;
import com.game.view.framework.InvalidInputException;
import com.game.view.terminal.AnsiTextColor;
import com.game.view.terminal.Console;
import com.game.view.terminal.ConsoleText;

import javax.swing.text.JTextComponent;
import java.util.ArrayList;
import java.util.List;

public class DisplayView {
    // INSTANCE VARIABLES
    // This holds the text that will be presented in the screen, a List of Lists of Strings, so we can individually manage each list
    public List<List<ConsoleText>> textList = new ArrayList<>();
    public String errorMessage;
    public JTextComponent displayComponent;
    
    // CONSTRUCTORS
    public DisplayView(){}
    
    // This constructor is better since it keeps the reference
    public DisplayView(List<List<ConsoleText>> textList, JTextComponent displayComponent){
        this();
        setText(textList);
        setDisplayComponent(displayComponent);
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
    
    void executeViewLogic(){
        // EMPTY ON PURPOSE :D
    }
    
    void displayText(){
        // Print all the text on this View
        for(var list : textList) {
            if(list != null) {
                for (ConsoleText t : list) {
                    //Console.printNewLine(t);
                    Display.printNewLine(t.getText(), getDisplayComponent());
                }
            }
        }
        // Print any error message
        if(errorMessage != null)
            //Console.printNewLine(new ConsoleText(errorMessage, AnsiTextColor.RED));
            Display.printNewLine(errorMessage, getDisplayComponent());
    }
    
    public void clearText() {
        getDisplayComponent().setText("");
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
    
    public void setText(List<List<ConsoleText>> text) {
        this.textList = text;
    }
    
    public List<List<ConsoleText>> getText() {
        return textList;
    }
    
    public List<List<ConsoleText>> getTextList() {
        return textList;
    }
    
    public JTextComponent getDisplayComponent() {
        return displayComponent;
    }
    
    public void setDisplayComponent(JTextComponent displayComponent) {
        this.displayComponent = displayComponent;
    }
}