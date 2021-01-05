package model;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import dto.KartaDto;
import dto.KartaReserveDto;
import utility.BLURL;

public class TicketOperator {
	private static TicketOperator instance = null;
	
	private RestTemplate restTemplate;
	
	private TicketOperator() {
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public KartaDto reserve(Long korisnikId, Long letId, Long kreditnaKarticaId) {
		//given
		KartaReserveDto kartaReserveDto = new KartaReserveDto();
		kartaReserveDto.setKreditnaKarticaId(kreditnaKarticaId);
		kartaReserveDto.setLetId(letId);
		HttpEntity<KartaReserveDto> request = new HttpEntity<>(kartaReserveDto);
		//when
		ResponseEntity<KartaDto> response = restTemplate
				.exchange(BLURL.getGatewayReserveURL(korisnikId), HttpMethod.POST, request, KartaDto.class);
		//then
		if(response.getStatusCode().equals(HttpStatus.CREATED)) {
			return response.getBody();
		} else {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public Long getReservedSlots(Long letId) {
		ResponseEntity<Long> response = restTemplate
				.exchange(BLURL.getGatewayReservedSlotsURL(letId), HttpMethod.GET, null, Long.class);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		}else {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public static TicketOperator getInstance() {
		if(instance == null) {
			instance = new TicketOperator();
		}
		return instance;
	}
}
