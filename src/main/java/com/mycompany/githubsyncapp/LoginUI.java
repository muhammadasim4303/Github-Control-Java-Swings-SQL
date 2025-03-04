package com.mycompany.githubsyncapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginUI extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, forgotPasswordButton;
    private JButton loginWithGitHubButton;

    public LoginUI() {
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 20, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 20, 150, 25);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 50, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 150, 25);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(20, 90, 80, 25);
        add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setBounds(110, 90, 100, 25);
        add(registerButton);

        forgotPasswordButton = new JButton("Forgot Password?");
        forgotPasswordButton.setBounds(20, 120, 190, 25);
        add(forgotPasswordButton);

        loginWithGitHubButton = new JButton("Login with GitHub");
        forgotPasswordButton.setBounds(20, 150, 190, 25);
        loginWithGitHubButton.addActionListener(e -> openGitHubLogin());

        add(loginWithGitHubButton);

        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (UserAuth.loginUser(email, password)) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new MainAppUI();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Credentials");
            }
        });

        registerButton.addActionListener(e -> new RegisterUI());
        forgotPasswordButton.addActionListener(e -> new ForgotPasswordUI());

        setVisible(true);
    }
}
