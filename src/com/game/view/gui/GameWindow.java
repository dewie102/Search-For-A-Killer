package com.game.view.gui;

import com.game.controller.GameController;
import com.game.controller.GameResult;
import com.game.controller.GsonParserController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

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
    private static int currentVolume = 25;
    private static int previousVolume = 25;
    private static int currentSfxVolume = 25;
    private static int previousSfxVolume = 25;
    private static boolean isMuted = false;
    private static boolean isSfxMuted = false;
    private static String currentVolumeOption = "1";
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



        // Add space around the panelsmapArea = createTextArea();
        //        mapArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        //        JPanel mapPanel = createTextPanel(mapArea);
        //        gbcMain.gridx = 1;
        //        gbcMain.weightx = 0.5;
        //        gbcMain.weighty = 1.0;
        //        gbcMain.gridheight = 2;
        //        mainPanel.add(mapPanel, gbcMain);
        //
        //        // Add space around the panels
        //        int panelSpace = 10;
        //        gameTextPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));
        //        informationPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));
        //        mapPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));mappane;
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
        // main panel
        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.setBackground(MAIN_BACKGROUND_COLOR);
        mainPanel.setOpaque(true);

        // Music volume panel
        JPanel volumePanel = new JPanel(new GridLayout(2, 1));
        volumePanel.setBackground(MAIN_BACKGROUND_COLOR);
        volumePanel.setOpaque(true);

        JLabel currentVolumeLabel = new JLabel("Volume: " + getCurrentVolume());
        currentVolumeLabel.setHorizontalAlignment(JLabel.CENTER);
        currentVolumeLabel.setForeground(Color.WHITE);

        JScrollBar volumeScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, getCurrentVolume(), 10, 0, 110);
        volumeScrollBar.setBackground(MAIN_BACKGROUND_COLOR);
        volumeScrollBar.setForeground(Color.WHITE);
        volumeScrollBar.setPreferredSize(new Dimension(5, 5));

        ImageIcon soundIcon = resizeImageIcon(new ImageIcon("data/volume.png"));
        JButton muteButton = new JButton(soundIcon);
        muteButton.setBackground(Color.WHITE);
        muteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleMute();
                updateMuteButton(muteButton, soundIcon);
            }
        });

        volumeScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int volume = e.getValue();
                updateVolumeControls(volume, currentVolumeLabel,muteButton);
            }
        });

        volumePanel.add(currentVolumeLabel);
        volumePanel.add(createScrollBarAndMuteButtonPanel(volumeScrollBar, muteButton));

        // SFX Panel
        JPanel sfxPanel = new JPanel(new GridLayout(2, 1));
        sfxPanel.setBackground(MAIN_BACKGROUND_COLOR);
        sfxPanel.setOpaque(true);

        JLabel sfxVolumeLabel = new JLabel("SFX Volume: " + currentSfxVolume);
        sfxVolumeLabel.setHorizontalAlignment(JLabel.CENTER);
        sfxVolumeLabel.setForeground(Color.WHITE);

        JScrollBar sfxVolumeScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, currentSfxVolume, 10, 0, 110);
        sfxVolumeScrollBar.setBackground(MAIN_BACKGROUND_COLOR);
        sfxVolumeScrollBar.setForeground(Color.WHITE);
        sfxVolumeScrollBar.setPreferredSize(new Dimension(5, 5));

        JButton sfxMuteButton = new JButton(soundIcon);
        sfxMuteButton.setBackground(Color.WHITE);
        sfxMuteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleSfxMute();
                updateSfxMuteButton(sfxMuteButton, soundIcon);
            }
        });

        sfxVolumeScrollBar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int volume = e.getValue();
                updateSfxVolumeControls(volume, sfxVolumeLabel, sfxMuteButton);
            }
        });

        sfxPanel.add(sfxVolumeLabel);
        sfxPanel.add(createScrollBarAndMuteButtonPanel(sfxVolumeScrollBar, sfxMuteButton));

        // Adding both panels to the main panel
        mainPanel.add(volumePanel);
        mainPanel.add(sfxPanel);

        return mainPanel;
    }

    private static JPanel createScrollBarAndMuteButtonPanel(JScrollBar scrollBar, JButton muteButton) {
        // panel with GridBagLayout for scroll bar and mute button
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(MAIN_BACKGROUND_COLOR);
        panel.setOpaque(true);

        // constraints for scroll bar
        GridBagConstraints scrollBarConstraints = new GridBagConstraints();
        scrollBarConstraints.gridx = 0;
        scrollBarConstraints.gridy = 0;
        scrollBarConstraints.weightx = 0.95;
        scrollBarConstraints.fill = GridBagConstraints.BOTH;
        scrollBarConstraints.insets = new Insets(0, 0, 0, 5); // Add 5 pixels right padding

        // constraints for mute button
        GridBagConstraints muteButtonConstraints = new GridBagConstraints();
        muteButtonConstraints.gridx = 1;
        muteButtonConstraints.gridy = 0;
        muteButtonConstraints.weightx = 0.05;
        muteButtonConstraints.fill = GridBagConstraints.BOTH;

        // Add scroll bar and mute button to the panel with constraints
        panel.add(scrollBar, scrollBarConstraints);
        panel.add(muteButton, muteButtonConstraints);

        return panel;
    }

    // Helper methods to update volume controls
    private static void updateVolumeControls(int volume, JLabel volumeLabel, JButton muteButton) {
        volumeLabel.setText("Volume: " + volume);
        setCurrentVolumeOption("2");
        setCurrentVolume(volume);
        System.out.println(getCurrentVolume());
        GameController.getInstance().runCommand("volume");

        if (isMuted()) {
            muteButton.setIcon(resizeImageIcon(new ImageIcon("data/volume.png")));
            toggleMute();
        }
    }

    private static void updateSfxVolumeControls(int volume, JLabel volumeLabel, JButton muteButton) {
        volumeLabel.setText("SFX Volume: " + volume);
        setCurrentVolumeOption("6");
        setCurrentSfxVolume(volume);
        System.out.println(getCurrentVolume());
        GameController.getInstance().runCommand("volume");

        if (isSfxMuted()) {
            muteButton.setIcon(resizeImageIcon(new ImageIcon("data/volume.png")));
            toggleSfxMute();
        }
    }

    // Helper methods to update mute button
    private static void updateMuteButton(JButton muteButton, ImageIcon soundIcon) {
        if (isMuted()) {
            System.out.println("muted");
            previousVolume = getCurrentVolume();
            setCurrentVolume(0);
            setCurrentVolumeOption("2");
            GameController.getInstance().runCommand("volume");
            muteButton.setIcon(resizeImageIcon(new ImageIcon("data/mute.png")));
        } else {
            setCurrentVolume(previousVolume);
            setCurrentVolumeOption("2");
            GameController.getInstance().runCommand("volume");
            muteButton.setIcon(soundIcon);
        }
    }

    private static void updateSfxMuteButton(JButton muteButton, ImageIcon soundIcon) {
        if (isSfxMuted()) {
            System.out.println("muting sfx");
            System.out.println("muted");
            setPreviousSfxVolume(getCurrentSfxVolume());
            setCurrentSfxVolume(0);
            setCurrentVolumeOption("5");
            GameController.getInstance().runCommand("volume");
            muteButton.setIcon(resizeImageIcon(new ImageIcon("data/mute.png")));
        } else {
            setCurrentSfxVolume(getPreviousSfxVolume());
            setCurrentVolumeOption("4");
            GameController.getInstance().runCommand("volume");
            muteButton.setIcon(soundIcon);
        }
    }

    private static ImageIcon resizeImageIcon(ImageIcon originalIcon) {
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private static JPanel createInputCommandPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MAIN_BACKGROUND_COLOR);

        commandTextField = new JTextField();
        commandTextField.setBackground(MAIN_BACKGROUND_COLOR);
        commandTextField.setForeground(Color.WHITE);

        // placeholder text
        String placeholder = "Enter Command and press enter";
        commandTextField.setText(placeholder);
        commandTextField.setFont(new Font("Arial", Font.BOLD, 14));

        Border paddingBorder = new EmptyBorder(10, 10, 10, 10);
        Border compoundBorder = BorderFactory.createCompoundBorder(
                commandTextField.getBorder(),
                paddingBorder
        );
        // compound border to maintain main border
        commandTextField.setBorder(compoundBorder);

        commandTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                System.out.println("focus gained");
                if (commandTextField.getText().equals(placeholder)) {
                    commandTextField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                System.out.println("focus lost");
                if (commandTextField.getText().isEmpty()) {
                    commandTextField.setText(placeholder);
                }
            }
        });

        commandTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameController.getInstance().runCommand(commandTextField.getText());
                commandTextField.setText("");
            }
        });

        JLabel label = new JLabel("Enter Command");
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
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

    public static void setCurrentVolume(int volume) {
        currentVolume = volume;
    }

    public static int getCurrentVolume() {
        return currentVolume;
    }

    public static JTextPane getGameTextArea() {
        return gameTextArea;
    }

    public static void setGameTextArea(JTextPane gameTextArea) {
        GameWindow.gameTextArea = gameTextArea;
    }

    public static int getCurrentSfxVolume() {
        return currentSfxVolume;
    }

    public static void setCurrentSfxVolume(int currentSfxVolume) {
        GameWindow.currentSfxVolume = currentSfxVolume;
    }

    public static int getPreviousSfxVolume() {
        return previousSfxVolume;
    }

    public static void setPreviousSfxVolume(int previousSfxVolume) {
        GameWindow.previousSfxVolume = previousSfxVolume;
    }

    public static void toggleMute() {
        isMuted = !isMuted;
    }

    public static void toggleSfxMute() {
        isSfxMuted = !isSfxMuted;
    }

    public static boolean isMuted() {
        return isMuted;
    }

    public static boolean isSfxMuted() {
        return isSfxMuted;
    }

    public static void setSfxMuted(boolean isSfxMuted) {
        GameWindow.isSfxMuted = isSfxMuted;
    }

    public static String getCurrentVolumeOption() {
        return currentVolumeOption;
    }

    public static void setCurrentVolumeOption(String currentVolumeOption) {
        GameWindow.currentVolumeOption = currentVolumeOption;
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
