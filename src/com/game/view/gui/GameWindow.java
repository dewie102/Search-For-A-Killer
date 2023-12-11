package com.game.view.gui;

import com.game.controller.GsonParserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWindow extends JFrame {
    private static JTextArea developmentTitleTextArea;

    public GameWindow() {
        setTitle("Search For A Killer");
        setSize(1000, 500);
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

        JPanel logoTextPanel = new JPanel(new BorderLayout());
        logoTextPanel.setBackground(new Color(34, 34, 34));

        logoTextPanel.add(logoLabel, BorderLayout.CENTER);

        developmentTitleTextArea = new JTextArea();
        developmentTitleTextArea.setEditable(false);
        developmentTitleTextArea.setLineWrap(true);
        developmentTitleTextArea.setWrapStyleWord(true);
        developmentTitleTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        developmentTitleTextArea.setForeground(Color.WHITE);
        developmentTitleTextArea.setBackground(new Color(34, 34, 34));
        developmentTitleTextArea.setBorder(BorderFactory.createEmptyBorder(10, 600, 10, 10));

        logoTextPanel.add(developmentTitleTextArea, BorderLayout.SOUTH);

        // Create a separate panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(34, 34, 34));

        // Use a custom font for the buttons
        Font buttonFont = new Font("Impact", Font.PLAIN, 15);

        JButton playGameButton = new JButton("Play Game");
        playGameButton.setFont(buttonFont);
        playGameButton.setBackground(Color.GREEN);
        playGameButton.setForeground(Color.BLACK);
        playGameButton.setFocusPainted(false);

        JButton quitButton = new JButton("Quit");
        quitButton.setFont(buttonFont);
        quitButton.setBackground(Color.RED);
        quitButton.setForeground(Color.BLACK);

        // Add buttons to the button panel
        buttonPanel.add(playGameButton);
        buttonPanel.add(quitButton);

        // Add the main panel to the frame
        panel.add(gameBanner, BorderLayout.NORTH);
        panel.add(logoTextPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        add(panel);

        setVisible(true);

        // Action Listeners

        playGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window
                dispose();

                // Open New Game Window
                SwingUtilities.invokeLater(NewGameWindow::createAndShowGUI);
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window and terminate the process
                dispose();
                System.exit(0);
            }
        });

        loadJsonData();
    }

    private void loadJsonData() {
        GsonParserController developmentPage = new GsonParserController("data/DevelopmentTitle.json");
        developmentPage.printJson(developmentTitleTextArea);
    }
}
