package com.game.view;

import com.game.view.terminal.ConsoleText;

import java.util.List;

public interface View {
    void show();
    
    void showMap();
    
    void executeViewLogic();
    
    void displayText();
    
    void clearText();
    
    String collectInput();
    
    String getErrorMessage();
    
    void setErrorMessage(String errorMessage);
    
    void clearErrorMessage();
    
    void setText(List<List<ConsoleText>> text);
    
    List<List<ConsoleText>> getText();
    
    List<List<ConsoleText>> getTextList();
}
