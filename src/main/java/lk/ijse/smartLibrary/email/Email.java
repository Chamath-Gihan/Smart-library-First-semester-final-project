package lk.ijse.smartLibrary.email;


import java.sql.*;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

    private static final String URL = "jdbc:mysql://localhost:3306/SmartLibrary";
    private static final Properties props = new Properties();

    static {
        props.setProperty("user", "root");
        props.setProperty("password", "Chamath2005.");
    }

    public static String getOtp(){
        String email = null;
        try (Connection con = DriverManager.getConnection(URL, props)) {
            String sql = "SELECT Librarian_Email FROM Librarian WHERE Librarian_Id = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, 1); // Set the librarian ID to 1
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                email = rs.getString("Librarian_Email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return email;
    }

    public static void firstOtp(int randomNumber) {

        String to = getOtp();
        String from = "tashhtechnologies@gmail.com";
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("tashhtechnologies@gmail.com", "mafmowhxsnejxbwx");

            }

        });

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("Smart Library");

            message.setText("6 digit OTP is : "+randomNumber);
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}

