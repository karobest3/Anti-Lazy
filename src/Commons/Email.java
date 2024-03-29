/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commons;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Doan Thanh Nhan
 */
public class Email {

    /**
     *
     * @param smtpServer : Ex Gmail : smtp.gmail.com
     * @param to : Receiver email address
     * @param from : Sender email address
     * @param psw: Password of sender email
     * @param subject: Subject of email (Title)
     * @param body : Email content
     * @throws Exception
     */
    public static void send_Email_Without_Attach(String to, String subject, String body) {
        try {
            Properties props = System.getProperties();
            props.put("mail.smtp.host", ConnectDB.SMTP_SERVER);
            props.put("mail.smtp.port", ConnectDB.SMTP_PORT);
            props.put("mail.smtp.starttls.enable", "true");
            final String login = ConnectDB.EMAIL;
            final String pwd = ConnectDB.EMAIL_PASS;
            Authenticator pa = null;
            if (login != null && pwd != null) {
                props.put("mail.smtp.auth", "true");
                pa = new Authenticator() {
                    @Override
                    public PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(login, pwd);
                    }
                };
            }//else: no authentication
            Session session = Session.getInstance(props, pa);
// — Create a new message –
            Message msg = new MimeMessage(session);

            // — Set the FROM and TO fields –
            msg.setFrom(new InternetAddress(ConnectDB.EMAIL));

            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
                    to, false));

// — Set the subject and body text –
            msg.setSubject(subject);
            msg.setText(body);//Để gởi nội dung dạng utf-8 các bạn dùng msg.setContent(body, "text/html; charset=UTF-8");
// — Set some other header information –
            msg.setHeader("X-Mailer", "LOTONtechEmail");
            msg.setSentDate(new Date());
            msg.saveChanges();
// — Send the message –
            Transport.send(msg);
            System.out.println("Mail da duoc gui");
        } catch (MessagingException ex) {
            Logger.getLogger(Email.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param sendTo : Receiver email address
     * @param subject : Subject of email (Title)
     * @param body : Email content
     * @param fileAttach : File need to attach
     * @throws AddressException
     * @throws MessagingException
     */
    public static void sendEmail_With_Attach(String sendTo, String subject, String body, String fileAttach) throws AddressException, MessagingException {
        // 1) get the session object
        Properties props = System.getProperties();
        props.put("mail.smtp.host", ConnectDB.SMTP_SERVER);
        props.put("mail.smtp.port", ConnectDB.SMTP_PORT);
        props.put("mail.smtp.starttls.enable", "true");
        final String login = ConnectDB.EMAIL;
        final String pwd = ConnectDB.EMAIL_PASS;
        Authenticator pa = null;
        if (login != null && pwd != null) {
            props.put("mail.smtp.auth", "true");
            pa = new Authenticator() {

                @Override
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(login, pwd);
                }
            };
        }//else: no authentication
        Session session = Session.getInstance(props, pa);
        // 2) compose message
        // — Create a new message –
        Message msg = new MimeMessage(session);
// — Set the FROM and TO fields –
        msg.setFrom(new InternetAddress(ConnectDB.EMAIL));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sendTo, false));
        // — Set the subject and body text –
        msg.setSubject(subject);
//        msg.setText(body);//Để gởi nội dung dạng utf-8 các bạn dùng msg.setContent(body, "text/html; charset=UTF-8");
// — Set some other header information –
        msg.setHeader("X-Mailer", "LOTONtechEmail");
        msg.setSentDate(new Date());
        // 3) create MimeBodyPart object and set your message text
        BodyPart messageBodyPart1 = new MimeBodyPart();
        messageBodyPart1.setText(body);
        // 4) create new MimeBodyPart object and set DataHandler object to this object
        MimeBodyPart messageBodyPart2 = new MimeBodyPart();

        String filename = fileAttach;
        DataSource source = new FileDataSource(filename);
        messageBodyPart2.setDataHandler(new DataHandler(source));
        messageBodyPart2.setFileName(filename);
        // 5) create Multipart object and add MimeBodyPart objects to this object
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart1);
        multipart.addBodyPart(messageBodyPart2);
        // 6) set the multiplart object to the message object
        msg.setContent(multipart);
        msg.saveChanges();
        // 7) send message
        Transport.send(msg);
        System.out.println("Message sent successfully");
    }

    public static void main(String[] args) throws Exception {

    }
}
