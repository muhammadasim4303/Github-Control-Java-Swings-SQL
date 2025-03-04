package com.mycompany.githubsyncapp;

import javax.swing.*;

public class ForgotPasswordUI extends JFrame {
    private JTextField emailField, otpField;
    private JPasswordField newPasswordField;
    private JButton sendOtpButton, verifyOtpButton, resetPasswordButton;
    private int generatedOtp = -1;
    private String userEmail = "";

    public ForgotPasswordUI() {
        setTitle("Forgot Password");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 20, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(120, 20, 150, 25);
        add(emailField);

        sendOtpButton = new JButton("Send OTP");
        sendOtpButton.setBounds(20, 60, 120, 25);
        add(sendOtpButton);

        JLabel otpLabel = new JLabel("OTP:");
        otpLabel.setBounds(20, 100, 80, 25);
        add(otpLabel);

        otpField = new JTextField();
        otpField.setBounds(120, 100, 150, 25);
        add(otpField);

        verifyOtpButton = new JButton("Verify OTP");
        verifyOtpButton.setBounds(20, 130, 120, 25);
        add(verifyOtpButton);

        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setBounds(20, 160, 120, 25);
        add(newPasswordLabel);

        newPasswordField = new JPasswordField(); 
        newPasswordField.setBounds(120, 160, 150, 25);
        add(newPasswordField);

        resetPasswordButton = new JButton("Reset Password");
        resetPasswordButton.setBounds(120, 190, 150, 25);
        add(resetPasswordButton);

        // Send OTP
        sendOtpButton.addActionListener(e -> {
            String email = emailField.getText().trim().toLowerCase();  // Trim spaces & lowercase email
        
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your email.");
                return;
            }
        
            generatedOtp = OTPService.sendOTP(email);
            if (generatedOtp != -1) {
                userEmail = email;
                JOptionPane.showMessageDialog(this, "OTP Sent to " + email);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Email!");  // Ensure email exists in DB
            }
        });

        // Verify OTP
        verifyOtpButton.addActionListener(e -> {
            String otpText = otpField.getText().trim();
            if (otpText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter the OTP.");
                return;
            }

            try {
                int enteredOtp = Integer.parseInt(otpText);
                if (enteredOtp == generatedOtp) {
                    JOptionPane.showMessageDialog(this, "OTP Verified! Enter new password.");
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid OTP!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "OTP must be a number.");
            }
        });

        // Reset Password
        resetPasswordButton.addActionListener(e -> {
            char[] passwordChars = newPasswordField.getPassword();
            String newPassword = new String(passwordChars);

            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Password cannot be empty.");
                return;
            }

            if (UserAuth.updatePassword(userEmail, newPassword)) {
                JOptionPane.showMessageDialog(this, "Password Reset Successful!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error resetting password.");
            }
        });

        setVisible(true);
    }
}
