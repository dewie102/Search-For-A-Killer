package com.game.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameWindow {
    private static JTextArea gameTextArea;
    private static JTextArea roomInformationArea;
    private static JTextArea playerInformationArea;
    private static JTextArea mapArea;
    private static JTextField commandTextField;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NewGameWindow::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Search For A Killer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 800);

        // Game Banner Panel
        JPanel gameBannerPanel = createBannerPanel();
        frame.add(gameBannerPanel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.fill = GridBagConstraints.BOTH;

        // Making first column width 50%
        gameTextArea = createTextArea();
        JPanel gameTextPanel = createTextPanel(gameTextArea);
        gbcMain.gridx = 0;
        gbcMain.weightx = 0.5;
        gbcMain.weighty = 1.0;
        mainPanel.add(gameTextPanel, gbcMain);

        JPanel informationPanel = new JPanel(new GridLayout(2, 1));

        roomInformationArea = createTextArea();
        JPanel roomInformationPanel = createTextPanel(roomInformationArea);
        informationPanel.add(roomInformationPanel);


        playerInformationArea = createTextArea();
        JPanel playerInformationPanel = createTextPanel(playerInformationArea);
        informationPanel.add(playerInformationPanel);

        gbcMain.gridx = 1;
        gbcMain.weightx = 0.25;
        mainPanel.add(informationPanel, gbcMain);

        mapArea = createTextArea();
        JPanel mapPanel = createTextPanel(mapArea);
        gbcMain.gridx = 2;
        gbcMain.weightx = 0.25;
        mainPanel.add(mapPanel, gbcMain);

        frame.add(mainPanel, BorderLayout.CENTER);

        // Action Panel
        JPanel actionPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbcAction = new GridBagConstraints();
        gbcAction.fill = GridBagConstraints.BOTH;

        JPanel inputCommandPanel = createTextInputPanel();
        gbcAction.gridx = 0;
        gbcAction.weightx = 0.475;
        actionPanel.add(inputCommandPanel, gbcAction);

        JPanel soundAdjustPanel = createLabelPanel();
        gbcAction.gridx = 1;
        gbcAction.weightx = 0.25;
        actionPanel.add(soundAdjustPanel, gbcAction);

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

        gameTextAreaPrintln("Welcome to the game!");
        roomInformationAreaPrintln();
        playerInformationAreaPrintln();
        mapAreaPrintln();

    }

    private static JPanel createBannerPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Search For A Killer");
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.setBackground(Color.BLUE);
        panel.add(label);
        return panel;
    }

    private static JPanel createLabelPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Sound Adjust");
        label.setHorizontalAlignment(JLabel.CENTER);
        panel.setBackground(Color.CYAN);
        panel.setOpaque(true);
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private static JPanel createTextInputPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        commandTextField = new JTextField();
        commandTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                executeCommand(commandTextField.getText());
                commandTextField.setText("");
            }
        });

        JLabel label = new JLabel("Enter Command");
        panel.add(label, BorderLayout.NORTH);
        panel.add(commandTextField, BorderLayout.CENTER);
        return panel;
    }

    private static JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        return textArea;
    }

    private static JPanel createTextPanel(JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setBackground(Color.GREEN);
        panel.setOpaque(true);
        return panel;
    }

    private static void gameTextAreaPrintln(String message) {
        gameTextArea.append(message + "\n");
    }

    private static void roomInformationAreaPrintln() {
        roomInformationArea.append("Room Information" + "\n");
    }

    private static void playerInformationAreaPrintln() {
        playerInformationArea.append("Player Information Area" + "\n");
    }

    private static void mapAreaPrintln() {
        mapArea.append("Map Area" + "\n");
    }

    private static void executeCommand(String command) {
        gameTextAreaPrintln("Player entered command: " + command);
    }
}
