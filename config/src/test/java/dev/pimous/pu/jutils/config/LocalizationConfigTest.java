package dev.pimous.pu.jutils.config;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationConfigTest{

	@Test
	void getters(){
		final Properties system = getProperties("config/system.properties");

		TestConfig tc = new TestConfig(system);
		assertEquals(Locale.of("en", "FR"),
			tc.getSection(LocalizationConfig.class).getLocale()
		);
		assertEquals(ZoneId.of("UTC"),
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
			new SystemConfig(getProperties("config/system.properties"))
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

		public static final File file = new File("server.properties");

		{
			addSection("localization", new LocalizationConfig(getSystem()));
		}

		public TestConfig(final Properties system){
			super(file, system);
		}
		public TestConfig(
			final Properties system, final Map<String, String> env
		){
			super(file, system, env);
		}
	}
}
