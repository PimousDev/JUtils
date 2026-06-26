package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.config.ConfigSection;
import dev.pimous.pu.jutils.config.Configuration;
import dev.pimous.pu.jutils.config.LocalizationConfig;
import dev.pimous.pu.jutils.i18n.I18n;
import dev.pimous.pu.jutils.logger.Level;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class AppLoadingTest{

	private static final File configFile = new File("unwritten.properties");
	private static final File defaultConfigFile = new File(
		App.DEFAULT_CONFIG_FILENAME
	);
	private static ByteArrayOutputStream outCapture;
	private static ByteArrayOutputStream errCapture;
	private static TestApp app;

	@BeforeEach
	void setup(){
		outCapture = new ByteArrayOutputStream();
		errCapture = new ByteArrayOutputStream();
		app = new TestApp(0,
			new PrintStream(outCapture),
			new PrintStream(errCapture)
		);
	}
	@AfterEach
	void clean(){
		if(configFile.exists() && !configFile.delete())
			throw new RuntimeException();
	}

	@Test
	void loadingDefaults(){
		assertDoesNotThrow(() -> app.load(
			new LocalizedConfig(null,
				getProperties("system.properties"),
				false
			),
			false
		));
		// Tests when config file isn't writable.
		assertFalse(app.getConfig().getFile().exists()
			&& app.getConfig().getFile().delete()
		);

		assertEquals(0, outCapture.size());
		assertTrue(errCapture.size() > 0);
		assertEquals(Locale.FRENCH, app.getI18n().getLocale());
		assertEquals(ZoneId.of("UTC"), app.getTimeZone().toZoneId());
		assertTrue(app.isLoaded());

		assertEquals(new File("config"), app.getConfigDir());
		assertEquals(new File("data"), app.getDataDir());
		assertEquals(new File("cache"), app.getCacheDir());
		assertEquals(new File("tmp"), app.getTempDir());
		assertEquals(new File("log"), app.getLogDir());

		assertThrowsExactly(RuntimeException.class, () -> app.load(
			new LocalizedConfig(configFile,
				getProperties("system.properties"),
				false
			),
			false
		));
	}
	@Test
	void loadingDefaultsWithMandatoryUnset(){
		assertFalse(configFile.exists());
		assertThrowsExactly(RuntimeException.class, () -> app.load(
			new UnsetConfig(configFile,
				getProperties("system.properties"),
				false
			),
			false
		));
		assertTrue(configFile.exists() && configFile.delete());
	}
	@Test
	void loadingSystem(){
		assertFalse(configFile.exists());
		assertDoesNotThrow(() -> app.load(
			new LocalizedConfig(configFile,
				getProperties("system.properties"),
				true
			),
			true
		));
		assertTrue(configFile.exists() && configFile.delete());

		assertTrue(outCapture.size() > 0);
		assertTrue(errCapture.size() > 0);
		assertEquals(Locale.of("de", "CH"), app.getI18n().getLocale());
		assertEquals(ZoneId.of("CET"), app.getTimeZone().toZoneId());
		assertTrue(app.isLoaded());
	}
	@Test
	void loadingConfig() throws IOException{
		if(!configFile.createNewFile())
			throw new RuntimeException();

		final FileWriter fw = new FileWriter(configFile);
		getProperties("config.properties").store(fw, "");
		fw.close();

		app.setLoggingLevel(Level.FATAL);

		assertTrue(configFile.exists());
		assertDoesNotThrow(() -> app.load(
			new LocalizedConfig(configFile,
				getProperties("system.properties"),
				true
			),
			true
		));
		assertTrue(configFile.exists() && configFile.delete());

		assertFalse(outCapture.toString().contains("INFO"));
		assertFalse(errCapture.toString().contains("INFO"));
		assertEquals(Locale.of("de", "CH"), app.getI18n().getLocale());
		assertEquals(ZoneId.of("America/Scoresbysund"),
			app.getTimeZone().toZoneId()
		);
		assertTrue(app.isLoaded());
	}

	// FUNCTIONS
	private Properties getProperties(final String resource){
		final Properties props = new Properties();

		try(final InputStream is = ClassLoader.getSystemResourceAsStream(
			resource
		)){
			props.load(is);
		}catch(Exception ignored){}

		return props;
	}

	// INNER CLASSES
	private static class TestApp extends App<LocalizedConfig>{

		private static final I18n language = new I18n(Locale.FRENCH, List.of());

		public TestApp(final int threads,
			final PrintStream out,
			final PrintStream err
		){
			super(threads, System.in, out, err);
		}

		// FUNCTIONS
		public void load(final LocalizedConfig config, boolean hasGUI){
			super.load(config, language, hasGUI);
		}

		@Override
		public void run(String[] args){}
		@Override
		public void run(Console console, String[] args){}
	}
	private static class LocalizedConfig extends Configuration{

		public LocalizedConfig(final File file,
			final Properties system,
			final boolean isLocalized
		){
			super(system);

			setFile(file);

			if(isLocalized)
				addSection("locale", new LocalizationConfig(getSystem(),
					Locale.of("en", "FR"), ZoneId.of("UTC")
				));
		}
	}
	private static class UnsetConfig extends LocalizedConfig{

		public UnsetConfig(final File file,
		                       final Properties system,
		                       final boolean isLocalized
		){
			super(file, system, isLocalized);

			addSection("unset", new UnsetSectionConfig());
		}
	}
	private static class UnsetSectionConfig extends ConfigSection{

		@ConfigField(mandatory = true)
		private Integer unset;

		public UnsetSectionConfig(){}
	}
}
