package testexamples;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import dto.KorisnikCreateDto;
import dto.KorisnikDto;
import dto.KreditnaKarticaCUDto;
import dto.KreditnaKarticaDto;
import dto.TokenRequestDto;
import dto.TokenResponseDto;
import utility.TokenInterceptor;
import wrapper.KreditnaKarticaPageWrapper;

public class Main {
	private static final String URL = "http://localhost:36250/api";
	private static final String KORISNIK_URL = "/korisnik";
	private static final String CC_URL = "/cc";
	
	private static Long id = null;

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		//testCorrectRegisterA(restTemplate);
		//testCorrectRegisterB(restTemplate);
		String token = testLogin(restTemplate, "agasic218rn@raf.rs", "testPassword");
		//String token = testLogin(restTemplate, "sbudimac618rn@raf.rs", "testPassword1");
		if(token != null) {
			TokenInterceptor tokenInterceptor = new TokenInterceptor(token);
			restTemplate.setInterceptors(Collections.singletonList(tokenInterceptor));
			
			testCCAddA(restTemplate);
			//testCCAddB(restTemplate);
			
			//testIncorrectDelete(restTemplate, 2L);
			//testCorrectDelete(restTemplate);
			
			testCCDisplay(restTemplate);
		}
	}

	public static String testLogin(RestTemplate restTemplate, String username, String password) {
		//given
		TokenRequestDto tokenRequest = new TokenRequestDto();
		tokenRequest.setUsername(username);
		tokenRequest.setPassword(password);
		HttpEntity<TokenRequestDto> requestLogin = new HttpEntity<>(tokenRequest);
		//when
		ResponseEntity<TokenResponseDto> response = restTemplate
                .exchange(URL + KORISNIK_URL + "/login", HttpMethod.POST, requestLogin, TokenResponseDto.class);
		//then
        if(response.getStatusCode().equals(HttpStatus.OK)) {
        	id = response.getBody().getKorisnikDto().getId();
        	return response.getBody().getToken();
        }
        return null;
	}
	public static void testCorrectRegisterA(RestTemplate restTemplate) {
        //given
		KorisnikCreateDto korisnikACreateDto = new KorisnikCreateDto("agasic218rn@raf.rs",
				"testPassword", "Andrej", "Gasic", "bgrs1326z");
		HttpEntity<KorisnikCreateDto> requestA = new HttpEntity<>(korisnikACreateDto);
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(URL + KORISNIK_URL, HttpMethod.POST, requestA, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	System.out.println("Successfully registered A");
        	System.out.println(response.getBody().getBrojPasosa());
        }
    }
	public static void testCorrectRegisterB(RestTemplate restTemplate) {
        //given
		KorisnikCreateDto korisnikBCreateDto = new KorisnikCreateDto("sbudimac618rn@raf.rs",
				"testPassword1", "Stefan", "Budimac", "bgrs1264z");
		HttpEntity<KorisnikCreateDto> requestB = new HttpEntity<>(korisnikBCreateDto);
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(URL + KORISNIK_URL, HttpMethod.POST, requestB, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	System.out.println("Successfully registered B");
        	System.out.println(response.getBody().getBrojPasosa());
        }
    }
	public static void testIncorrectRegister(RestTemplate restTemplate) {
        //given
		KorisnikCreateDto korisnikBCreateDto = new KorisnikCreateDto("gupsi",
				"o", "Luka", "Kovacevic", "o");
		HttpEntity<KorisnikCreateDto> requestB = new HttpEntity<>(korisnikBCreateDto);
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(URL + KORISNIK_URL, HttpMethod.POST, requestB, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	System.out.println("Succesfully not registered");
        	System.out.println(response.getBody().getBrojPasosa());
        }
    }
	public static void testCorrectDelete(RestTemplate restTemplate) {
		ResponseEntity<Void> response = restTemplate
                .exchange(URL + KORISNIK_URL + "/" + id.toString(), HttpMethod.DELETE, null, Void.class);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			System.out.println("Successfully deleted");
		}
	}
	public static void testIncorrectDelete(RestTemplate restTemplate, Long id) {
		ResponseEntity<Void> response = restTemplate
                .exchange(URL + KORISNIK_URL + "/" + id.toString(), HttpMethod.DELETE, null, Void.class);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			System.out.println("Successfully not deleted");
		}
	}
	public static void testCCAddA(RestTemplate restTemplate) {
		//given
		KreditnaKarticaCUDto kreditnaKarticaCreateDto = new KreditnaKarticaCUDto(54102561655549576L,
				"Andrej", "Gasic", 123);
		HttpEntity<KreditnaKarticaCUDto> request = new HttpEntity<>(kreditnaKarticaCreateDto);
		//when
		ResponseEntity<KreditnaKarticaDto> response = restTemplate
				.exchange(URL + KORISNIK_URL + "/" + id.toString() + CC_URL, HttpMethod.POST, request, KreditnaKarticaDto.class);
		//then
		if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	System.out.println("Succesfully added credit card A");
        	System.out.println(response.getBody().getId());
        }
	}
	public static void testCCAddB(RestTemplate restTemplate) {
		//given
		KreditnaKarticaCUDto kreditnaKarticaCreateDto = new KreditnaKarticaCUDto(5410216768951324L,
				"Stefan", "Budimac", 321);
		HttpEntity<KreditnaKarticaCUDto> request = new HttpEntity<>(kreditnaKarticaCreateDto);
		//when
		ResponseEntity<KreditnaKarticaDto> response = restTemplate
				.exchange(URL + KORISNIK_URL + "/" + id.toString() + CC_URL, HttpMethod.POST, request, KreditnaKarticaDto.class);
		//then
		if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	System.out.println("Succesfully added credit card B");
        	System.out.println(response.getBody().getId());
        }
	}
	public static void testCCDisplay(RestTemplate restTemplate) {
		ResponseEntity<KreditnaKarticaPageWrapper> response = restTemplate
				.exchange(URL + KORISNIK_URL + "/" + id.toString() + CC_URL, HttpMethod.GET, null, KreditnaKarticaPageWrapper.class);
		
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			for(KreditnaKarticaDto cc : response.getBody().getContent()) {
				System.out.println(cc.getKrajKartice());
			}
		}
	}
}
