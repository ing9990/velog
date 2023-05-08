package com.velogresttemplate.infra;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.velogresttemplate.infra.response.PapagoTranslateResponse;
import com.velogresttemplate.port.Translator;

@Service
public class Papago implements Translator {

	private static final String REQUEST_URL = "https://openapi.naver.com/v1/papago/n2mt";

	private static final String BODY_SOURCE_NAME = "source";
	private static final String BODY_TARGET_NAME = "target";
	private static final String BODY_TEXT_NAME = "text";

	private final RestTemplate request;

	private LinkedMultiValueMap<String, String> paramter = new LinkedMultiValueMap<>();

	public Papago(RestTemplate request) {
		this.request = request;
	}

	@Override
	public String translate(String source, String target, String text) {

		paramter.add(BODY_SOURCE_NAME, source);
		paramter.add(BODY_TARGET_NAME, target);
		paramter.add(BODY_TEXT_NAME, text);

		var response = request.postForEntity(
			REQUEST_URL,
			new HttpEntity<>(paramter, new HttpHeaders()),
			PapagoTranslateResponse.class);

		if (null == response.getBody() || null == response.getBody().getTranslatedText()) {
			throw new IllegalArgumentException(text + "에 대한 번역결과가 없습니다.");
		}

		return response.getBody().getTranslatedText();
	}
}
