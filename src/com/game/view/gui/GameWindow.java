package com.game.view.gui;

import com.formdev.flatlaf.FlatLightLaf;
import com.game.controller.GameController;
import com.game.controller.GameResult;
import com.game.controller.GsonParserController;
import com.game.controller.MainController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;

public class GameWindow {
    public static final int FRAME_WIDTH = 1400;
    public static final int FRAME_HEIGHT = 800;
    private static final int ACTION_PANEL_HEIGHT = 100;
    public static final Color MAIN_BACKGROUND_COLOR = new Color(50, 50, 50);
    public static final Color MAIN_FOREGROUND_COLOR = Color.white;
    public static final int PANEL_SPACE = 10;

    public static JTextPane gameTextArea;
    public static JTextArea roomInformationArea;
    public static JTextArea playerInformationArea;
    public static JTextField commandTextField;
    public static JTextArea helpTextArea;
    public static JTextArea talkTextArea;
    public static JPanel gameTextPanel;
    public static JPanel talkButtonPanel;
    public static JPanel mainTalkPanel;
    private static int currentVolume = 25;
    private static int previousVolume = 25;
    private static int currentSfxVolume = 50;
    private static int previousSfxVolume = 50;
    private static boolean isMuted = false;
    private static boolean isSfxMuted = false;
    private static String currentVolumeOption = "1";
    public static JPanel mapButtonPanel;
    private static boolean initialCommandTextCleared = false;
    private static JFrame frame;

