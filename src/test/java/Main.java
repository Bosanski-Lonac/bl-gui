import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import dto.KorisnikCUDto;
import dto.KorisnikDto;

public class Main {
	private static final String KORISNIK_URL = "http://localhost:36250/api/korisnik";

	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		testCorrectRegister(restTemplate);
	}
	public static void testCorrectRegister(RestTemplate restTemplate) {
        //given
		KorisnikCUDto korisnikACreateDto = createTestKorisnikCUDto("agasic218rn@raf.rs",
				"testPassword", "Andrej", "Gasic", "bgrs1326z");
		HttpEntity<KorisnikCUDto> requestA = new HttpEntity<>(korisnikACreateDto);
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(KORISNIK_URL, HttpMethod.POST, requestA, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	System.out.println("WOO");
        	System.out.println(response.getBody().getBrojPasosa());
        }
    }
	public static void testIncorrectRegister(RestTemplate restTemplate) {
        //given
		KorisnikCUDto korisnikBCreateDto = createTestKorisnikCUDto("sbudimac",
				"testPassword", "Stefan", "Budimac", "o");
		HttpEntity<KorisnikCUDto> requestB = new HttpEntity<>(korisnikBCreateDto);
        //when
        ResponseEntity<KorisnikDto> response = restTemplate
                .exchange(KORISNIK_URL, HttpMethod.POST, requestB, KorisnikDto.class);
        //then
        if(response.getStatusCode().equals(HttpStatus.CREATED)) {
        	System.out.println("WOO");
        	System.out.println(response.getBody().getBrojPasosa());
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
