package com.mycompany.githubsyncapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainAppUI extends JFrame {
    private JList<String> publicRepoList;
    private JList<String> privateRepoList;
    private DefaultListModel<String> publicModel;
    private DefaultListModel<String> privateModel;

    public MainAppUI() {
        setTitle("GitHub Sync App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        publicModel = new DefaultListModel<>();
        privateModel = new DefaultListModel<>();

        publicRepoList = new JList<>(publicModel);
        privateRepoList = new JList<>(privateModel);

        JButton loginButton = new JButton("Login with GitHub");
        JButton refreshButton = new JButton("Refresh Repos");
        JButton createRepoButton = new JButton("Create New Repo");

        loginButton.addActionListener(e -> GitHubOAuth.loginWithGitHub());
        refreshButton.addActionListener(e -> loadRepositories());
        createRepoButton.addActionListener(e -> {
            String repoName = JOptionPane.showInputDialog("Enter new repo name:");
            if (repoName != null && !repoName.isEmpty()) {
                GitHubAPI.createRepository(repoName, false);
                loadRepositories();
            }
        });

        JPanel panel = new JPanel();
        panel.add(loginButton);
        panel.add(refreshButton);
        panel.add(createRepoButton);

        add(new JLabel("Public Repositories:"), BorderLayout.NORTH);
        add(new JScrollPane(publicRepoList), BorderLayout.CENTER);
        add(new JLabel("Private Repositories:"), BorderLayout.SOUTH);
        add(new JScrollPane(privateRepoList), BorderLayout.SOUTH);
        add(panel, BorderLayout.PAGE_END);

        setVisible(true);
    }

    private void loadRepositories() {
        publicModel.clear();
        privateModel.clear();
        List<String> publicRepos = GitHubAPI.getRepositories(false);
        List<String> privateRepos = GitHubAPI.getRepositories(true);
        publicRepos.forEach(publicModel::addElement);
        privateRepos.forEach(privateModel::addElement);
    }

    public static void main(String[] args) {
        new MainAppUI().setVisible(true);
    }
}
