package com.game.view.gui;

import com.game.controller.OptionsMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TitleWindow extends JFrame {
    public TitleWindow(OptionsMenuController menuController) {
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

        // Add components to the main panel
        panel.add(gameBanner, BorderLayout.NORTH);
        panel.add(logoLabel, BorderLayout.CENTER);
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
                SwingUtilities.invokeLater(GameWindow::createAndShowGUI);
                menuController.newGame();
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
    }
}
