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
import wrapper.KartaPageWrapper;

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
	
	public void reserve(Long letId, Long kreditnaKarticaId, Integer kolicina) {
		//given
		KartaReserveDto kartaReserveDto = new KartaReserveDto();
		kartaReserveDto.setKreditnaKarticaId(kreditnaKarticaId);
		kartaReserveDto.setLetId(letId);
		kartaReserveDto.setKolicina(kolicina);
		HttpEntity<KartaReserveDto> request = new HttpEntity<>(kartaReserveDto);
		//when
		ResponseEntity<Void> response = restTemplate
				.exchange(BLURL.getGatewayReserveURL(UserOperator.getInstance().getUserInfo(false).getId()),
						HttpMethod.POST, request, Void.class);
		//then
		if(response.getStatusCode().equals(HttpStatus.CREATED)) {
		} else {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public KartaPageWrapper displayKarte(Integer brojStranice) {
		//when
		ResponseEntity<KartaPageWrapper> response = restTemplate
				.exchange(BLURL.getGatewayReservationsURL(UserOperator.getInstance().getUserInfo(false).getId(),
						brojStranice), HttpMethod.GET, null, KartaPageWrapper.class);
		//then
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			for(KartaDto kartaDto : response.getBody().getContent()) {
				System.out.println(kartaDto.getId());
				System.out.println(kartaDto.getLetId());
			}
			return response.getBody();
		} else {
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
