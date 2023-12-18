package com.game.view.gui;

import com.game.controller.GameController;
import com.game.controller.GameResult;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapGUITest {
    public static final int FRAME_WIDTH = 1400;
    public static final int FRAME_HEIGHT = 800;
    public static final Color MAIN_BACKGROUND_COLOR = new Color(50, 50, 50);
    public static final int PANEL_SPACE = 10;
    
    public static JTextPane gameTextArea;
    public static JTextArea roomInformationArea;
    public static JTextArea playerInformationArea;
    public static JTextArea mapArea;
    public static JTextArea talkTextArea;
    public static JPanel gameTextPanel;
    public static JPanel mapButtonPanel;
    
    static public void createAndShowGUI() {
        JFrame frame = new JFrame("Search For A Killer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        
        // window icon
        ImageIcon image = new ImageIcon("data/logo.png");
        frame.setIconImage(image.getImage());
        
        // Window open in center
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        
        // Main Panel, consist of game text, information panel (player/room info) and map
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(MAIN_BACKGROUND_COLOR);
        
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.fill = GridBagConstraints.BOTH;
        
        int gameTextAreaWidth = (int) (FRAME_WIDTH * 0.6);
        
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setMinimumSize(new Dimension(gameTextAreaWidth, 0));
        containerPanel.setMaximumSize(new Dimension(gameTextAreaWidth, 0));
        containerPanel.setPreferredSize(new Dimension(gameTextAreaWidth, 0));
        
        
        // game text panel
        gameTextArea = createTextPane();
        gameTextPanel = createTextPanel(gameTextArea);
        gameTextPanel.setVisible(true);
        gameTextPanel.setPreferredSize(new Dimension(0, (int) (FRAME_HEIGHT/2.2)));
        gameTextPanel.setBackground(MAIN_BACKGROUND_COLOR);
        containerPanel.add(gameTextPanel);
        mainPanel.add(gameTextPanel, gbcMain);
        
        
        JPanel informationPanel = new JPanel(new GridLayout(1, 2, PANEL_SPACE * 2, 0));
        informationPanel.setBackground(MAIN_BACKGROUND_COLOR);
        
        roomInformationArea = createTextArea();
        JPanel roomInformationPanel = createTextPanel(roomInformationArea);
        informationPanel.add(roomInformationPanel);
        
        playerInformationArea = createTextArea();
        JPanel playerInformationPanel = createTextPanel(playerInformationArea);
        informationPanel.add(playerInformationPanel);
        
        containerPanel.add(informationPanel);
        gbcMain.gridx = 0;
        gbcMain.weightx = 1.0; // use gameTextAreaWidth as it is
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
        
        gameTextPanel.setBorder(BorderFactory.createEmptyBorder(PANEL_SPACE, PANEL_SPACE, PANEL_SPACE, PANEL_SPACE));
        informationPanel.setBorder(BorderFactory.createEmptyBorder(PANEL_SPACE, PANEL_SPACE, PANEL_SPACE, PANEL_SPACE));
        mapPanel.setBorder(BorderFactory.createEmptyBorder(PANEL_SPACE, PANEL_SPACE, PANEL_SPACE, PANEL_SPACE));
        
        frame.add(mainPanel, BorderLayout.CENTER);
        
        frame.setVisible(true);
        
        gameTextArea.setText("TESTING Game");
        playerInformationArea.setText("TESTING Player");
        roomInformationArea.setText("Testing Room");
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
        //textArea.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        textArea.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        
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
        //textPane.setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        textPane.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        
        return textPane;
    }
    
    private static JPanel createTextPanel(JTextComponent textArea) {
        JPanel panel = new JPanel(new BorderLayout());
        //JScrollPane scrollPane = new JScrollPane(textArea);
        //panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(textArea, BorderLayout.CENTER);
        panel.setBackground(MAIN_BACKGROUND_COLOR);
        panel.setOpaque(true);
        return panel;
    }
    
    private static JPanel createMapPanel() {
        JPanel mapPanel = new JPanel();
        int mapWidth = (int)(FRAME_WIDTH * 0.4) - (PANEL_SPACE * 2); // 40% of the total size minus the 2 padding spaces
        int mapHeight = (int)(FRAME_HEIGHT) - (PANEL_SPACE * 4);
    
        mapPanel.setMinimumSize(new Dimension(mapWidth, mapHeight));
        mapPanel.setMaximumSize(new Dimension(mapWidth, mapHeight));
        mapPanel.setPreferredSize(new Dimension(mapWidth, mapHeight));
        mapPanel.setBackground(MAIN_BACKGROUND_COLOR);
    
        JPanel mapButtonPanel = new JPanel(new GridBagLayout());
        mapButtonPanel.setMinimumSize(new Dimension(mapPanel.getWidth() - (PANEL_SPACE * 2), mapPanel.getHeight() - (PANEL_SPACE * 2)));
        mapButtonPanel.setMaximumSize(new Dimension(mapPanel.getWidth() - (PANEL_SPACE * 2), mapPanel.getHeight() - (PANEL_SPACE * 2)));
        mapButtonPanel.setPreferredSize(new Dimension(mapPanel.getWidth() - (PANEL_SPACE * 2), mapPanel.getHeight() - (PANEL_SPACE * 2)));
    
        mapButtonPanel.setOpaque(false);
    
        GridBagConstraints mapConstraints = new GridBagConstraints();
    
        //ImageIcon test = new ImageIcon("data/test.PNG");
    
        for(int row = 0; row < 3; row++) {
            for(int column = 0; column < 3; column++) {
                JButton btn = createMapButton(new Dimension(150, 100));
                mapConstraints.gridx = row;
                mapConstraints.gridy = column;
                mapConstraints.gridwidth = 1;
                mapConstraints.gridheight = 1;
                mapButtonPanel.add(btn, mapConstraints);
            }
        }
    
        mapPanel.add(mapButtonPanel, BorderLayout.CENTER);
        mapButtonPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray, 2, true));
        
        return mapPanel;
    }
    
    private static ImageIcon resizeImageIcon(ImageIcon originalIcon) {
        Image image = originalIcon.getImage();
        Image scaledImage = image.getScaledInstance(10, 10, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
    
    public static JPanel createGridLayoutPanel(int rows, int columns) {
        JPanel panel = new JPanel(new GridLayout(rows, columns)); // 5 rows, 1 column
        panel.setBackground(MAIN_BACKGROUND_COLOR);
        
        return panel;
    }
    
    public static JButton createMapButton(Dimension size) {
        JButton btn = new JButton();
        /*ImageIcon test = new ImageIcon("data/test.PNG");
        btn.setIcon(test);*/
        btn.setPreferredSize(size);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        
        btn.addActionListener((evt) -> {
            String room = evt.getActionCommand();
            System.out.printf("Printing room value: %s\n", room);
            //DO something
        });
        
        return btn;
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
}