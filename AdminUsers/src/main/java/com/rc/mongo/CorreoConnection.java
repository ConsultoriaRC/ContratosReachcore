package com.rc.mongo;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CorreoConnection {
	protected static String enviarCorreo(String correoDestinatario, String asunto, String mensaje){
		String username = "kerenmreachcore@gmail.com";
		String password = "Felipe19.";
		String To = correoDestinatario;
		String Subject = asunto; //"tst-Invitación para registrarte";
		//String mensaje = "Da clic en la siguiente liga para registrarte en el portal a provedores SLB: ";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To));
			message.setSubject(Subject);
			message.setText(mensaje);
			Transport.send(message);
			return "Success";
		} 
		catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(enviarCorreo("kmonge@reachcore.com", "tst-Invitación para registrarte", "Da clic en la siguiente liga para registrarte en el portal a provedores SLB: "));
	}
	
}

    