package model;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import dto.KorisnikCUDto;
import dto.KorisnikDto;
import dto.KreditnaKarticaCUDto;
import dto.KreditnaKarticaDto;
import dto.TokenRequestDto;
import dto.TokenResponseDto;
import wrapper.KreditnaKarticaPageWrapper;

public class UserOperator {
	private static final String URL = "http://localhost:36250/api";
	private static final String KORISNIK_URL = "/korisnik";
	private static final String ADMIN_URL = "/admin";
	private static final String CC_URL = "/cc";
	
	private static UserOperator instance = null;
	
	private Long id;
	private RestTemplate restTemplate;
	
	private UserOperator() {
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public void registerUser(String email, String sifra, String ime, String prezime, String brojPasosa) {
		//given
		KorisnikCUDto korisnikCreateDto = new KorisnikCUDto(email,
				sifra, ime, prezime, brojPasosa);
		HttpEntity<KorisnikCUDto> request = new HttpEntity<>(korisnikCreateDto);
        //when
        ResponseEntity<TokenResponseDto> response = restTemplate
                .exchange(URL + KORISNIK_URL, HttpMethod.POST, request, TokenResponseDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	id = response.getBody().getId();
        	TokenInterceptor tokenInterceptor = new TokenInterceptor(response.getBody().getToken());
        	restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));
        }
	}
	
	public void signInUser(String email, String sifra) {
		//given
		TokenRequestDto tokenRequest = new TokenRequestDto();
		tokenRequest.setUsername(email);
		tokenRequest.setPassword(sifra);
		HttpEntity<TokenRequestDto> requestLogin = new HttpEntity<>(tokenRequest);
		//when
		ResponseEntity<TokenResponseDto> response = restTemplate
                .exchange(URL + KORISNIK_URL + "/login", HttpMethod.POST, requestLogin, TokenResponseDto.class);
		//then
        if(response.getStatusCode().equals(HttpStatus.OK)) {
        	id = response.getBody().getId();
        	TokenInterceptor tokenInterceptor = new TokenInterceptor(response.getBody().getToken());
        	restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));
        }
	}
	
	public void signInAdmin(String username, String sifra) {
		//given
		TokenRequestDto tokenRequest = new TokenRequestDto();
		tokenRequest.setUsername(username);
		tokenRequest.setPassword(sifra);
		HttpEntity<TokenRequestDto> requestLogin = new HttpEntity<>(tokenRequest);
		//when
		ResponseEntity<TokenResponseDto> response = restTemplate
                .exchange(URL + ADMIN_URL, HttpMethod.POST, requestLogin, TokenResponseDto.class);
		//then
        if(response.getStatusCode().equals(HttpStatus.OK)) {
        	id = response.getBody().getId();
        	TokenInterceptor tokenInterceptor = new TokenInterceptor(response.getBody().getToken());
        	restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));
        }
	}
	
	public void signOut() {
		restTemplate.setInterceptors(Collections.emptyList());
		id = null;
	}
	
	public KorisnikDto getUserInfo() {
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(URL + KORISNIK_URL + "/" + id.toString(), HttpMethod.POST, null, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.OK)) {
        	return response.getBody();
        }
        return null;
	}
	
	public KorisnikDto updateUserInfo(KorisnikCUDto korisnikUpdateDto) {
		//given
		HttpEntity<KorisnikCUDto> request = new HttpEntity<>(korisnikUpdateDto);
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(URL + KORISNIK_URL + "/" + id.toString(), HttpMethod.POST, request, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.OK)) {
        	return response.getBody();
        }
        return null;
	}
	
	public void deleteUser() {
		ResponseEntity<Void> response = restTemplate
                .exchange(URL + KORISNIK_URL + "/" + id.toString(), HttpMethod.DELETE, null, Void.class);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			signOut();
		}
	}
	
	public KreditnaKarticaDto addCC(Long brojKartice, String ime, String prezime, int sigurnosniBroj) {
		//given
		KreditnaKarticaCUDto kreditnaKarticaCreateDto = new KreditnaKarticaCUDto(brojKartice,
				ime, prezime, sigurnosniBroj);
		HttpEntity<KreditnaKarticaCUDto> request = new HttpEntity<>(kreditnaKarticaCreateDto);
		//when
		ResponseEntity<KreditnaKarticaDto> response = restTemplate
				.exchange(URL + KORISNIK_URL + "/" + id.toString() + CC_URL, HttpMethod.POST, request, KreditnaKarticaDto.class);
		//then
		if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	return response.getBody();
        }
		return null;
	}
	
	public KreditnaKarticaPageWrapper displayCC() {
		ResponseEntity<KreditnaKarticaPageWrapper> response = restTemplate
				.exchange(URL + KORISNIK_URL + "/" + id.toString() + CC_URL, HttpMethod.GET, null, KreditnaKarticaPageWrapper.class);
		
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		}
		return null;
	}
	
	public void deleteCC(Long ccId) {
		ResponseEntity<Void> response = restTemplate
                .exchange(URL + KORISNIK_URL + "/" + id.toString() + CC_URL + "/" + ccId.toString(), HttpMethod.DELETE, null, Void.class);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
		}
	}
	
	public static UserOperator getInstance() {
		if(instance == null) {
			instance=new UserOperator();
		}
		return instance;
	}
}
