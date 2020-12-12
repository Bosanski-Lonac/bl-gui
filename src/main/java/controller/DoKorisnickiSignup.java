package controller;

import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import dto.KorisnikCUDto;
import dto.KorisnikDto;
import gui.KorisnickiSignupDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DoKorisnickiSignup implements EventHandler<ActionEvent> {
	private static final String URL = "http://localhost:36250/api";
	private static final String KORISNIK_URL = "/korisnik";
	
	private KorisnickiSignupDialog dialog;
	
	public DoKorisnickiSignup(KorisnickiSignupDialog dialog) {
		this.dialog=dialog;
	}

	@Override
	public void handle(ActionEvent event) {
		RestTemplate restTemplate = new RestTemplate();
		KorisnikCUDto korisnikCreateDto=new KorisnikCUDto(dialog.getEmail(), dialog.getPassword(), dialog.getBrojPasosa(), dialog.getIme(), dialog.getPrezime());
		HttpEntity<KorisnikCUDto> request = new HttpEntity<>(korisnikCreateDto);
		ResponseEntity<KorisnikDto> response = restTemplate.exchange(URL + KORISNIK_URL, HttpMethod.POST, request, KorisnikDto.class);
		if(response.getStatusCode().equals(HttpStatus.CREATED)) {
			String to=dialog.getEmail();
			String from="bosanskilonacRN@gmail.com";
			Properties properties=new Properties();
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", 587);
			Session session=Session.getDefaultInstance(properties, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("bosanskilonacRN@gmail.com", "bosanskilonac");
				}
			});
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("Potvrda o registraciji");
				message.setText("Uspesno ste se registrovali!");
				Transport.send(message);
		        System.out.println("Sent message successfully....");
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}
}
