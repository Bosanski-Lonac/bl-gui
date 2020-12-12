package controller;

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
			System.out.println("Uspeh");
		}
	}
}
