package com.javaee.email;

public class EnviarEmail {
	public static void main(String[] args) {
		final String fromEmail = "fredericofbh@gmail.com";
		final String password = "****";
		final String toEmail = "fredericomgbh@gmail.com";
		
		System.out.println("Inicializando envio...");
		
		EmailConfig config = new EmailConfig();
		
		config.enviarEmail(fromEmail, password, toEmail, "Tutorial 1 - Email", "Este é"
				+ " o tutorial de enviar email por meio de um servidor SMTP.");
	}
}
