package com.internspace.entities.exchanges;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailerStudent {
	public void send(String mail,String msgt, String sub){
        
        String myEmail = "rayane.limem@gmail.com";
          String password = "passs";
String opponentEmail = mail;
Properties pro = new Properties();
pro.put("mail.smtp.host", "smtp.gmail.com");
pro.put("mail.smtp.starttls.enable", "true");
pro.put("mail.smtp.auth", "true");
pro.put("mail.smtp.port", "587");
Session ss = Session.getInstance(pro, new javax.mail.Authenticator() {
 @Override
 protected PasswordAuthentication getPasswordAuthentication() {
  return new PasswordAuthentication(myEmail, password);
 }
});
try {
 Message msg = new MimeMessage(ss);
 msg.setFrom(new InternetAddress(myEmail));
 msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(opponentEmail));
 msg.setSubject(sub);
 msg.setText(msgt);
 Transport trans = ss.getTransport("smtp");
 trans.send(msg);
 System.out.println("message sent !! ");
} catch (Exception e) {
 System.out.println(e.getMessage());
}
}
}
