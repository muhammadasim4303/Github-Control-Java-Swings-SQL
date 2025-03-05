package com.mycompany.githubsyncapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class MainAppUI extends JFrame {
    private JPanel publicRepoPanel, privateRepoPanel;

    public MainAppUI() {
        setTitle("GitHub Sync App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton loginButton = new JButton("Login with GitHub");
        JButton refreshButton = new JButton("Refresh Repos");
        JButton createRepoButton = new JButton("Create New Repo");

        loginButton.addActionListener(e -> GitHubOAuth.loginWithGitHub());
        refreshButton.addActionListener(e -> loadRepositories());
        createRepoButton.addActionListener(e -> {
            String repoName = JOptionPane.showInputDialog("Enter new repo name:");
            if (repoName != null && !repoName.trim().isEmpty()) {
                int choice = JOptionPane.showConfirmDialog(null, "Do you want this repository to be private?", "Repo Privacy", JOptionPane.YES_NO_OPTION);
                boolean isPrivate = (choice == JOptionPane.YES_OPTION);
                GitHubAPI.createRepository(repoName, isPrivate);
                loadRepositories();
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.add(loginButton);
        topPanel.add(refreshButton);
        topPanel.add(createRepoButton);
        add(topPanel, BorderLayout.NORTH);

        publicRepoPanel = new JPanel();
        publicRepoPanel.setLayout(new BoxLayout(publicRepoPanel, BoxLayout.Y_AXIS));
        privateRepoPanel = new JPanel();
        privateRepoPanel.setLayout(new BoxLayout(privateRepoPanel, BoxLayout.Y_AXIS));

        JScrollPane publicScroll = new JScrollPane(publicRepoPanel);
        JScrollPane privateScroll = new JScrollPane(privateRepoPanel);

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(publicScroll);
        mainPanel.add(privateScroll);

        add(mainPanel, BorderLayout.CENTER);

        loadRepositories();
        setVisible(true);
    }

    private void loadRepositories() {
        publicRepoPanel.removeAll();
        privateRepoPanel.removeAll();

        List<String> publicRepos = GitHubAPI.getRepositories(false);
        List<String> privateRepos = GitHubAPI.getRepositories(true);

        addRepoButtons(publicRepoPanel, publicRepos, false);
        addRepoButtons(privateRepoPanel, privateRepos, true);

        publicRepoPanel.revalidate();
        publicRepoPanel.repaint();
        privateRepoPanel.revalidate();
        privateRepoPanel.repaint();
    }

    private void addRepoButtons(JPanel panel, List<String> repos, boolean isPrivate) {
        for (String repoName : repos) {
            JPanel repoPanel = new JPanel();
            repoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel repoLabel = new JLabel(repoName + (isPrivate ? " (Private)" : " (Public)"));
            JButton linkButton = new JButton("Link Local Repo");

            linkButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    linkLocalRepo(repoName);
                }
            });

            repoPanel.add(repoLabel);
            repoPanel.add(linkButton);
            panel.add(repoPanel);
        }
    }

    private void linkLocalRepo(String repoName) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Local Folder to Link");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = fileChooser.getSelectedFile();
            GitHandler.linkLocalToRemote(selectedFolder.getAbsolutePath(), repoName);
        }
    }

    public static void main(String[] args) {
        new MainAppUI();
    }
}
