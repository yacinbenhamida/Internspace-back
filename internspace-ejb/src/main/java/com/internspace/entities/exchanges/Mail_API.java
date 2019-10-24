package com.internspace.entities.exchanges;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class Mail_API {
	public static void sendMail(String to,String subject,String message) throws MessagingException
    {
        String host="smtp.gmail.com";
        final String user="rayene.limem@esprit.tn";//change accordingly
        final String password="183JFT3203";//change accordingly


        //Get the session object
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.from",user);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        props.setProperty("mail.debug", "true");

        Session session = Session.getInstance(props, null);
        MimeMessage msg = new MimeMessage(session);

        msg.setRecipients(Message.RecipientType.TO, to);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(message);

        Transport transport = session.getTransport("smtp");

        transport.connect(user, password);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();



    }

}
