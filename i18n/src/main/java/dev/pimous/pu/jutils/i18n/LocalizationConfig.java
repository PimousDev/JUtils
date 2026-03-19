package dev.pimous.pu.jutils.i18n;

import dev.pimous.pu.jutils.base.Functions;
import dev.pimous.pu.jutils.config.ConfigSection;
import dev.pimous.pu.jutils.config.SystemConfig;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Function;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class LocalizationConfig extends ConfigSection{

	private static final String DEFAULT_LANGUAGE = "en";
	private static final String DEFAULT_COUNTRY = "FR";
	private static final String DEFAULT_TIMEZONE = ZoneId.of("UTC").getId();

	@ConfigField(property = "locale")
	private Locale locale;
	@ConfigField(property = "timezone")
	private ZoneId zoneId;

	public LocalizationConfig(final SystemConfig system){
		locale = Locale.of(
			system.getLanguage().orElse(DEFAULT_LANGUAGE),
			system.getCountry().orElse(DEFAULT_COUNTRY)
		);
		zoneId = ZoneId.of(
			system.getTimezone().orElse(DEFAULT_TIMEZONE)
		);
	}

	// GETTERS
	@Override
	protected Function<String, ?> getParser(final String property){
		return switch(property){
			case "locale" -> Locale::forLanguageTag;
			case "timezone" -> ZoneId::of;
			default -> super.getParser(property);
		};
	}
	@Override
	protected Function<Object, CharSequence> getFormatter(String property){
		return switch(property){
			case "locale" -> Functions.castFunction(Locale.class).andThen(
				Locale::toLanguageTag
			);
			default -> super.getFormatter(property);
		};
	}

	public Locale getLocale(){ return locale; }
	public TimeZone getTimeZone(){ return TimeZone.getTimeZone(zoneId); }
}
