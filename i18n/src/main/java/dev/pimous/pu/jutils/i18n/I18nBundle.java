package dev.pimous.pu.jutils.i18n;

import java.time.temporal.TemporalAccessor;
import java.util.Locale;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public interface I18nBundle{

	// GETTERS
	Locale getLocale();
	I18nBundle getParent();

	String getSentence(final String identifier,
		final Object... args
	);
	String getLocalDateTime(final String identifier,
		final TemporalAccessor temporal
	);
}
