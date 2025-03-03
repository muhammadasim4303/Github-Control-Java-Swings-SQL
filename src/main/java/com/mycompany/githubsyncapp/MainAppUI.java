package com.mycompany.githubsyncapp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainAppUI extends JFrame {
    private JTextField repoPathField, commitMessageField;
    private JButton selectRepoButton, commitButton, pushButton;

    private GitHandler gitHandler;

    public MainAppUI() {
        setTitle("GitHub Sync App");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel repoLabel = new JLabel("Repository Path:");
        repoLabel.setBounds(20, 20, 150, 25);
        add(repoLabel);

        repoPathField = new JTextField();
        repoPathField.setBounds(150, 20, 200, 25);
        add(repoPathField);

        selectRepoButton = new JButton("Select");
        selectRepoButton.setBounds(20, 60, 100, 25);
        add(selectRepoButton);

        JLabel commitLabel = new JLabel("Commit Message:");
        commitLabel.setBounds(20, 100, 150, 25);
        add(commitLabel);

        commitMessageField = new JTextField();
        commitMessageField.setBounds(150, 100, 200, 25);
        add(commitMessageField);

        commitButton = new JButton("Commit");
        commitButton.setBounds(20, 140, 100, 25);
        add(commitButton);

        pushButton = new JButton("Push");
        pushButton.setBounds(150, 140, 100, 25);
        add(pushButton);

        selectRepoButton.addActionListener(e -> {
            String path = repoPathField.getText();
            gitHandler = new GitHandler(path);
            JOptionPane.showMessageDialog(this, "Repository Selected!");
        });

        commitButton.addActionListener(e -> {
            if (gitHandler != null) {
                String message = commitMessageField.getText();
                gitHandler.commitChanges(message);
                JOptionPane.showMessageDialog(this, "Changes Committed!");
            } else {
                JOptionPane.showMessageDialog(this, "Select a repository first!");
            }
        });

        pushButton.addActionListener(e -> {
            if (gitHandler != null) {
                gitHandler.pushChanges();
                JOptionPane.showMessageDialog(this, "Changes Pushed!");
            } else {
                JOptionPane.showMessageDialog(this, "Select a repository first!");
            }
        });

        setVisible(true);
    }
}
