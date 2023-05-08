package com.velogresttemplate.adapter;

import org.springframework.stereotype.Service;

import com.velogresttemplate.port.Translator;
import com.velogresttemplate.port.TranslatorPort;

@Service
public class TranslatorAdapter implements TranslatorPort {

	private final Translator translator;

	public TranslatorAdapter(Translator translator) {
		this.translator = translator;
	}

	@Override
	public String translate(String source, String target, String text) {
		return translator.translate(source, target, text);
	}
}
