package com.game.view.gui;

// GameClient.java
import com.game.controller.MainController;

import javax.swing.*;

public class GameClient {
    public static void main(String[] args) {
        // Execute Main Game Window
        MainController.PLAY_IN_GUI = true;
        //SwingUtilities.invokeLater(TitleWindow::new);
    }
}
