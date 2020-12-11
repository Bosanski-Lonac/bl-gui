import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import dto.KorisnikCUDto;
import dto.KorisnikDto;
import dto.TokenRequestDto;
import dto.TokenResponseDto;

public class Main {
	private static final String KORISNIK_URL = "http://localhost:36250/api/korisnik";

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		testCorrectRegisterA(restTemplate);
		testCorrectRegisterB(restTemplate);
		String token = testLogin(restTemplate);
		if(token != null) {
			TokenInterceptor tokenInterceptor = new TokenInterceptor(token);
			restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));
			//testIncorrectDelete(restTemplate);
			testCorrectDelete(restTemplate);
		}
	}
	public static String testLogin(RestTemplate restTemplate) {
		//given
		TokenRequestDto tokenRequest = new TokenRequestDto();
		tokenRequest.setUsername("agasic218rn@raf.rs");
		tokenRequest.setPassword("testPassword");
		HttpEntity<TokenRequestDto> requestLogin = new HttpEntity<>(tokenRequest);
		//when
		ResponseEntity<TokenResponseDto> response = restTemplate
                .exchange(KORISNIK_URL + "/login", HttpMethod.POST, requestLogin, TokenResponseDto.class);
		//then
        if(response.getStatusCode().equals(HttpStatus.OK)) {
        	return response.getBody().getToken();
        }
        return null;
	}
	public static void testCorrectRegisterA(RestTemplate restTemplate) {
        //given
		KorisnikCUDto korisnikACreateDto = createTestKorisnikCUDto("agasic218rn@raf.rs",
				"testPassword", "Andrej", "Gasic", "bgrs1326z");
		HttpEntity<KorisnikCUDto> requestA = new HttpEntity<>(korisnikACreateDto);
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(KORISNIK_URL, HttpMethod.POST, requestA, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	System.out.println("Successfully registered B");
        	System.out.println(response.getBody().getBrojPasosa());
        }
    }
	public static void testCorrectRegisterB(RestTemplate restTemplate) {
        //given
		KorisnikCUDto korisnikBCreateDto = createTestKorisnikCUDto("sbudimac618rn@raf.rs",
				"testPassword1", "Stefan", "Budimac", "bgrs1264z");
		HttpEntity<KorisnikCUDto> requestB = new HttpEntity<>(korisnikBCreateDto);
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(KORISNIK_URL, HttpMethod.POST, requestB, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	System.out.println("Successfully registered B");
        	System.out.println(response.getBody().getBrojPasosa());
        }
    }
	public static void testIncorrectRegister(RestTemplate restTemplate) {
        //given
		KorisnikCUDto korisnikBCreateDto = createTestKorisnikCUDto("gupsi",
				"o", "Luka", "Kovacevic", "o");
		HttpEntity<KorisnikCUDto> requestB = new HttpEntity<>(korisnikBCreateDto);
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(KORISNIK_URL, HttpMethod.POST, requestB, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	System.out.println("Succesfully not registered");
        	System.out.println(response.getBody().getBrojPasosa());
        }
    }
	public static void testCorrectDelete(RestTemplate restTemplate) {
		ResponseEntity<Void> response = restTemplate
                .exchange(KORISNIK_URL + "/agasic218rn@raf.rs", HttpMethod.DELETE, null, Void.class);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			System.out.println("Successfully deleted");
		}
	}
	public static void testIncorrectDelete(RestTemplate restTemplate) {
		ResponseEntity<Void> response = restTemplate
                .exchange(KORISNIK_URL + "/sbudimac618rn@raf.rs", HttpMethod.DELETE, null, Void.class);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			System.out.println("Successfully not deleted");
		}
	}
	private static KorisnikCUDto createTestKorisnikCUDto(String email, String sifra, String ime, String prezime, String brojPasosa) {
		KorisnikCUDto korisnikCUDto = new KorisnikCUDto();
		korisnikCUDto.setEmail(email);
		korisnikCUDto.setSifra(sifra);
		korisnikCUDto.setIme(ime);
		korisnikCUDto.setPrezime(prezime);
		korisnikCUDto.setBrojPasosa(brojPasosa);
		return korisnikCUDto;
	}
}
