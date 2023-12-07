package com.game.view.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class MainFrame extends JFrame implements ActionListener {
    JButton actionButton;
    MainFrame() {
        this.setTitle("Search For A Killer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 700);
        this.setLayout(null); // to position components manually
        this.setVisible(true);
        this.setLocationRelativeTo(null); // display window in centre of screen

        ImageIcon image = new ImageIcon("data/logo.png");
        this.setIconImage(image.getImage());

        this.getContentPane().setBackground(new Color(0x6495ED));

        JLabel label = new JLabel();
        label.setText("SEARCH FOR A KILLER");
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(Color.GREEN);label.setFont(new Font("MV Boli", Font.PLAIN, 30));
        label.setBackground(Color.BLACK);
        label.setOpaque(true);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(0, 0, 1000, 50);

        JPanel bannerPanel = new JPanel();
        bannerPanel.setBackground(Color.RED);
        bannerPanel.setBounds(0, 0, 1000, 60);
        bannerPanel.setLayout(new BorderLayout());
        bannerPanel.add(label);
        this.add(bannerPanel);

        actionButton = new JButton("Play");
        actionButton.setBounds(400, 10, 200, 40);
        actionButton.addActionListener(this);

        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(Color.BLUE);
        actionPanel.setBounds(0, 600, 1000, 60);
        actionPanel.setLayout(null);
        actionPanel.add(actionButton);
        this.add(actionPanel);

        //        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == actionButton) {
            System.out.println("Play Button Clicked!");
        }
    }
}
