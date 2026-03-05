package dev.pimous.pu.jutils.i18n;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public interface I18nBundle{

	// GETTERS
	public abstract Locale getLocale();
	public abstract I18nBundle getParent();

	public abstract String getSentence(final String identifier,
		final Object ...args
	);
	public abstract String getLocalDateTime(final String identifier,
		final TemporalAccessor temporal
	);
}
