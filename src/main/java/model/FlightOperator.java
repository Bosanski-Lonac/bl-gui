package model;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import dto.LetCriteriaDto;
import wrapper.LetPageWrapper;

public class FlightOperator {
	private static final String URL = "http://localhost:26530/api";
	private static final String LET_URL = "/let";
	private static final String AVION_URL = "/avion";
	
	private static FlightOperator instance = null;
	
	private LetCriteriaDto letCriteriaDto;
	private RestTemplate restTemplate;
	
	private FlightOperator() {
		letCriteriaDto = new LetCriteriaDto();
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}
	
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public LetPageWrapper getFlights(LetCriteriaDto letCriteriaDto) {
		//given
		//HttpEntity<LetCriteriaDto> request = new HttpEntity<>(letCriteriaDto);
		//System.out.println(letCriteriaDto);
		
		/*letCriteriaDto.setPocetnaDestinacija("test");
		letCriteriaDto.setKrajnjaDestinacija("test2");
		letCriteriaDto.setMinDuzina(1);
		letCriteriaDto.setMaxDuzina(3);
		letCriteriaDto.setMinCena(1);
		letCriteriaDto.setMaxCena(3);
		letCriteriaDto.setStranica(0);*/
		
		/*System.out.println(letCriteriaDto.getPocetnaDestinacija());
		System.out.println(letCriteriaDto.getKrajnjaDestinacija());
		System.out.println(letCriteriaDto.getMinDuzina());
		System.out.println(letCriteriaDto.getMaxDuzina());
		System.out.println(letCriteriaDto.getMinCena());
		System.out.println(letCriteriaDto.getMaxCena());*/
		
		//when
		ResponseEntity<LetPageWrapper> response = restTemplate
				.exchange(URL + LET_URL + letCriteriaDto.getQuery(), HttpMethod.GET, null, LetPageWrapper.class);
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

	public LetCriteriaDto getLetCriteriaDto() {
		return letCriteriaDto;
	}

	public void setLetCriteriaDto(LetCriteriaDto letCriteriaDto) {
		this.letCriteriaDto = letCriteriaDto;
	}
}
