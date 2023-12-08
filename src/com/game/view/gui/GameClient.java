package com.game.view.gui;

// GameClient.java
import javax.swing.*;

public class GameClient {
    public static void main(String[] args) {
        // This is the client code
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
