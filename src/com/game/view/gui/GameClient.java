package com.game.view.gui;

// GameClient.java
import javax.swing.*;

public class GameClient {
    public static void main(String[] args) {
        // Execute Main Game Window
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
