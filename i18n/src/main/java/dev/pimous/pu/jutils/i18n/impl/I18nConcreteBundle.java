package dev.pimous.pu.jutils.i18n.impl;

import dev.pimous.pu.jutils.base.BadResourceException;
import dev.pimous.pu.jutils.i18n.I18nBundle;
import dev.pimous.pu.jutils.i18n.util.LocaleResPaths;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class I18nConcreteBundle implements I18nBundle{

	private static final String UNDEFINED_TEXT = "#UNDEFINED#";

	private final Locale locale;
	private Properties sentences = new Properties();
	private I18nConcreteBundle parent;

	public I18nConcreteBundle(final Locale locale){
		this.locale = locale;
	}

	// GETTERS
	@Override
	public Locale getLocale(){ return locale; }
	@Override
	public I18nBundle getParent(){ return parent; }

	private Optional<String> searchSentence(final String identifier){
		return Optional.ofNullable(sentences.getProperty(identifier))
			.or(() -> getParent() != null ?
				parent.searchSentence(identifier) : Optional.empty()
			);
	}
	@Override
	public String getSentence(final String identifier, final Object... args){
		return searchSentence(identifier)
			.map(s -> args.length > 0 ? s.formatted(args) : s)
			.orElse(UNDEFINED_TEXT);
	}
	@Override
	public String getLocalDateTime(final String identifier,
		final TemporalAccessor temporal
	){
		return searchSentence(identifier)
			.map(s -> DateTimeFormatter.ofPattern(s)
				.withLocale(locale)
				.format(temporal)
			)
			.orElse(UNDEFINED_TEXT);
	}

	// SETTERS
	public void setParent(final I18nConcreteBundle parent){
		if(this == parent)
			throw new IllegalArgumentException(
				"A I18nBundle cannot be its own parent;"
			);

		this.parent = parent;
	}

	public void loadSection(final String section, final Charset charset)
		throws BadResourceException
	{
		final String path = LocaleResPaths.getLocaleResourcePath(
			locale, section
		);

		final InputStream is = ClassLoader.getSystemResourceAsStream(path);
		if(is == null)
			throw new BadResourceException(
				"%s sentence section doesn't exists for %s locale"
					.formatted(section, locale.toLanguageTag()),
				path,
				new FileNotFoundException(path)
			);

		try{
			sentences = new Properties(sentences);
			sentences.load(new InputStreamReader(is, charset));
		}catch(final IOException|IllegalArgumentException e){
			throw new BadResourceException(
				"Unable to read %s sentence section", path, e
			);
		}
	}
}
