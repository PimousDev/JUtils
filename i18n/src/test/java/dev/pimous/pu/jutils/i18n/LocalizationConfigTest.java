package dev.pimous.pu.jutils.i18n;

import dev.pimous.pu.jutils.config.Configuration;
import dev.pimous.pu.jutils.config.SystemConfig;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

class LocalizationConfigTest{

	@Test
	void getters(){
		final Properties system = getProperties("config/minimal.properties");

		TestConfig tc = new TestConfig(system);
		assertEquals(Locale.of("en", "FR"),
			tc.getSection(LocalizationConfig.class).getLocale()
		);
		assertEquals(TimeZone.getTimeZone(ZoneOffset.UTC.getId()),
			tc.getSection(LocalizationConfig.class).getTimeZone()
		);

		system.putAll(getProperties("config/localized.properties"));
		tc = new TestConfig(system);
		assertEquals(Locale.of("fr", "CH"),
			tc.getSection(LocalizationConfig.class).getLocale()
		);
		assertEquals(TimeZone.getTimeZone(ZoneId.of("CET")),
			tc.getSection(LocalizationConfig.class).getTimeZone()
		);
	}

	// FUNCTIONS
	private Properties getProperties(String resource){
		final Properties props = new Properties();

		try{
			props.load(ClassLoader.getSystemResourceAsStream(resource));
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
