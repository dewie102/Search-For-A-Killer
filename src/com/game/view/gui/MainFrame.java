package com.game.view.gui;

import javax.swing.*;
import java.awt.*;

class MainFrame extends JFrame {
    MainFrame() {
        this.setTitle("Search For A Killer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 700);
        this.setLayout(null); // to position components manually
        this.setVisible(true);
        this.setLocationRelativeTo(null); // display window in centre of screen

        ImageIcon image = new ImageIcon("data/logo.png");
        this.setIconImage(image.getImage());

        this.getContentPane().setBackground(new Color(0x6495ED));

        JLabel label = new JLabel();
        label.setText("SEARCH FOR A KILLER");

        label.setVerticalTextPosition(JLabel.TOP);

        label.setForeground(Color.GREEN);
        label.setFont(new Font("MV Boli", Font.PLAIN, 30));

        label.setBackground(Color.BLACK);
        label.setOpaque(true);

        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);

        label.setBounds(0, 0, 1000, 50);

        JPanel panel = new JPanel();
        panel.setBackground(Color.RED);
        panel.setBounds(0, 0, 1000, 60);

        panel.setLayout(new BorderLayout());

        panel.add(label);

        this.add(panel);

        //        this.pack();

    }
}
