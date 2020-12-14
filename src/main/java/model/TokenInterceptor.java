package model;
import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class TokenInterceptor implements ClientHttpRequestInterceptor {

	private String token;
	
	public TokenInterceptor(String token) {
		this.token = token;
	}
	
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", "Bearer " + token);
        return execution.execute(request, body);
	}

}
