package com.mycompany.githubsyncapp;

import javax.swing.*;

public class RegisterUI extends JFrame {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    public RegisterUI() {
        setTitle("Register");
        setSize(300, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 20, 80, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 20, 150, 25);
        add(usernameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 60, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 60, 150, 25);
        add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 100, 80, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 100, 150, 25);
        add(passwordField);

        registerButton = new JButton("Register");
        registerButton.setBounds(20, 150, 100, 25);
        add(registerButton);

        backButton = new JButton("Back");
        backButton.setBounds(130, 150, 100, 25);
        add(backButton);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (UserAuth.registerUser(username, email, password)) {
                JOptionPane.showMessageDialog(this, "Registration Successful! Please Login.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed. Try Again.");
            }
        });

        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }
}
