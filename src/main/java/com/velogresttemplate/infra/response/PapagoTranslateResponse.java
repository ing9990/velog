package com.velogresttemplate.infra.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PapagoTranslateResponse {

	private Message message;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Message {
		private Result result;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Result {
		private String srcLangType;
		private String tarLangType;
		private String translatedText;
		private String engineType;
	}

	public String getTranslatedText() {
		return this.message.result.translatedText;
	}
}
