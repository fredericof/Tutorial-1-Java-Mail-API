package com.javaee.email;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.mail.PasswordAuthentication;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailConfig {
	
	public Properties getProperties() throws IOException {
		InputStream inputStream = null;
		try {
			Properties properties = new Properties();
			String propertyFile = "config.properties";
			
			inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);
			
			if (inputStream != null) {
				properties.load(inputStream);
			} else {
				throw new FileNotFoundException("Arquivo '" + propertyFile + "' não encontrado no classpath");
			}
			
			return properties;
			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		
		return null;
	}
	
	public void enviarEmail(final String de, final String senha, String para, String assunto, String corpo) {
		try {
			Authenticator auth = configAuth(de, senha);
			Session session = Session.getInstance(getProperties(), auth);
			MimeMessage message = new MimeMessage(session);
			
			configHeaders(message);
			configContent(para, assunto, corpo, message);
			System.out.println("Mensagem pronta!");
			
			Transport.send(message);
			System.out.println("Email enviado com sucesso!");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void configHeaders(MimeMessage message) throws MessagingException {
		message.addHeader("Content-type", "text/HTML; charset=UTF-8");
		message.addHeader("format", "flowed");
		message.addHeader("Content-Transfer-Encoding", "8bit");
	}

	private void configContent(String para, String assunto, String corpo, MimeMessage message) {
		try {
			message.setFrom(new InternetAddress("example@gmail.com", "Example"));
			message.setReplyTo(InternetAddress.parse("example@gmail.com", false));
			message.setSubject(assunto, "UTF-8");
			message.setText(corpo, "UTF-8");
			message.setSentDate(new Date());
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para, false));
		} catch (Exception e) {
			e.printStackTrace();
}		
	}

	private Authenticator configAuth(final String de, final String senha) {
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(de, senha);
			}
		};
		
		return auth;
	}

}
