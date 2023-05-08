package com.velogresttemplate.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.velogresttemplate.infra.response.PapagoTranslateResponse;
import com.velogresttemplate.port.Translator;

@Service
public class Papago implements Translator {

	private static final String REQUEST_URL = "https://openapi.naver.com/v1/papago/n2mt";

	private static final String HEADER_CLIENT_ID = "X-Naver-Client-Id";
	private static final String HEADER_CLIENT_SECRET = "X-Naver-Client-Secret";

	private static final String BODY_SOURCE_NAME = "source";
	private static final String BODY_TARGET_NAME = "target";
	private static final String BODY_TEXT_NAME = "text";

	private final String clientId;
	private final String secret;

	private RestTemplate request = new RestTemplate();
	private HttpHeaders header = new HttpHeaders();
	private LinkedMultiValueMap<String, String> paramter = new LinkedMultiValueMap<>();

	public Papago(
		@Value("${api.papago.client-id}") String clientId,
		@Value("${api.papago.secret}") String secret) {
		this.clientId = clientId;
		this.secret = secret;
	}

	public void setHeaders() {
		header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		header.set(HEADER_CLIENT_ID, clientId);
		header.set(HEADER_CLIENT_SECRET, secret);
	}

	@Override
	public String translate(String source, String target, String text) {
		setHeaders();

		paramter.add(BODY_SOURCE_NAME, source);
		paramter.add(BODY_TARGET_NAME, target);
		paramter.add(BODY_TEXT_NAME, text);

		var response = request.postForEntity(
			REQUEST_URL,
			new HttpEntity<>(paramter, header),
			PapagoTranslateResponse.class);

		if (null == response.getBody() || null == response.getBody().getTranslatedText()) {
			throw new IllegalArgumentException(text + "에 대한 번역결과가 없습니다.");
		}

		return response.getBody().getTranslatedText();
	}
}
