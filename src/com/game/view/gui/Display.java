package com.game.view.gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class Display {
    // print method without a color selector
    public static void print(String message, JTextComponent component) {
        component.setText(component.getText() + message);
    }
    
    public static void printNewLine(String message, JTextComponent component){
        component.setText(component.getText() + message + "\n");
    }

    public static void printNewLineWithButton(String message, int optionId, JTextComponent component, JPanel panel){
        JButton button = GameWindow.createButtonWithId(message, optionId);
        button.setBackground(GameWindow.MAIN_BACKGROUND_COLOR);
        button.setForeground(Color.WHITE);
        panel.add(button);

    }
    
    public static void printError(String message, JTextComponent component) {
        StyledDocument styledDocument = ((JTextPane)component).getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, Color.red);
        
        try {
            // Insert error message with the attribute color set to red
            styledDocument.insertString(styledDocument.getLength(), message, attrs);
            // Set the color back to the "default" color
            StyleConstants.setForeground(attrs, GameWindow.MAIN_FOREGROUND_COLOR);
            // Insert a space with the new attribute as it will keep these until changed later
            styledDocument.insertString(styledDocument.getLength(), " ", attrs);
        } catch (BadLocationException e) {
            printNewLine(e.getMessage(), component);
        }
    }
}