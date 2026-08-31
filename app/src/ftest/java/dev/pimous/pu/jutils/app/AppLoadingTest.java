package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.config.ConfigSection;
import dev.pimous.pu.jutils.config.Configuration;
import dev.pimous.pu.jutils.config.LocalizationConfig;
import dev.pimous.pu.jutils.i18n.I18n;
import dev.pimous.pu.jutils.logger.Level;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import java.io.*;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppLoadingTest{

	private static final Path CONFIG_DIR = Path.of("etc");
	private static final Path DEFAULT_CONFIG_PATH = CONFIG_DIR.resolve(
		App.DEFAULT_CONFIG_FILENAME
	);
	private static final Path CONFIG_PATH = Path.of("unwritten.properties");

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
			Files.deleteIfExists(DEFAULT_CONFIG_PATH);
			Files.deleteIfExists(CONFIG_DIR);
		}catch(IOException ignored){
			ignored.printStackTrace();
		}

		if(Files.exists(CONFIG_PATH) || Files.exists(DEFAULT_CONFIG_PATH))
			throw new RuntimeException();
	}

	@Test
	@Order(0)
	void loadingEmptyConfig(){
		assertDoesNotThrow(() -> app.load(
			new LocalizedConfig(null,
				getProperties("system.properties"),
				false
			),
			false
		));
		assertFalse(Files.isDirectory(CONFIG_DIR));

		assertEquals(0, outCapture.size());
		assertTrue(errCapture.size() > 0);
		assertTrue(errCapture.toString().contains("NOTICE"));
		assertFalse(errCapture.toString().contains("WARNING"));

		assertEquals(Locale.FRENCH, app.getI18n().getLocale());
		assertEquals(ZoneId.of("UTC"), app.getTimeZone().toZoneId());
		assertTrue(app.isLoaded());

		assertThrowsExactly(RuntimeException.class, () -> app.load(
			new LocalizedConfig(CONFIG_PATH,
				getProperties("system.properties"),
				false
			),
			false
		));
	}
	@SuppressWarnings("deprecation")
	@Test
	@Order(1)
	void loadingEmptyConfigDeprecation(){
		assertDoesNotThrow(() -> app.load(
			new LocalizedConfig(CONFIG_PATH,
				getProperties("system.properties"),
				false
			),
			false
		));
		assertFalse(Files.isDirectory(CONFIG_DIR));

		assertEquals(new File("./etc"), app.getConfigDirFile());
		assertEquals(new File("./data"), app.getDataDirFile());
		assertEquals(new File("./cache"), app.getCacheDirFile());
		assertEquals(new File("./tmp"), app.getTempDirFile());
		assertEquals(new File("./state/log"), app.getLogDirFile());
	}
	@Test
	@Order(2)
	void loadingDefaultsConfigFilename(){
		assertDoesNotThrow(() -> app.load(
			new LocalizedConfig(null,
				getProperties("system.properties"),
				true
			),
			false
		));
		assertTrue(Files.exists(DEFAULT_CONFIG_PATH));
		assertFalse(Files.exists(CONFIG_PATH));
	}
	@Test
	@Order(3)
	void loadingDefaultsWithMandatoryUnset(){
		assertThrowsExactly(RuntimeException.class, () -> app.load(
			new UnsetConfig(CONFIG_PATH,
				getProperties("system.properties"),
				false
			),
			false
		));
		assertTrue(Files.exists(CONFIG_PATH));
	}

	@Test
	@Order(4)
	void loadingSystem(){
		assertDoesNotThrow(() -> app.load(
			new LocalizedConfig(CONFIG_PATH,
				getProperties("system.properties"),
				true
			),
			true
		));
		assertTrue(Files.exists(CONFIG_PATH));

		assertTrue(outCapture.size() > 0);
		assertEquals(0, errCapture.size());
		assertEquals(Locale.of("de", "CH"), app.getI18n().getLocale());
		assertEquals(ZoneId.of("CET"), app.getTimeZone().toZoneId());
		assertTrue(app.isLoaded());
	}
	@Test
	@Order(5)
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
		assertTrue(Files.exists(CONFIG_PATH));

		assertFalse(outCapture.toString().contains("INFO"));
		assertFalse(errCapture.toString().contains("INFO"));
		assertEquals(Locale.of("de", "CH"), app.getI18n().getLocale());
		assertEquals(ZoneId.of("America/Scoresbysund"),
			app.getTimeZone().toZoneId()
		);
		assertTrue(app.isLoaded());
	}
	@Test
	@Order(6)
	@EnabledOnOs(OS.LINUX)
	void loadingInaccessibleConfig(){
		final var perms = PosixFilePermissions.fromString("---------");

		assertDoesNotThrow(() -> Files.createFile(CONFIG_PATH,
			PosixFilePermissions.asFileAttribute(perms)
		));

		var t = assertThrowsExactly(RuntimeException.class, () -> app.load(
			new UnsetConfig(CONFIG_PATH,
				getProperties("system.properties"),
				false
			),
			false
		));
		assertTrue(Files.exists(CONFIG_PATH));
		assertInstanceOf(AccessDeniedException.class, t.getCause().getCause());

		assertDoesNotThrow(() -> assertEquals(perms,
			Files.getPosixFilePermissions(CONFIG_PATH, new LinkOption[]{})
		));
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
	private static class TestApp extends App<Configuration>{

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
