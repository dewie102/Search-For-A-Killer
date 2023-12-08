package com.game.view.gui;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow() {
        setTitle("Search For A Killer");
        setSize(800, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon image = new ImageIcon("data/logo.png");
        setIconImage(image.getImage());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(34, 34, 34));

        Font customFont = new Font("Chiller", Font.PLAIN, 20);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(customFont);
        customFont = customFont.deriveFont(Font.PLAIN, 36);

        JLabel gameBanner = new JLabel("Search For A Killer");
        gameBanner.setFont(customFont);
        gameBanner.setForeground(Color.WHITE);
        gameBanner.setHorizontalAlignment(JLabel.CENTER);

        ImageIcon logoIcon = new ImageIcon("data/logo.png");
        JLabel logoLabel = new JLabel(logoIcon);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        // Create a separate panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(34, 34, 34));
        JButton playGameButton = new JButton("Play Game");
        JButton quitButton = new JButton("Quit");

        playGameButton.setBackground(Color.GREEN);
        playGameButton.setForeground(Color.WHITE);
        playGameButton.setFocusPainted(false);
        quitButton.setBackground(Color.RED);
        quitButton.setForeground(Color.WHITE);

        // Add buttons to the button panel
        buttonPanel.add(playGameButton);
        buttonPanel.add(quitButton);

        // Add components to the main panel
        panel.add(gameBanner, BorderLayout.NORTH);
        panel.add(logoLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(panel);

        setVisible(true);
    }
}
