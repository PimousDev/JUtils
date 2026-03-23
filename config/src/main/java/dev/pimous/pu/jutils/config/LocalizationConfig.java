package dev.pimous.pu.jutils.config;

import dev.pimous.pu.jutils.base.Functions;

import java.time.ZoneId;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Function;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class LocalizationConfig extends ConfigSection{

	@ConfigField(property = "locale")
	private final Locale locale;
	@ConfigField(property = "timezone")
	private final ZoneId zoneId;

	public LocalizationConfig(final SystemConfig system,
		final Locale defaultLocale,
		final ZoneId defaultZone
	){
		locale = Locale.of(
			system.getLanguage().orElse(defaultLocale.getLanguage()),
			system.getCountry().orElse(defaultLocale.getCountry())
		);
		zoneId = ZoneId.of(
			system.getTimezone().orElse(defaultZone.getId())
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
