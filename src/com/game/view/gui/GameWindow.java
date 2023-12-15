package com.game.view.gui;

import com.game.controller.GameController;
import com.game.controller.GameResult;
import com.game.controller.GsonParserController;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class GameWindow {
    public static final int FRAME_WIDTH = 1400;
    public static final int FRAME_HEIGHT = 800;
    public static final Color MAIN_BACKGROUND_COLOR = new Color(50, 50, 50);

    public static JTextPane gameTextArea;
    public static JTextArea roomInformationArea;
    public static JTextArea playerInformationArea;
    public static JTextArea mapArea;
    public static JTextField commandTextField;
    public static JTextArea helpTextArea;
    public static JTextArea talkTextArea;
    public static JPanel gameTextPanel;
    public static JPanel talkButtonPanel;
    public static JPanel mainTalkPanel;
    public static JPanel mapButtonPanel;

    static void createAndShowGUI() {
        JFrame frame = new JFrame("Search For A Killer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // window icon
        ImageIcon image = new ImageIcon("data/logo.png");
        frame.setIconImage(image.getImage());

        // Window open in center
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Game Banner Panel
        JPanel gameBannerPanel = createBannerPanel();
        frame.add(gameBannerPanel, BorderLayout.NORTH);

        // Main Panel, consist of game text, player/room info and map
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(MAIN_BACKGROUND_COLOR);

        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.fill = GridBagConstraints.BOTH;

        int gameTextAreaWidth = (int) (FRAME_WIDTH * 0.6);

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBackground(MAIN_BACKGROUND_COLOR);
        containerPanel.setPreferredSize(new Dimension(gameTextAreaWidth, 0));


        // game text panel
        gameTextArea = createTextPane();
        gameTextPanel = createTextPanel(gameTextArea);
        gameTextPanel.setVisible(true);
        gameTextPanel.setPreferredSize(new Dimension(0, (int) (FRAME_HEIGHT/2.2)));
        containerPanel.add(gameTextPanel);
        mainPanel.add(gameTextPanel, gbcMain);

        // talk panel
        talkTextArea = createTextArea();
        mainTalkPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        mainTalkPanel.setBackground(MAIN_BACKGROUND_COLOR);
        JScrollPane scrollPane = new JScrollPane(talkTextArea);
        talkButtonPanel = new JPanel(new GridLayout(0,1));
        talkButtonPanel.setBackground(MAIN_BACKGROUND_COLOR);
        mainTalkPanel.setPreferredSize(new Dimension(0, (int) (FRAME_HEIGHT/2.2)));
        mainTalkPanel.add(scrollPane);
        mainTalkPanel.add(talkButtonPanel);
        containerPanel.add(mainTalkPanel);
        mainPanel.add(mainTalkPanel, gbcMain);
        mainTalkPanel.setVisible(false);
        int padding = 10;
        mainTalkPanel.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));


        JPanel informationPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        informationPanel.setBackground(MAIN_BACKGROUND_COLOR);

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

        // This is the main map text area, where the text is displayed
        mapArea = createTextArea();
        // "Courier New" is a monospaced font, used to keep the map from deforming
        mapArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        JPanel mapPanel = createTextPanel(mapArea);
    
        /*// This is the panel for the map buttons
        mapButtonPanel = new JPanel(new GridBagLayout());
        mapButtonPanel.setOpaque(false);
        mapButtonPanel.setBackground(new Color(0, 0, 0, 0));
        //mapButtonPanel.setBackground(Color.red);
        
        GridBagConstraints mapConstraints = new GridBagConstraints();
        mapConstraints.fill = GridBagConstraints.HORIZONTAL;
        
        JButton testBtn1 = new JButton("1");
        ImageIcon test = new ImageIcon("data/test.PNG");
        test = new ImageIcon(test.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        testBtn1.setIcon(test);
        testBtn1.setPreferredSize(new Dimension(200, 200));
        testBtn1.setOpaque(false);
        testBtn1.setContentAreaFilled(false); // Maybe just this one?
        testBtn1.setBorderPainted(false);
        mapConstraints.gridx = 0;
        mapConstraints.gridy = 0;
        mapConstraints.gridwidth = 1;
        mapConstraints.gridheight = 1;
        mapButtonPanel.add(testBtn1, mapConstraints);
        
        JButton testBtn2 = new JButton("2");
        //testBtn.setSize(200, 200);
        mapConstraints.gridx = 1;
        mapConstraints.gridy = 0;
        mapButtonPanel.add(testBtn2, mapConstraints);
    
        JButton testBtn3 = new JButton("3");
        //testBtn.setSize(200, 200);
        mapConstraints.gridx = 2;
        mapConstraints.gridy = 0;
        mapButtonPanel.add(testBtn3, mapConstraints);
        
        mapButtonPanel.setPreferredSize(new Dimension(60, 60));

        //mapPanel.add(mapButtonPanel);*/
        
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
        actionPanel.setBackground(MAIN_BACKGROUND_COLOR);

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
        helpPanel.setBackground(MAIN_BACKGROUND_COLOR);
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
        
        GsonParserController introText = new GsonParserController("data/IntroText.json");
        introText.printJson();
        
        GameController.getInstance().initializeGUIComponents();
    }

    private static JTextArea createTextArea() {
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(MAIN_BACKGROUND_COLOR);
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
    
    private static JTextPane createTextPane() {
        JTextPane textPane = new JTextPane();
        textPane.setEditable(false);
        textPane.setBackground(MAIN_BACKGROUND_COLOR);
        textPane.setForeground(Color.WHITE);
        textPane.setFont(new Font("Courier", Font.PLAIN, 14));
        
        // Add padding around the text area
        int padding = 10;
        textPane.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        
        return textPane;
    }

    private static JPanel createTextPanel(JTextComponent textArea) {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.setBackground(MAIN_BACKGROUND_COLOR);
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
        panel.setBackground(MAIN_BACKGROUND_COLOR);
        panel.setOpaque(true);
        panel.add(label, BorderLayout.NORTH);

        // Volume Control scroll bar
        JScrollBar volumeScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 50, 10, 0, 100);
        volumeScrollBar.setBackground(MAIN_BACKGROUND_COLOR);
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
        panel.setBackground(MAIN_BACKGROUND_COLOR);

        commandTextField = new JTextField();
        commandTextField.setBackground(MAIN_BACKGROUND_COLOR);
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
        helpTextArea.setBackground(MAIN_BACKGROUND_COLOR);
        helpTextArea.setForeground(Color.WHITE);

        int padding = 10;
        helpTextArea.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        // Set text content in help window
        //helpTextArea.setText("Help Window");
        GameController.getInstance().displayHelpMessage();

        JScrollPane scrollPane = new JScrollPane(helpTextArea);

        JPanel helpPanel = new JPanel(new BorderLayout());
        helpPanel.setBackground(MAIN_BACKGROUND_COLOR);
        helpPanel.add(scrollPane, BorderLayout.CENTER);

        helpFrame.add(helpPanel);

        helpFrame.setVisible(true);
    }

    public static JPanel createGridLayoutPanel(int rows, int columns) {
        JPanel panel = new JPanel(new GridLayout(rows, columns)); // 5 rows, 1 column
        panel.setBackground(MAIN_BACKGROUND_COLOR);

        return panel;
    }
    
    public static JButton createMapButton(String roomValue) {
        JButton button = new JButton();
        button.setActionCommand(roomValue);
        
        button.addActionListener((evt) -> {
            String room = evt.getActionCommand();
            System.out.printf("Printing room value: %s\n", room);
            //DO something
        });
        
        return button;
    }

    public static JButton createButtonWithId(String label, int id) {
        JButton button = new JButton(label);
        button.setActionCommand(String.valueOf(id));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                int buttonID = Integer.parseInt(command);

                GameController gameController = GameController.getInstance();

                // if conversation does not have a followup questions
                if (!gameController.conversationController.followedUpQuestion) {
                    gameController.conversationController.result = buttonID;
                } else {

                    // report weapon/suspect
                    GameController.getInstance().character.getConversation().getDialog(gameController.conversationController.result).getFollowUpConversation().getDialog(buttonID).reportIfAble();

                    // check winning condition
                    GameResult result = gameController.conversationController.checkWinningConditions.checkWinningConditions();

                    // check for result after reporting suspect/weapon
                    if (!result.equals(GameResult.UNDEFINED)) {
                        if (result.equals(GameResult.WIN)) {
                            JOptionPane.showMessageDialog(null, "You WON the game\nThanks for playing.", "Result", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "You LOST the game\nThanks for playing.", "Result", JOptionPane.INFORMATION_MESSAGE);

                        }
                        System.exit(0);
                    }

                    // reset followup questions and result after answering followup question
                    gameController.conversationController.followedUpQuestion = false;
                    gameController.conversationController.result = -1;
                }
                gameController.conversationController.run(GameController.getInstance().player, GameController.getInstance().character);

            }
        });

        return button;
    }

    private static void gameTextAreaPrintln(String message) {
        //gameTextArea.append(message + "\n");
        gameTextArea.setText(gameTextArea.getText() + message + "\n");
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
