package com.game.view.gui;

import javax.swing.*;
import java.awt.*;

class MainFrame extends JFrame {
    MainFrame() {
        this.setTitle("Search For A Killer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 700);
        this.setVisible(true);
        this.setLocationRelativeTo(null); // display window in centre of screen
//        this.pack(); -> sets the size depending on the components inside it

        ImageIcon image = new ImageIcon("data/logo.png");
        this.setIconImage(image.getImage());

        this.getContentPane().setBackground(new Color(0x6495ED));

    }
}
