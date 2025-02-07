package model;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import dto.KorisnikCreateDto;
import dto.KorisnikDto;
import dto.KorisnikUpdateDto;
import dto.KreditnaKarticaCUDto;
import dto.KreditnaKarticaDto;
import dto.TokenRequestDto;
import dto.TokenResponseDto;
import utility.TokenInterceptor;
import utility.BLURL;
import wrapper.KreditnaKarticaPageWrapper;

public class UserOperator {
	private static UserOperator instance = null;
	
	private KorisnikDto korisnikDto;
	private RestTemplate restTemplate;
	
	private UserOperator() {
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public KorisnikDto registerUser(String email, String sifra, String ime, String prezime, String brojPasosa) {
		//given
		KorisnikCreateDto korisnikCreateDto = new KorisnikCreateDto(email,
				sifra, ime, prezime, brojPasosa);
		HttpEntity<KorisnikCreateDto> request = new HttpEntity<>(korisnikCreateDto);
        //when
        ResponseEntity<TokenResponseDto> response = restTemplate
                .exchange(BLURL.getGatewayUserCreateURL(), HttpMethod.POST, request, TokenResponseDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	korisnikDto = response.getBody().getKorisnikDto();
        	TokenInterceptor tokenInterceptor = new TokenInterceptor(response.getBody().getToken());
        	restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));
        	return korisnikDto;
        } else {
        	throw new HttpClientErrorException(response.getStatusCode());
        }
	}
	
	public KorisnikDto signInUser(String email, String sifra) {
		//given
		TokenRequestDto tokenRequest = new TokenRequestDto();
		tokenRequest.setUsername(email);
		tokenRequest.setPassword(sifra);
		HttpEntity<TokenRequestDto> requestLogin = new HttpEntity<>(tokenRequest);
		//when
		ResponseEntity<TokenResponseDto> response = restTemplate
                .exchange(BLURL.getGatewayUserLoginURL(), HttpMethod.POST, requestLogin, TokenResponseDto.class);
		//then
        if(response.getStatusCode().equals(HttpStatus.OK)) {
        	korisnikDto = response.getBody().getKorisnikDto();
        	TokenInterceptor tokenInterceptor = new TokenInterceptor(response.getBody().getToken());
        	restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));
        	return korisnikDto;
        } else {
        	throw new HttpClientErrorException(response.getStatusCode());
        }
	}
	
	public KorisnikDto signInAdmin(String username, String sifra) {
		//given
		TokenRequestDto tokenRequest = new TokenRequestDto();
		tokenRequest.setUsername(username);
		tokenRequest.setPassword(sifra);
		HttpEntity<TokenRequestDto> requestLogin = new HttpEntity<>(tokenRequest);
		//when
		ResponseEntity<TokenResponseDto> response = restTemplate
                .exchange(BLURL.getGatewayAdminURL(), HttpMethod.POST, requestLogin, TokenResponseDto.class);
		//then
        if(response.getStatusCode().equals(HttpStatus.OK)) {
        	korisnikDto = response.getBody().getKorisnikDto();
        	TokenInterceptor tokenInterceptor = new TokenInterceptor(response.getBody().getToken());
        	restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));
        	return korisnikDto;
        } else {
        	throw new HttpClientErrorException(response.getStatusCode());
        }
	}
	
	public void signOut() {
		restTemplate.setInterceptors(Collections.emptyList());
		korisnikDto = null;
	}
	
	public KorisnikDto getUserInfo(boolean refresh) {
		if(!refresh) {
			return korisnikDto;
		}
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(BLURL.getGatewayUserOperationURL(korisnikDto.getId()), HttpMethod.GET, null, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.OK)) {
        	korisnikDto = response.getBody();
        	return korisnikDto;
        } else {
        	throw new HttpClientErrorException(response.getStatusCode());
        }
	}
	
	public KorisnikDto updateUserInfo(KorisnikUpdateDto korisnikUpdateDto) {
		//given
		HttpEntity<KorisnikUpdateDto> request = new HttpEntity<>(korisnikUpdateDto);
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(BLURL.getGatewayUserOperationURL(korisnikDto.getId()), HttpMethod.PUT, request, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.OK)) {
        	korisnikDto = response.getBody();
        	return korisnikDto;
        } else {
        	throw new HttpClientErrorException(response.getStatusCode());
        }
	}
	
	public void deleteUser() {
		ResponseEntity<Void> response = restTemplate
                .exchange(BLURL.getGatewayUserOperationURL(korisnikDto.getId()), HttpMethod.DELETE, null, Void.class);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			signOut();
		} else {
        	throw new HttpClientErrorException(response.getStatusCode());
        }
	}
	
	public KreditnaKarticaDto addCC(Long brojKartice, String ime, String prezime, int sigurnosniBroj) {
		//given
		KreditnaKarticaCUDto kreditnaKarticaCreateDto = new KreditnaKarticaCUDto(brojKartice,
				ime, prezime, sigurnosniBroj);
		HttpEntity<KreditnaKarticaCUDto> request = new HttpEntity<>(kreditnaKarticaCreateDto);
		//when
		ResponseEntity<KreditnaKarticaDto> response = restTemplate
				.exchange(BLURL.getGatewayCCOperationURL(korisnikDto.getId()), HttpMethod.POST, request, KreditnaKarticaDto.class);
		//then
		if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	return response.getBody();
        } else {
        	throw new HttpClientErrorException(response.getStatusCode());
        }
	}
	
	public KreditnaKarticaPageWrapper displayCC(Integer brojStranice) {
		ResponseEntity<KreditnaKarticaPageWrapper> response = restTemplate
				.exchange(BLURL.getGatewayCCDisplayURL(korisnikDto.getId(), brojStranice), HttpMethod.GET, null, KreditnaKarticaPageWrapper.class);
		
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
        	throw new HttpClientErrorException(response.getStatusCode());
        }
	}
	
	public void deleteCC(Long ccId) {
		ResponseEntity<Void> response = restTemplate
                .exchange(BLURL.getGatewayCCDeleteURL(korisnikDto.getId(), ccId), HttpMethod.DELETE, null, Void.class);
		if(!response.getStatusCode().equals(HttpStatus.OK)) {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public static UserOperator getInstance() {
		if(instance == null) {
			instance=new UserOperator();
		}
		return instance;
	}
}
