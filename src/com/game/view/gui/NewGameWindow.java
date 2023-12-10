package com.game.view.gui;

import javax.swing.*;
import java.awt.*;

public class NewGameWindow {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(NewGameWindow::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Search For A Killer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 800);

        // Game Banner
        JPanel gameBannerPanel = createBannerPanel();
        frame.add(gameBannerPanel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.fill = GridBagConstraints.BOTH;

        // Making first column width 50%
        JPanel gameTextPanel = createLabelPanel("Game Console Text", Color.GREEN);
        gbcMain.gridx = 0;
        gbcMain.weightx = 0.5;
        gbcMain.weighty = 1.0;
        mainPanel.add(gameTextPanel, gbcMain);

        JPanel informationPanel = new JPanel(new GridLayout(2, 1));

        JPanel roomInformationPanel = createLabelPanel("Room Information", Color.YELLOW);
        informationPanel.add(roomInformationPanel);

        JPanel playerInformationPanel = createLabelPanel("Player Information", Color.RED);
        informationPanel.add(playerInformationPanel);

        gbcMain.gridx = 1;
        gbcMain.weightx = 0.25;
        mainPanel.add(informationPanel, gbcMain);

        JPanel mapPanel = createLabelPanel("Map", Color.ORANGE);
        gbcMain.gridx = 2;
        gbcMain.weightx = 0.25;
        mainPanel.add(mapPanel, gbcMain);

        frame.add(mainPanel, BorderLayout.CENTER);

        // Action Panel
        JPanel actionPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbcAction = new GridBagConstraints();
        gbcAction.fill = GridBagConstraints.BOTH;

        // Input Command Panel (50%)
        JPanel inputCommandPanel = createTextInputPanel();
        gbcAction.gridx = 0;
        gbcAction.weightx = 0.475;
        actionPanel.add(inputCommandPanel, gbcAction);

        // Sound Adjust Panel (25%)
        JPanel soundAdjustPanel = createLabelPanel("Sound Adjust", Color.CYAN);
        gbcAction.gridx = 1;
        gbcAction.weightx = 0.25;
        actionPanel.add(soundAdjustPanel, gbcAction);

        // Help Panel (25%)
        JPanel helpPanel = new JPanel(new FlowLayout());
        JButton helpButton = new JButton("Help");
        JButton quitButton = new JButton("Quit");
        helpPanel.add(helpButton);
        helpPanel.add(quitButton);
        gbcAction.gridx = 2;
        gbcAction.weightx = 0.144;
        actionPanel.add(helpPanel, gbcAction);

        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JPanel createBannerPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Search For A Killer");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.setBackground(Color.BLUE);
        panel.add(label);
        return panel;
    }

    private static JPanel createLabelPanel(String text, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.setBackground(color);
        panel.setOpaque(true);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private static JPanel createTextInputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Enter Command");
        JTextField textField = new JTextField();
        panel.add(label, BorderLayout.NORTH);
        panel.add(textField, BorderLayout.CENTER);
        return panel;
    }
}
