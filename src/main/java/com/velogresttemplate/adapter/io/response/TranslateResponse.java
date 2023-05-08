package com.velogresttemplate.adapter.io.response;

import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TranslateResponse {

	private String translatedText;

	public static TranslateResponse of(final String translatedText) {
		validateTranslatedText(translatedText);

		return TranslateResponse.builder()
			.translatedText(translatedText)
			.build();
	}

	private static void validateTranslatedText(String translatedText) {
		if (!StringUtils.hasText(translatedText)) {
			throw new IllegalArgumentException("번역결과가 없습니다.");
		}
	}
}
