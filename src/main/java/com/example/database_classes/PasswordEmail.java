package com.example.database_classes;

import com.example.security.PasswordSystemManager;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by axelkvistad on 29/01/17.
 */
public class PasswordEmail {
    private static final String FROM_ADDRESS = "noreply.minvakt@gmail.com";
    private static final String FROM_PASSWORD = "aF9SoPaP";
    private static final String SUBJECT = "MinVakt - tilbakestilling av passord";
    private String newPassword = "";

    public void setNewPassword() {
        newPassword = PasswordSystemManager.generatePassword();
    }

    public String getNewPassword() {
        return newPassword;
    }




    public void sendMail(String toAddress) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String content = "Vi har mottatt en forespørsel om å tilbakestille passordet ditt på MinVakt.\n" +
                "Ditt nye passord er: " + newPassword;

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_ADDRESS, FROM_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_ADDRESS));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
            message.setSubject(SUBJECT);
            message.setText(content);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
