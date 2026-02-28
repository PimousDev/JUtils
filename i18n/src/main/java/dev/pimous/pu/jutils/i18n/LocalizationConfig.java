package dev.pimous.pu.jutils.i18n;

import dev.pimous.pu.jutils.config.ConfigSection;
import dev.pimous.pu.jutils.config.Configuration;
import dev.pimous.pu.jutils.config.SystemConfig;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Function;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class LocalizationConfig extends ConfigSection{

	private static final String DEFAULT_LANGUAGE = "en";
	private static final String DEFAULT_COUNTRY = "FR";
	private static final String DEFAULT_TIMEZONE = ZoneOffset.UTC.getId();

	@ConfigField(property = "locale")
	private Locale locale;
	@ConfigField(property = "timezone")
	private TimeZone timeZone;

	public LocalizationConfig(final SystemConfig system){
		locale = Locale.of(
			system.getLanguage().orElse(DEFAULT_LANGUAGE),
			system.getCountry().orElse(DEFAULT_COUNTRY)
		);
		timeZone = TimeZone.getTimeZone(
			system.getTimezone().orElse(DEFAULT_TIMEZONE)
		);
	}

	// GETTERS
	@Override
	protected Function<? super String, ?> getParser(final String property){
		return switch(property){
			case "locale" -> Locale::forLanguageTag;
			case "timezone" -> TimeZone::getTimeZone;
			default -> super.getParser(property);
		};
	}

	public Locale getLocale(){ return locale; }
	public TimeZone getTimeZone(){ return timeZone; }
}
