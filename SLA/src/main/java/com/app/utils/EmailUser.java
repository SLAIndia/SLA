package com.app.utils;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
/**
 * @author Jinesh George
 */ 
public class EmailUser {
	private static final Logger logger = Logger.getLogger(EmailUser.class);
	@SuppressWarnings("static-access")
	public static boolean emailUser(String toEmailID, String subject, String msgText) {
    	Properties prop = new Properties();
    	String  username = "";
    	String smtphost = "";
    	boolean status = false;
    	//String toEmailID = "";
        try {
            //load a properties file
            prop.load(EmailUser.class.getClassLoader().getResourceAsStream("config.properties"));
            username = prop.getProperty("username");
            smtphost = prop.getProperty("smtphost");
           // toEmailID = prop.getProperty("login_approval_to_emailid");
        } catch (IOException ex) {
        	logger.info("Error  while geting email password " + ex.getMessage());
        }
        Properties props = new Properties();
        props.put("mail.smtp.host", smtphost);//for yahoo - smtp.mail.yahoo.com
        props.put("mail.stmp.user", username);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.password", "");        
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {            	
                String username = "";
                String password = "";
                Properties prop = new Properties();
                try {
                    //load a properties file
                    prop.load(EmailUser.class.getClassLoader().getResourceAsStream("config.properties"));
                    username = prop.getProperty("username");
                    password = prop.getProperty("password");
                } catch (IOException ex) {
                	logger.info("Error  while geting email password " + ex.getMessage());
                }
                return new PasswordAuthentication(username, password);
            }
        });
        
        String from = username;        
        MimeMessage msg = new MimeMessage(session);
        try {
        	msg.setFrom(new InternetAddress(from));
            msg.setSubject(subject);
            msg.setContent(msgText, "text/html;charset=utf-8");
            Transport transport = session.getTransport("smtp");
            msg.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(toEmailID));
            try{
            	transport.send(msg, msg.getAllRecipients());
            	status = true;
            	//System.out.println("E-mail has been sent successfully....");
            	logger.info("E-mail has been sent successfully.");    
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            	//System.out.println("E-mail sending failed !!!");
            	logger.error("sending failed  "+ e.getMessage());
            }
                    
        } catch (Exception exc) {
        	exc.printStackTrace();
        	logger.info("Error  in email sending. " + exc.getMessage());           
        }
        return status;
    }
}
