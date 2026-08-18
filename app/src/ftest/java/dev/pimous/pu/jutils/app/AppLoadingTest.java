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
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class AppLoadingTest{

	private static final Path CONFIG_PATH = Path.of("unwritten.properties");
	private static final Path DEFAULT_CONFIG_PATH = Path.of(
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
		try{
			Files.deleteIfExists(CONFIG_PATH);
		}catch(IOException ignored){}

		if(Files.exists(CONFIG_PATH))
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
		assertTrue(Files.exists(app.getConfig().getPath()));
		assertDoesNotThrow(
			() -> Files.deleteIfExists(app.getConfig().getPath())
		);

		assertEquals(0, outCapture.size());
		assertTrue(errCapture.size() > 0);
		assertEquals(Locale.FRENCH, app.getI18n().getLocale());
		assertEquals(ZoneId.of("UTC"), app.getTimeZone().toZoneId());
		assertTrue(app.isLoaded());

		assertEquals(Path.of("config"), app.getConfigDir());
		assertEquals(Path.of("data"), app.getDataDir());
		assertEquals(Path.of("cache"), app.getCacheDir());
		assertEquals(Path.of("tmp"), app.getTempDir());
		assertEquals(Path.of("log"), app.getLogDir());
		// TODO: All deprecated
		assertEquals(new File("config"), app.getConfigDirFile());
		assertEquals(new File("data"), app.getDataDirFile());
		assertEquals(new File("cache"), app.getCacheDirFile());
		assertEquals(new File("tmp"), app.getTempDirFile());
		assertEquals(new File("log"), app.getLogDirFile());

		assertThrowsExactly(RuntimeException.class, () -> app.load(
			new LocalizedConfig(CONFIG_PATH,
				getProperties("system.properties"),
				false
			),
			false
		));
	}
	@Test
	void loadingDefaultsWithMandatoryUnset(){
		assertFalse(Files.exists(CONFIG_PATH));
		var t = assertThrowsExactly(RuntimeException.class, () -> app.load(
			new UnsetConfig(CONFIG_PATH,
				getProperties("system.properties"),
				false
			),
			false
		));
		assertTrue(Files.exists(CONFIG_PATH));
		assertDoesNotThrow(
			() -> Files.deleteIfExists(CONFIG_PATH)
		);
	}
	@Test
	void loadingSystem(){
		assertFalse(Files.exists(CONFIG_PATH));
		assertDoesNotThrow(() -> app.load(
			new LocalizedConfig(CONFIG_PATH,
				getProperties("system.properties"),
				true
			),
			true
		));
		assertTrue(Files.exists(app.getConfig().getPath()));
		assertDoesNotThrow(
			() -> Files.deleteIfExists(app.getConfig().getPath())
		);

		assertTrue(outCapture.size() > 0);
		assertTrue(errCapture.size() > 0);
		assertEquals(Locale.of("de", "CH"), app.getI18n().getLocale());
		assertEquals(ZoneId.of("CET"), app.getTimeZone().toZoneId());
		assertTrue(app.isLoaded());
	}
	@Test
	void loadingConfig() throws IOException{
		assertDoesNotThrow(() -> Files.createFile(CONFIG_PATH));
		assertTrue(Files.exists(CONFIG_PATH));

		final var bf = Files.newBufferedWriter(CONFIG_PATH);
		getProperties("config.properties").store(bf, "");
		bf.close();

		app.setLoggingLevel(Level.FATAL);

		assertTrue(Files.exists(CONFIG_PATH));
		assertDoesNotThrow(() -> app.load(
			new LocalizedConfig(CONFIG_PATH,
				getProperties("system.properties"),
				true
			),
			true
		));
		assertTrue(Files.exists(app.getConfig().getPath()));
		assertDoesNotThrow(
			() -> Files.deleteIfExists(app.getConfig().getPath())
		);

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

		public LocalizedConfig(final Path path,
			final Properties system,
			final boolean isLocalized
		){
			super(system);

			setPath(path);

			if(isLocalized)
				addSection("locale", new LocalizationConfig(getSystem(),
					Locale.of("en", "FR"), ZoneId.of("UTC")
				));
		}
	}
	private static class UnsetConfig extends LocalizedConfig{

		public UnsetConfig(final Path path,
		                       final Properties system,
		                       final boolean isLocalized
		){
			super(path, system, isLocalized);

			addSection("unset", new UnsetSectionConfig());
		}
	}
	private static class UnsetSectionConfig extends ConfigSection{

		@ConfigField(mandatory = true)
		private Integer unset;

		public UnsetSectionConfig(){}
	}
}
