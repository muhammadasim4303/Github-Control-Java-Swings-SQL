package com.mycompany.githubsyncapp;

import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;

public class OTPService {
    public static int sendOTP(String email) {
        if (!UserAuth.emailExists(email)) return -1; // Check if email exists in DB

        int otp = new Random().nextInt(900000) + 100000;
        String from = "email@gmail.com"; //your email using for sending email
        String password = "password"; //your password for the email

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Password Reset OTP");
            message.setText("Your OTP is: " + otp);
            Transport.send(message);
            return otp;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
