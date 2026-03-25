package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.config.Configuration;
import dev.pimous.pu.jutils.config.LocalizationConfig;
import dev.pimous.pu.jutils.config.Version;
import dev.pimous.pu.jutils.i18n.I18n;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class AppTest{

	@Test
	void getters(){
		final App<LocalizedConfig> app = new TestApp(31);

		assertEquals("testApp", app.getIdentifier());
		assertEquals("Test App", app.getProperties().getName());
		assertEquals("This is a test app.",
			app.getProperties().getDescription()
		);
		assertEquals(
			new Version(31, 1, 20,
				new Version.PreRelease('f', 24)
			).toString(),
			app.getProperties().getVersion().toString()
		);
		assertEquals("Closed", app.getProperties().getLicense());
		assertEquals("Me", app.getProperties().getAuthor());
		// FIXME: Unable to test whether a pool of exactly 31 alive threads has been created.
//		assertEquals(31,
//			((ScheduledThreadPoolExecutor) app.getExecutor()).getPoolSize()
//		);
		assertFalse(app.isLoaded());

		assertNull(app.getConfig());
		assertThrows(NullPointerException.class, app::getI18n);
		assertEquals(ZoneId.of("UTC"), app.getTimeZone().toZoneId());
	}

	@Nested
	class LoadingTest{

		private static final File configFile = new File("unwritten.properties");
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
			configFile.delete();
		}

		@Test
		void loadingDefaults(){
			assertFalse(configFile.exists());
			assertDoesNotThrow(() -> app.load(
				new LocalizedConfig(null,
					getProperties("system.properties"),
					false
				),
				false
			));
			assertFalse(configFile.exists());

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

			assertThrows(RuntimeException.class, () -> app.load(
				new LocalizedConfig(configFile,
					getProperties("system.properties"),
					false
				),
				false
			));
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
			configFile.createNewFile();
			final FileWriter fw = new FileWriter(configFile);
			getProperties("config.properties").store(fw, "");
			fw.close();

			assertTrue(configFile.exists());
			assertDoesNotThrow(() -> app.load(
				new LocalizedConfig(configFile,
					getProperties("system.properties"),
					true
				),
				true
			));
			assertTrue(configFile.exists() && configFile.delete());

			assertEquals(Locale.of("de", "CH"), app.getI18n().getLocale());
			assertEquals(ZoneId.of("America/Scoresbysund"),
				app.getTimeZone().toZoneId()
			);
			assertTrue(app.isLoaded());
		}
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
		public TestApp(final int threads){
			super(threads);
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
}
