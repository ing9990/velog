package com.velogresttemplate.application.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.velogresttemplate.infra.HeaderSettingInterceptor;
import com.velogresttemplate.infra.TranslateErrorHandler;

@Configuration
public class AppConfig {

	private final TranslateErrorHandler translateErrorHandler;
	private final HeaderSettingInterceptor headerSettingInterceptor;

	public AppConfig(
		TranslateErrorHandler translateErrorHandler,
		HeaderSettingInterceptor headerSettingInterceptor
	) {
		this.translateErrorHandler = translateErrorHandler;
		this.headerSettingInterceptor = headerSettingInterceptor;
	}

	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		restTemplate.setInterceptors(Collections.singletonList(headerSettingInterceptor));
		restTemplate.setErrorHandler(translateErrorHandler);

		return restTemplate;
	}
}
