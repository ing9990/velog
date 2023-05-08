package com.velogresttemplate.adapter;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.velogresttemplate.port.TranslatorPort;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class TranslatorApi {

	private final TranslatorPort translatorPort;

	public TranslatorApi(TranslatorPort translatorPort) {
		this.translatorPort = translatorPort;
	}

	@GetMapping("/velog")
	public ResponseEntity<?> velog() {
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<Object> response = restTemplate.getForEntity("http://localhost:7777/api/v1/test",
			Object.class);

		System.out.println(response);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/translate")
	public ResponseEntity<?> translate(
		@RequestParam(name = "text") String text,
		@RequestParam(name = "source", required = false, defaultValue = "ko") String source,
		@RequestParam(name = "target", required = false, defaultValue = "en") String target
	) {
		var response = translatorPort.translate(source, target, text);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
