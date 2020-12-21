package model;

import java.math.BigDecimal;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import dto.AvionCUDto;
import dto.AvionDto;
import dto.LetCUDto;
import dto.LetCriteriaDto;
import dto.LetDto;
import utility.BLURL;
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
	
	public LetPageWrapper getFlights(LetCriteriaDto letCriteriaDto) {
		//when
		ResponseEntity<LetPageWrapper> response = restTemplate
				.exchange(BLURL.SZL_URL + BLURL.LET_URL + letCriteriaDto.getQuery(), HttpMethod.GET, null, LetPageWrapper.class);
		//then
		if(response.getStatusCode().equals(HttpStatus.OK)) {
			return response.getBody();
		} else {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public LetDto addFlight(String pocetnaDestinacija, String krajnjaDestinacija, Integer duzina, BigDecimal cena, Integer milje) {
		LetCUDto letCreateDto=new LetCUDto(pocetnaDestinacija, krajnjaDestinacija, duzina, cena, milje);
		HttpEntity<LetCUDto> request=new HttpEntity<>(letCreateDto);
		ResponseEntity<LetDto> response=restTemplate.exchange(BLURL.SZL_URL + "/" + BLURL.LET_URL, HttpMethod.POST, request, LetDto.class);
		if(response.getStatusCode().equals(HttpStatus.CREATED)) {
			return response.getBody();
		}else {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public void deleteFlight(Long flightId) {
		ResponseEntity<Void> response=restTemplate.exchange(BLURL.SZL_URL + "/" + BLURL.LET_URL + "/" + flightId.toString(), HttpMethod.DELETE, null, Void.class);
		if(!response.getStatusCode().equals(HttpStatus.OK)) {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public AvionDto addPlane(String naziv, Integer kapacitet) {
		AvionCUDto avionCreateDto=new AvionCUDto(naziv, kapacitet);
		HttpEntity<AvionCUDto> request=new HttpEntity<>(avionCreateDto);
		ResponseEntity<AvionDto> response=restTemplate.exchange(BLURL.SZL_URL + "/" + BLURL.AVION_URL, HttpMethod.POST, request, AvionDto.class);
		if(response.getStatusCode().equals(HttpStatus.CREATED)) {
			return response.getBody();
		}else {
			throw new HttpClientErrorException(response.getStatusCode());
		}
	}
	
	public void deletePlane(Long planeId) {
		ResponseEntity<Void> response=restTemplate.exchange(BLURL.SZL_URL + "/" + BLURL.AVION_URL + "/" + planeId.toString(), HttpMethod.DELETE, null, Void.class);
		if(!response.getStatusCode().equals(HttpStatus.OK)) {
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
