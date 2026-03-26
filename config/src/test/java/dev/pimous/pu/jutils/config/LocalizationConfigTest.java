package dev.pimous.pu.jutils.config;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationConfigTest{

	private static final Locale DEFAULT_LOCALE = Locale.of("en", "FR");
	private static final ZoneId DEFAULT_ZONEID = ZoneId.of("UTC");

	@Test
	void getters(){
		final Properties system = getProperties("config/system.properties");

		TestConfig tc = new TestConfig(system);
		assertEquals(DEFAULT_LOCALE,
			tc.getSection(LocalizationConfig.class).getLocale()
		);
		assertEquals(DEFAULT_ZONEID,
			tc.getSection(LocalizationConfig.class).getTimeZone().toZoneId()
		);

		system.putAll(getProperties("config/localized.properties"));
		tc = new TestConfig(system);
		assertEquals(Locale.of("fr", "CH"),
			tc.getSection(LocalizationConfig.class).getLocale()
		);
		assertEquals(ZoneId.of("CET"),
			tc.getSection(LocalizationConfig.class).getTimeZone().toZoneId()
		);
	}

	@Test
	void integrity(){
		final LocalizationConfig lc = new LocalizationConfig(
			new SystemConfig(getProperties("config/system.properties")),
			DEFAULT_LOCALE, DEFAULT_ZONEID
		);

		assertEquals(lc.getLocale(),
			lc.getParser("locale").apply(
				lc.getFormatter("locale").apply(lc.getLocale()).toString()
			)
		);
		assertEquals(lc.getTimeZone().toZoneId(),
			lc.getParser("timezone").apply(
				lc.getFormatter("timezone")
					.apply(lc.getTimeZone().toZoneId())
					.toString()
			)
		);
	}

	// FUNCTIONS
	private Properties getProperties(String resource){
		final Properties props = new Properties();

		try(final InputStream is = ClassLoader.getSystemResourceAsStream(
			resource
		)){
			props.load(is);
		}catch(Exception ignored){}

		return props;
	}

	// INNER CLASSES
	private static class TestConfig extends Configuration{

		{
			addSection("localization", new LocalizationConfig(getSystem(),
				DEFAULT_LOCALE, DEFAULT_ZONEID
			));
		}

		public TestConfig(final Properties system){
			super(system);
		}
	}
}
