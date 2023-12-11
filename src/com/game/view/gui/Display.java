package com.game.view.gui;

import javax.swing.text.JTextComponent;

public class Display {
    // print method without a color selector
    public static void print(String message, JTextComponent component) {
        component.setText(component.getText() + message);
    }
    
    public static void printNewLine(String message, JTextComponent component){
        component.setText(component.getText() + message + "\n");
    }
}