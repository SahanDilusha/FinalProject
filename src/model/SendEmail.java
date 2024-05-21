/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.time.Year;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author sahan
 */
public class SendEmail {
    
    private final static String adminMail = "sdilusha34@gmail.com";
    private final static String password = "pjfsvhvtyxoahcrv";

    public static String send(String email,String body,String subject) {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Create session
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adminMail, password);
            }
        });

        try {
            // Create message
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(adminMail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);

            // Construct HTML content
            String htmlContent = body;

            // Set HTML content
            message.setContent(htmlContent, "text/html");

            // Send message
            Transport.send(message);

            return "ok";
        } catch (MessagingException e) {
            e.printStackTrace(); // Handle exceptions properly
            return "error";
        }
    }
    
}
