package com.game.view.gui;

import com.game.view.View;
import com.game.view.framework.InputCollector;
import com.game.view.terminal.ConsoleText;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DisplayView implements View {
    // INSTANCE VARIABLES
    // This holds the text that will be presented in the screen, a List of Lists of Strings, so we can individually manage each list
    public List<List<ConsoleText>> textList = new ArrayList<>();
    public String errorMessage = null;
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
    
    public void executeViewLogic(){
        // EMPTY ON PURPOSE :D
    }
    
    public void displayText(){
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
        if(errorMessage != null) {
            StyledDocument styledDocument = ((JTextPane)getDisplayComponent()).getStyledDocument();
            int startPos = getDisplayComponent().getDocument().getLength();
            Display.printNewLine(errorMessage, getDisplayComponent());
            int endPos = getDisplayComponent().getDocument().getLength();
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setForeground(attrs, Color.red);
            styledDocument.setCharacterAttributes(startPos, endPos, attrs, false);
        }
    }
    
    public void clearText() {
        getDisplayComponent().setText("");
    }
    
    public String collectInput(){
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