package com.velogresttemplate.infra;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
public class TranslateErrorHandler implements ResponseErrorHandler {

	private final ObjectMapper om;

	public TranslateErrorHandler(ObjectMapper om) {
		this.om = om;
	}

	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {
		return response.getStatusCode().is4xxClientError()
			|| response.getStatusCode().is5xxServerError();
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		var status = response.getStatusCode();

		if (status.is5xxServerError()) {
			throw new RuntimeException("예상치 못한 오류가 발생했습니다.");
		}

		if (status.is4xxClientError()) {
			ErrorResponse errorResponse = om.readValue(response.getBody(), ErrorResponse.class);

			throw new IllegalArgumentException(errorResponse.getErrorMessage() + " : [" + errorResponse.getErrorCode() + "]");
		}
	}

	@Data
	@NoArgsConstructor
	static class ErrorResponse {
		private String errorCode;
		private String errorMessage;
	}
}
