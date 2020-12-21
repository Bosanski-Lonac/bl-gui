package model;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import dto.LetCriteriaDto;
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
	
	public static FlightOperator getInstance() {
		if(instance == null) {
			instance=new FlightOperator();
		}
		return instance;
	}
}