    static void createAndShowGUI() {
        initialCommandTextCleared = false;
        FlatLightLaf.setup();
        UIManager.put("TextComponent.arc", 20);

        frame = new JFrame("Search For A Killer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // window icon
        ImageIcon image = new ImageIcon("data/logo.png");
        frame.setIconImage(image.getImage());

        // Window open in center
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // 1. Game Banner Panel
        JPanel gameBannerPanel = createBannerPanel();
        frame.add(gameBannerPanel, BorderLayout.NORTH);

        // 2. Main Panel, consist of game text, player/room info and map panels
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
        gameTextPanel.setMinimumSize(new Dimension(gameTextAreaWidth, (int) (FRAME_HEIGHT/2.2)));
        gameTextPanel.setMaximumSize(new Dimension(gameTextAreaWidth, (int) (FRAME_HEIGHT/2.2)));
        gameTextPanel.setPreferredSize(new Dimension(gameTextAreaWidth, (int) (FRAME_HEIGHT/2.2)));
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
    
        JPanel mapPanel = createMapPanel();
    
        gbcMain.gridx = 1;
        gbcMain.gridwidth = 1;
        gbcMain.gridheight = 2;
        gbcMain.weightx = 1;
        gbcMain.weighty = 1;
        gbcMain.fill = GridBagConstraints.BOTH;
        mainPanel.add(mapPanel, gbcMain);
        
        int panelSpace = 10;
        gameTextPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));
        informationPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));
        mapPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));

        frame.add(mainPanel, BorderLayout.CENTER);

        // 3. Action Panel, consist of command input, volume control and help/quit buttons
        JPanel actionPanel = new JPanel(new GridBagLayout());
        actionPanel.setBackground(MAIN_BACKGROUND_COLOR);
        actionPanel.setPreferredSize(new Dimension(FRAME_WIDTH, ACTION_PANEL_HEIGHT));

        GridBagConstraints gbcAction = new GridBagConstraints();
        gbcAction.fill = GridBagConstraints.BOTH;

        JPanel inputCommandPanel = createInputCommandPanel();
        gbcAction.gridx = 0;
        gbcAction.weightx = 0.91;
        actionPanel.add(inputCommandPanel, gbcAction);

        JPanel soundAdjustPanel = adjustSoundPanel();
        gbcAction.gridx = 1;
        gbcAction.weightx = 0.139;
        gbcAction.insets = new Insets(0, 100, 0, 0);
        actionPanel.add(soundAdjustPanel, gbcAction);

        JPanel helpPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 30));
        JButton helpButton = new JButton("Help");
        JButton quitButton = new JButton("Quit");

        helpPanel.setBackground(MAIN_BACKGROUND_COLOR);

        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        helpButton.setFont(buttonFont);
        quitButton.setFont(buttonFont);

        helpButton.setBackground(new Color(89, 166, 94));
        helpButton.setForeground(Color.WHITE);
        quitButton.setBackground(new Color(207, 74, 74));
        quitButton.setForeground(Color.WHITE);

        helpPanel.add(helpButton);
        helpPanel.add(quitButton);

        gbcAction.gridx = 2;
        gbcAction.weightx = 0.144;
        gbcAction.insets = new Insets(0, 10, 0, 0);
        actionPanel.add(helpPanel, gbcAction);

        // Add space around the action panel
        inputCommandPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));
        soundAdjustPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));
        helpPanel.setBorder(BorderFactory.createEmptyBorder(panelSpace, panelSpace, panelSpace, panelSpace));

        frame.add(actionPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // Add ActionListener for the Help button
        helpButton.addActionListener(e -> {
            // Display the help pop-up
            showHelpPopup();
        });

        quitButton.addActionListener(e -> {
            int option = JOptionPane.showOptionDialog(frame, "Are you sure you want to quit?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
            if(option == 0) {
                // Close the current window and terminate the process
                frame.dispose();
                System.exit(0);
            }
        });

        // focus command text field when window loads
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent windowEvent) {
                commandTextField.requestFocusInWindow();
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
        UIManager.put("ScrollBar.track", Color.WHITE);
        UIManager.put("ScrollBar.thumb", MAIN_BACKGROUND_COLOR);
        UIManager.put("ScrollBar.trackArc", 20);
        UIManager.put("ScrollBar.thumbArc", 25);
        JLabel currentVolumeLabel = new JLabel("Volume: " + getCurrentVolume());
        currentVolumeLabel.setHorizontalAlignment(JLabel.CENTER);
        currentVolumeLabel.setForeground(Color.WHITE);
        
        JScrollBar volumeScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, getCurrentVolume(), 10, 0, 110);
        volumeScrollBar.setBackground(MAIN_BACKGROUND_COLOR);
        volumeScrollBar.setForeground(Color.WHITE);
        volumeScrollBar.setPreferredSize(new Dimension(5, 5));
        
        ImageIcon soundIcon = resizeImageIcon(new ImageIcon("data/volume.png"), 10, 10);
        JButton muteButton = new JButton(soundIcon);
        muteButton.setBackground(Color.WHITE);
        muteButton.addActionListener(e -> {
            toggleMute();
            // update mute button icon
            updateMuteButton(muteButton, soundIcon);
        });
        
        volumeScrollBar.addAdjustmentListener(e -> {
            int volume = e.getValue();
            updateVolumeControls(volume, currentVolumeLabel,muteButton);
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
        sfxMuteButton.addActionListener(e -> {
            toggleSfxMute();
            updateSfxMuteButton(sfxMuteButton, soundIcon);
        });
        
        sfxVolumeScrollBar.addAdjustmentListener(e -> {
            int volume = e.getValue();
            updateSfxVolumeControls(volume, sfxVolumeLabel, sfxMuteButton);
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
        scrollBarConstraints.insets = new Insets(0, 0, 0, 5);

        // constraints for mute button
        GridBagConstraints muteButtonConstraints = new GridBagConstraints();
        muteButtonConstraints.gridx = 1;
        muteButtonConstraints.gridy = 0;
        muteButtonConstraints.weightx = 0.05;
        muteButtonConstraints.fill = GridBagConstraints.BOTH;

        panel.add(scrollBar, scrollBarConstraints);
        panel.add(muteButton, muteButtonConstraints);

        return panel;
    }

    // Helper methods to update volume controls
    private static void updateVolumeControls(int volume, JLabel volumeLabel, JButton muteButton) {
        volumeLabel.setText("Volume: " + volume);
        setCurrentVolumeOption("2");
        setCurrentVolume(volume);
        GameController.getInstance().runCommand("volume");

        // check if muted when change volume and toggle mute
        if (isMuted()) {
            muteButton.setIcon(resizeImageIcon(new ImageIcon("data/volume.png"), 10, 10));
            toggleMute();
        }
    }

    private static void updateSfxVolumeControls(int volume, JLabel volumeLabel, JButton muteButton) {
        volumeLabel.setText("SFX Volume: " + volume);
        setCurrentVolumeOption("6");
        setCurrentSfxVolume(volume);
        GameController.getInstance().runCommand("volume");

        if (isSfxMuted()) {
            muteButton.setIcon(resizeImageIcon(new ImageIcon("data/volume.png"), 10, 10));
            toggleSfxMute();
        }
    }

    // Helper methods to update mute button
    private static void updateMuteButton(JButton muteButton, ImageIcon soundIcon) {
        if (isMuted()) {
            previousVolume = getCurrentVolume();
            setCurrentVolume(0);
            setCurrentVolumeOption("2");
            GameController.getInstance().runCommand("volume");
            muteButton.setIcon(resizeImageIcon(new ImageIcon("data/mute.png"), 10, 10));
        } else {
            setCurrentVolume(previousVolume);
            setCurrentVolumeOption("2");
            GameController.getInstance().runCommand("volume");
            muteButton.setIcon(soundIcon);
        }
    }

    private static void updateSfxMuteButton(JButton muteButton, ImageIcon soundIcon) {
        if (isSfxMuted()) {
            setPreviousSfxVolume(getCurrentSfxVolume());
            setCurrentSfxVolume(0);
            setCurrentVolumeOption("5");
            GameController.getInstance().runCommand("volume");
            muteButton.setIcon(resizeImageIcon(new ImageIcon("data/mute.png"), 10, 10));
        } else {
            setCurrentSfxVolume(getPreviousSfxVolume());
            setCurrentVolumeOption("4");
            GameController.getInstance().runCommand("volume");
            muteButton.setIcon(soundIcon);
        }
    }
    
    private static JPanel createMapPanel() {
        JPanel mapPanel = new JPanel();
        int mapWidth = (int)(FRAME_WIDTH * 0.4) - (PANEL_SPACE * 2); // 40% of the total size minus the 2 padding spaces
        int mapHeight = (int)((FRAME_HEIGHT) - (PANEL_SPACE * 4) - (ACTION_PANEL_HEIGHT * 1.5));
        
        mapPanel.setMinimumSize(new Dimension(mapWidth, mapHeight));
        mapPanel.setMaximumSize(new Dimension(mapWidth, mapHeight));
        mapPanel.setPreferredSize(new Dimension(mapWidth, mapHeight));
        mapPanel.setBackground(MAIN_BACKGROUND_COLOR);
    
        mapButtonPanel = new JPanel(new GridBagLayout());
        mapButtonPanel.setMinimumSize(new Dimension(mapWidth - (PANEL_SPACE * 2), mapHeight - (PANEL_SPACE * 2)));
        mapButtonPanel.setMaximumSize(new Dimension(mapWidth - (PANEL_SPACE * 2), mapHeight - (PANEL_SPACE * 2)));
        mapButtonPanel.setPreferredSize(new Dimension(mapWidth - (PANEL_SPACE * 2), mapHeight - (PANEL_SPACE * 2)));
        
        mapButtonPanel.setOpaque(false);
        
        GridBagConstraints mapConstraints = new GridBagConstraints();
        
        for(int row = 0; row < 3; row++) {
            for(int column = 0; column < 3; column++) {
                JButton btn = createMapButton(new Dimension(150, 100));
                mapConstraints.gridx = column;
                mapConstraints.gridy = row;
                mapConstraints.gridwidth = 1;
                mapConstraints.gridheight = 1;
                // This adds the btn to the panel
                mapButtonPanel.add(btn, mapConstraints);
                btn.setActionCommand("" + row+column);
            }
        }
        
        mapPanel.add(mapButtonPanel, BorderLayout.CENTER);
        mapButtonPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        
        return mapPanel;
    }

    public static ImageIcon resizeImageIcon(ImageIcon originalIcon, int width, int height) {
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private static JPanel createInputCommandPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(MAIN_BACKGROUND_COLOR);

        commandTextField = new JTextField();
        commandTextField.setBackground(MAIN_BACKGROUND_COLOR);
        commandTextField.setForeground(Color.WHITE);

        // placeholder text
        String placeholder = "Press Enter to Continue";
        commandTextField.setText(placeholder);
        commandTextField.setFont(new Font("Arial", Font.BOLD, 16));

        Border paddingBorder = new EmptyBorder(10, 10, 10, 10);
        Border compoundBorder = BorderFactory.createCompoundBorder(
                commandTextField.getBorder(),
                paddingBorder
        );
        // compound border to maintain main border for text field component
        commandTextField.setBorder(compoundBorder);

        // command text field focus handling
        commandTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!initialCommandTextCleared && commandTextField.getText().equals(placeholder) && !commandTextField.hasFocus()) {
                    commandTextField.setText(placeholder);
                    initialCommandTextCleared = true;
                }
            }

            @Override
            public void focusLost(FocusEvent e) {

            }

        });

        // listen for enter and clear text only if clicked for the first time
        commandTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && !initialCommandTextCleared) {
                    // Clear the text when Initial Enter is pressed
                    commandTextField.setText("");
                    initialCommandTextCleared = true;
                }
            }
        });

        commandTextField.addActionListener(e -> {
            GameController.getInstance().runCommand(commandTextField.getText());
            commandTextField.setText("");
        });

        JLabel label = new JLabel("Command");
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 0));
        panel.add(label, BorderLayout.NORTH);
        panel.add(commandTextField, BorderLayout.CENTER);
        return panel;
    }

    private static void showHelpPopup() {
        JFrame helpFrame = new JFrame("Help");
        helpFrame.setSize(525, 300);
        helpFrame.setLocationRelativeTo(frame);
        helpFrame.setResizable(false);

        ImageIcon image = new ImageIcon("data/logo.png");
        helpFrame.setIconImage(image.getImage());

        helpTextArea = new JTextArea();
        helpTextArea.setEditable(false);
        helpTextArea.setBackground(MAIN_BACKGROUND_COLOR);
        helpTextArea.setForeground(Color.WHITE);
        helpTextArea.setFont(new Font("Ariel", Font.PLAIN, 14));

        int padding = 10;
        helpTextArea.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));

        // Set text content in help window
        GameController.getInstance().displayHelpMessage();

        JScrollPane scrollPane = new JScrollPane(helpTextArea);

        JPanel helpPanel = new JPanel(new BorderLayout());
        helpPanel.setBackground(MAIN_BACKGROUND_COLOR);
        helpPanel.add(scrollPane, BorderLayout.CENTER);

        helpFrame.add(helpPanel);

        helpFrame.setVisible(true);
    }
    
    public static JButton createMapButton(Dimension size) {
        JButton btn = new JButton();
        btn.setPreferredSize(size);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        
        btn.setLayout(new BorderLayout());
        
        JLabel playerLabel = new JLabel();
        ImageIcon playerIcon = new ImageIcon("data/logo.png");
        playerIcon = resizeImageIcon(playerIcon, 32, 32);
        playerLabel.setIcon(playerIcon);
        playerLabel.setVisible(false);
        
        btn.add(playerLabel, BorderLayout.SOUTH);
        
        btn.addActionListener((evt) -> {
            String room = evt.getActionCommand();
            GameController.getInstance().runCommand(String.format("go %s", room));
        });
        
        return btn;
    }

    public static JButton createButtonWithId(String label, int id) {
        JButton button = new JButton(label);
        button.setActionCommand(String.valueOf(id));

        button.addActionListener(e -> {
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
                    StringBuilder resultText = new StringBuilder();
                    if (result.equals(GameResult.WIN)) {
                        resultText.append("You WON the game\n");
                    } else {
                        resultText.append("You LOST the game\n");
                    }
                    
                    resultText.append("Thanks for playing.\n Would you like to play again?");
                    int option = JOptionPane.showOptionDialog(frame, resultText.toString(), "Result", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if(option == 0) {
                        //We want to play again, so start new title screen?
                        frame.dispose();
                        MainController.startNewGame();
                        return;
                    } else {
                        // No we don't want to play again, quit.
                        frame.dispose();
                        System.exit(0);
                    }
                }

                // reset followup questions and result after answering followup question
                gameController.conversationController.followedUpQuestion = false;
                gameController.conversationController.result = -1;
            }
            gameController.conversationController.run(GameController.getInstance().player, GameController.getInstance().character);

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
}
