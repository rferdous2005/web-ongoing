package com.cirt.web.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.cirt.web.dto.IncidentDto;
import com.cirt.web.entity.Incident;
import com.cirt.web.entity.User;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


@Service
@Slf4j
public class MailService{

	public static String template="""
       			Dear %s<br/><br/>
    			Your account has been successfully created for the %s. This email confirms that your team registration has been received.
				<br/> Please note that you will not be able to access the portal until your team is approved by the admin team.
    			<br/><br/> Once approved, you can log in to the Cyber Drill portal using your credentials at the following link: <br/>
				Link: <a href="%s">%s</a><br/>
    			username: <b>%s</b><br/>
    			password: <b>%s</b>
    			<br/><br/>
    			Thank you<br/>
    			%s Team
				""";

	@Value("${spring.mail.host}")
	private String MAIL_SERVER_HOST;

	@Value("${spring.mail.port}")
	private int MAIL_SERVER_PORT;

	@Value("${spring.mail.username}")
	private String MAIL_SENDER;

	@Value("${spring.mail.password}")
	private String MAIL_PASSWORD;


	private Properties getMailProps(){
		Properties props = new Properties();

		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.enable", "false");
		props.put("mail.smtp.ssl.trust", "*");

		//mapping DNS zimbra220.bcc.gov.bd -> 114.130.54.220
		props.put("mail.smtp.host", MAIL_SERVER_HOST);
		props.put("mail.smtp.port", MAIL_SERVER_PORT);

		props.put("mail.debug", "true");

		return props;

	}

	@Async
	public void sendAccountEmail(User user, String rawPassword) {

		Properties props = getMailProps();
		String body="""
       			Dear %s<br/><br/>
    			Your account has been successfully created. This email confirms that your registration has been received.
				<br/> Please note that you will be able to access the portal with the following username and password.
    			<br/><br/> You are requested to reset your password as soon as you log in for the first time. <br/>
				Link: <a href="https://www.cirt.gov.bd/login">Admin Login Link</a><br/>
    			username: <b>%s</b><br/>
    			password: <b>%s</b>
    			<br/><br/>
    			Thank you<br/>
    			BGD e-GOV CIRT
				""";
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(MAIL_SENDER,MAIL_PASSWORD);
				}
		});
		
		try {

			MimeMessage message = new MimeMessage(session);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getUsername()));

			//message.addRecipient(Message.RecipientType.CC, new InternetAddress("ahasanul.hadi@gmail.com"));

			message.setSubject("CIRT website Admin Account", "UTF-8");
			message.setFrom(new InternetAddress(MAIL_SENDER));
			message.setContent(String.format(body, user.getName(), user.getUsername(), rawPassword), "text/html; charset=utf-8");

			Transport.send(message);

		} catch (Exception e) {
			log.error(e.toString());
		}

	}

	@Async
	public void sendIncidentReceivedEmail(Incident incidentEntity) {
		Properties props = getMailProps();
		String body="""
       			Dear Reporter,<br/><br/>
    			Your incident report has been received by BGD e-GOV CIRT. Incident id: %s <br/>
				Our team will get back to you shortly with an appropriate response.
    			<br/><br/>
    			Thank you<br/>
    			BGD e-GOV CIRT Team
				""";
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(MAIL_SENDER,MAIL_PASSWORD);
				}
		});
		
		try {

			MimeMessage message = new MimeMessage(session);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(incidentEntity.getEmail()));

			//message.addRecipient(Message.RecipientType.CC, new InternetAddress("ahasanul.hadi@gmail.com"));

			message.setSubject("Incident Report Received", "UTF-8");
			message.setFrom(new InternetAddress(MAIL_SENDER));
			message.setContent(String.format(body, incidentEntity.getGeneratedId()), "text/html; charset=utf-8");

			Transport.send(message);

		} catch (Exception e) {
			log.error(e.toString());
		}
		body="""
       			Dear CTI Team,<br/><br/>
    			New Incident Report is received with id: %s <br/>
				Please check CIRT website admin panel Incident Report Section.
				Please login and view the new incident in this <a href="https://www.cirt.gov.bd/admin/incident">Link</a>
    			<br/><br/>
    			Thank you<br/>
    			BGD e-GOV CIRT Team
				""";
		try {

			MimeMessage message = new MimeMessage(session);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress("cti@cirt.gov.bd"));

			//message.addRecipient(Message.RecipientType.CC, new InternetAddress("ahasanul.hadi@gmail.com"));

			message.setSubject("New Incident Report", "UTF-8");
			message.setFrom(new InternetAddress(MAIL_SENDER));
			message.setContent(String.format(body, incidentEntity.getGeneratedId()), "text/html; charset=utf-8");

			Transport.send(message);

		} catch (Exception e) {
			log.error(e.toString());
		}
	}
	
	@Async
	public void sendIncidentResponseDoneEmail(Incident incidentEntity) {
		Properties props = getMailProps();
		String body="""
       			Dear Reporter,<br/><br/>
    			BGD e-GOV CIRT team has responded to your reported Incident with id: %s <br/>
				We hope and expect you a safe journey in the global cyberspace.
    			<br/><br/>
    			Thank you<br/>
    			BGD e-GOV CIRT Team
				""";
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(MAIL_SENDER,MAIL_PASSWORD);
				}
		});
		
		try {

			MimeMessage message = new MimeMessage(session);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(incidentEntity.getEmail()));

			//message.addRecipient(Message.RecipientType.CC, new InternetAddress("ahasanul.hadi@gmail.com"));

			message.setSubject("Incident Reponse Completed", "UTF-8");
			message.setFrom(new InternetAddress(MAIL_SENDER));
			message.setContent(String.format(body, incidentEntity.getGeneratedId()), "text/html; charset=utf-8");

			Transport.send(message);

		} catch (Exception e) {
			log.error(e.toString());
		}
		body="""
       			Dear CTI Team,<br/><br/>
    			Incident Response is done successfully with id: %s <br/>
				Thank you for the prompt and efficient response.
    			<br/><br/>
    			Thank you<br/>
    			BGD e-GOV CIRT Team
				""";
		try {

			MimeMessage message = new MimeMessage(session);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress("cti@cirt.gov.bd"));

			//message.addRecipient(Message.RecipientType.CC, new InternetAddress("ahasanul.hadi@gmail.com"));

			message.setSubject("Incident Response Completed", "UTF-8");
			message.setFrom(new InternetAddress(MAIL_SENDER));
			message.setContent(String.format(body, incidentEntity.getGeneratedId()), "text/html; charset=utf-8");

			Transport.send(message);

		} catch (Exception e) {
			log.error(e.toString());
		}
	}
	
}
