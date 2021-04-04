package model;

import java.math.BigDecimal;
import java.util.HashSet;

import dto.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import utility.BLURL;
import wrapper.AvionPageWrapper;
import wrapper.KartaPageWrapper;
import wrapper.LetPageWrapper;

public class FlightOperator {
	private static FlightOperator instance = null;
	
	private RestTemplate restTemplate;
	
	private FlightOperator() {
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public LetoviDto getFlightsForIds(KartaPageWrapper kartaPageWrapper) {
		//given
		ListaLetovaDto listaLetovaDto = new ListaLetovaDto();
		listaLetovaDto.setLetovi(new HashSet<>());
		for(KartaDto kartaDto : kartaPageWrapper.getContent()) {
			listaLetovaDto.getLetovi().add(kartaDto.getLetId());
		}
		HttpEntity<ListaLetovaDto> request = new HttpEntity<>(listaLetovaDto);
		//when
		ResponseEntity<LetoviDto> response = restTemplate
				.exchange(BLURL.getGatewayFlightURL(), HttpMethod.PUT, request, LetoviDto.class);
		//then
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public LetPageWrapper getFlights(LetCriteriaDto letCriteriaDto) {
		//when
		ResponseEntity<LetPageWrapper> response = restTemplate
				.exchange(BLURL.getGatewayFlightQueryURL(letCriteriaDto.getQuery()), HttpMethod.GET, null, LetPageWrapper.class);
		//then
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public LetDto addLet(String pocetnaDestinacija, String krajnjaDestinacija, Integer duzina, BigDecimal cena, Integer milje, AvionDto avionDto) {
		LetCUDto letCreateDto=new LetCUDto(pocetnaDestinacija, krajnjaDestinacija, duzina, cena, milje, avionDto.getId());
		HttpEntity<LetCUDto> request=new HttpEntity<>(letCreateDto);
		ResponseEntity<LetDto> response=restTemplate.exchange(BLURL.getGatewayFlightURL(), 
				HttpMethod.POST, request, LetDto.class);
		if(response.getStatusCode().equals(HttpStatus.CREATED)) {
			return response.getBody();
		}else {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public void deleteFlight(Long letId) {
		ResponseEntity<Void> response=restTemplate.exchange(BLURL.getGatewayFlightDeleteURL(letId), HttpMethod.DELETE, null, Void.class);
		if(!response.getStatusCode().equals(HttpStatus.OK)) {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public AvionPageWrapper getPlanes(Integer brojStranice) {
		ResponseEntity<AvionPageWrapper> response=restTemplate.exchange(BLURL.getGatewayDisplayPlanesURL(brojStranice), HttpMethod.GET, null, AvionPageWrapper.class);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		}else {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public AvionDto addPlane(String naziv, Integer kapacitet) {
		AvionCUDto avionCreateDto=new AvionCUDto(naziv, kapacitet);
		HttpEntity<AvionCUDto> request=new HttpEntity<>(avionCreateDto);
		ResponseEntity<AvionDto> response=restTemplate.exchange(BLURL.getGatewayPlaneCreateURL(), HttpMethod.POST, request, AvionDto.class);
		if(response.getStatusCode().equals(HttpStatus.CREATED)) {
			return response.getBody();
		}else {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public void deletePlane(Long avionId) {
		ResponseEntity<Void> response=restTemplate.exchange(BLURL.getGatewayPlaneDeleteURL(avionId), HttpMethod.DELETE, null, Void.class);
		if(!response.getStatusCode().equals(HttpStatus.OK)) {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}

	public LetoviInfoDto getLetoviInfo() {
		ResponseEntity<LetoviInfoDto> response=restTemplate.exchange(BLURL.getGatewayFlightInfoURL(), HttpMethod.GET, null, LetoviInfoDto.class);
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else{
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public static FlightOperator getInstance() {
		if(instance == null) {
			instance=new FlightOperator();
		}
		return instance;
	}
}
