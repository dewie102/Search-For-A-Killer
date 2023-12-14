package com.game.view.gui;

import javax.swing.*;
import javax.swing.text.JTextComponent;
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
        panel.add(button);

    }
}