package com.velogresttemplate.infra;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

@Component
public class HeaderSettingInterceptor implements ClientHttpRequestInterceptor {

	private static final String HEADER_CLIENT_ID = "X-Naver-Client-Id";
	private static final String HEADER_CLIENT_SECRET = "X-Naver-Client-Secret";

	private final String clientId;
	private final String secret;

	public HeaderSettingInterceptor(
		@Value("${api.papago.client-id}") String clientId,
		@Value("${api.papago.secret}") String secret) {
		this.clientId = clientId;
		this.secret = secret;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws
		IOException {

		request.getHeaders().add(HEADER_CLIENT_ID, clientId);
		request.getHeaders().add(HEADER_CLIENT_SECRET, secret);
		request.getHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		return execution.execute(request, body);
	}
}
