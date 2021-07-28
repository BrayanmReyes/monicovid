package pe.akiramenai.project.unasam.spring.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSend{
	
	private static boolean send(String destinatario, String asunto, String cuerpo) {
		boolean flag=false;
		String remitente="monicovid.no.reply";
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.user", remitente);
		props.put("mail.smtp.clave", "bgzdagkdujcjxmag");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		
		Session session=Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(remitente));
			message.addRecipients(Message.RecipientType.TO, destinatario);
			message.setSubject(asunto);
			message.setText(cuerpo);
			Transport transport=session.getTransport("smtp");
			transport.connect("smtp.gmail.com", remitente,"bgzdagkdujcjxmag");
			transport.sendMessage(message,message.getAllRecipients());
			transport.close();
			flag=true;
		}catch(MessagingException me) {
			
			me.printStackTrace();
			flag=false;
		}
		return flag;
	}
	public static boolean enviarMail(String destinatario,String asunto, String cuerpo) {
		return send(destinatario,asunto, cuerpo);	
		
	}
}
