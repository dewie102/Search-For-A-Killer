package com.game.view.gui;

import com.game.controller.GameController;
import com.game.controller.GsonParserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class GameWindow {
    public static final int FRAME_WIDTH = 1400;
    public static final int FRAME_HEIGHT = 800;

    public static JTextArea gameTextArea;
    public static JTextArea roomInformationArea;
    public static JTextArea playerInformationArea;
    public static JTextArea mapArea;
    public static JTextField commandTextField;
    public static JTextArea helpTextArea;

    static void createAndShowGUI() {
        JFrame frame = new JFrame("Search For A Killer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // window icon
        ImageIcon image = new ImageIcon("data/logo.png");
        frame.setIconImage(image.getImage());

        // Window open in center
        frame.setLocationRelativeTo(null);

        // Game Banner Panel
        JPanel gameBannerPanel = createBannerPanel();
        frame.add(gameBannerPanel, BorderLayout.NORTH);

        // Main Panel, consist of game text, player/room info and map
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(50, 50, 50));

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.fill = GridBagConstraints.BOTH;

        int gameTextAreaWidth = (int) (FRAME_WIDTH * 0.6);

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(new Color(50, 50, 50));
        containerPanel.setPreferredSize(new Dimension(gameTextAreaWidth, 0));

        gameTextArea = createTextArea();
        JPanel gameTextPanel = createTextPanel(gameTextArea);

        containerPanel.add(gameTextPanel);

        mainPanel.add(gameTextPanel, gbcMain);

        JPanel informationPanel = new JPanel(new GridLayout(1, 2));
        informationPanel.setBackground(new Color(50, 50, 50));

        roomInformationArea = createTextArea();
        JPanel roomInformationPanel = createTextPanel(roomInformationArea);
        informationPanel.add(roomInformationPanel);

        playerInformationArea = createTextArea();
        JPanel playerInformationPanel = createTextPanel(playerInformationArea);
        informationPanel.add(playerInformationPanel);

        containerPanel.add(informationPanel);
        gbcMain.gridx = 0;
        gbcMain.weightx = 0.0; // use gameTextAreaWidth as it is
        gbcMain.weighty = 1.0;
        mainPanel.add(containerPanel, gbcMain);

        mapArea = createTextArea();
        mapArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        JPanel mapPanel = createTextPanel(mapArea);
        gbcMain.gridx = 1;
        gbcMain.weightx = 0.5;
        gbcMain.weighty = 1.0;
        gbcMain.gridheight = 2;
        mainPanel.add(mapPanel, gbcMain);

        // Add space around the panels
        int panelSpace = 10;
        gameTextPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));
        informationPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));
        mapPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));

        frame.add(mainPanel, BorderLayout.CENTER);

        // Action Panel, consist of command input, volume control and help/quit buttons
        JPanel actionPanel = new JPanel(new GridBagLayout());
        actionPanel.setBackground(new Color(50, 50, 50));

        GridBagConstraints gbcAction = new GridBagConstraints();
        gbcAction.fill = GridBagConstraints.BOTH;

        JPanel inputCommandPanel = createInputCommandPanel();
        gbcAction.gridx = 0;
        gbcAction.weightx = 0.31;
        actionPanel.add(inputCommandPanel, gbcAction);

        JPanel soundAdjustPanel = adjustSoundPanel();
        gbcAction.gridx = 1;
        gbcAction.weightx = 0.139;
        actionPanel.add(soundAdjustPanel, gbcAction);

        JPanel helpPanel = new JPanel(new FlowLayout());
        JButton helpButton = new JButton("Help");
        JButton quitButton = new JButton("Quit");
        helpPanel.setBackground(new Color(50, 50, 50));
        helpButton.setBackground(Color.GREEN);
        helpButton.setForeground(Color.BLACK);
        quitButton.setBackground(Color.RED);
        quitButton.setForeground(Color.BLACK);
        helpPanel.add(helpButton);
        helpPanel.add(quitButton);
        gbcAction.gridx = 2;
        gbcAction.weightx = 0.144;
        actionPanel.add(helpPanel, gbcAction);

        // Add space around the action panel
        inputCommandPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));
        soundAdjustPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));
        helpPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));

        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        gameTextAreaPrintln("Welcome to the game!");
        roomInformationAreaPrintln();
        playerInformationAreaPrintln();
        mapAreaPrintln();

        // Action Listeners

        // Add ActionListener for the Help button
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Display the help pop-up
                showHelpPopup();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window and terminate the process
                frame.dispose();
                System.exit(0);
            }
        });
        
        
        // TODO: POC
        GsonParserController introText = new GsonParserController("data/IntroText.json");
    
        //developmentPage.printJson();
        introText.printJson();
    }

    private static JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(new Color(50, 50, 50));
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Courier", Font.PLAIN, 14));

        // Wrapping text for both line and word if it cannot fit in the text area width
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        // Add padding around the text area
        int padding = 10;
        textArea.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        return textArea;
    }


    private static JPanel createTextPanel(JTextArea textArea) {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setBackground(new Color(50, 50, 50));
        panel.setOpaque(true);
        return panel;
    }

    private static JPanel createBannerPanel() {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Search For A Killer");
        Font customFont = new Font("Chiller", Font.BOLD, 36);
        label.setFont(customFont);
        label.setForeground(Color.WHITE);
        panel.setBackground(new Color(30, 30, 30));
        panel.add(label);
        return panel;
    }

    private static JPanel adjustSoundPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Volume");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(Color.WHITE);
        panel.setBackground(new Color(50, 50, 50));
        panel.setOpaque(true);
        panel.add(label, BorderLayout.NORTH);

        // Volume Control scroll bar
        JScrollBar volumeScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 50, 10, 0, 100);
        volumeScrollBar.setBackground(new Color(50, 50, 50));
        volumeScrollBar.setForeground(Color.WHITE);

        volumeScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            // Called when adjust volume
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int volume = e.getValue();

                // Volume control logic here

                gameTextAreaPrintln("Volume Adjusted: " + volume);
            }
        });

        panel.add(volumeScrollBar, BorderLayout.SOUTH);

        return panel;
    }

    private static JPanel createInputCommandPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50));

        commandTextField = new JTextField();
        commandTextField.setBackground(new Color(50, 50, 50));
        commandTextField.setForeground(Color.WHITE);

        commandTextField.addActionListener(new ActionListener() {
            @Override
            // Called when press enter in command input
            public void actionPerformed(ActionEvent e) {
                GameController.getInstance().runCommand(commandTextField.getText());
                commandTextField.setText("");
            }
        });

        JLabel label = new JLabel("Enter Command");
        label.setForeground(Color.WHITE);
        panel.add(label, BorderLayout.NORTH);
        panel.add(commandTextField, BorderLayout.CENTER);
        return panel;
    }

    private static void showHelpPopup() {
        JFrame helpFrame = new JFrame("Help");
        helpFrame.setSize(700, 500);
        helpFrame.setLocationRelativeTo(null);

        ImageIcon image = new ImageIcon("data/logo.png");
        helpFrame.setIconImage(image.getImage());

        helpTextArea = new JTextArea();
        helpTextArea.setEditable(false);
        helpTextArea.setBackground(new Color(50, 50, 50));
        helpTextArea.setForeground(Color.WHITE);

        // Set text content in help window
        //helpTextArea.setText("Help Window");
        GameController.getInstance().displayHelpMessage();

        JScrollPane scrollPane = new JScrollPane(helpTextArea);

        JPanel helpPanel = new JPanel(new BorderLayout());
        helpPanel.setBackground(new Color(50, 50, 50));
        helpPanel.add(scrollPane, BorderLayout.CENTER);

        helpFrame.add(helpPanel);

        helpFrame.setVisible(true);
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
