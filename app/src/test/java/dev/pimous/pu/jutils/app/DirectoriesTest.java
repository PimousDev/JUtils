package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.app.dirs.LinuxDirs;
import dev.pimous.pu.jutils.app.dirs.LocalDirs;
import dev.pimous.pu.jutils.app.dirs.WindowsDirs;
import dev.pimous.pu.jutils.config.ConfigPropertyException;
import dev.pimous.pu.jutils.config.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class DirectoriesTest{

	private static final AppConfig PROPERTIES = new AppConfig();

	@BeforeAll
	static void loadProperties() throws ConfigPropertyException{
		PROPERTIES.load(getProperties("app.properties"));
	}

	@SuppressWarnings("deprecation")
	@Test
	void deprecatedGetters(){
		final var dirs = new LocalDirs(
			PROPERTIES,
			new DummyConfig(getProperties("system/dirs.properties"))
		);

		assertEquals(dirs.getConfigDir().toFile(),
			dirs.getGlobalConfigDirFile()
		);
		assertEquals(dirs.getDataDir().toFile(),
			dirs.getGlobalDataDirFile()
		);
		assertEquals(dirs.getCacheDir().toFile(),
			dirs.getGlobalCacheDirFile()
		);
		assertEquals(dirs.getTemporaryDir().toFile(),
			dirs.getGlobalTempDirFile()
		);
		assertEquals(dirs.getLogDir().toFile(),
			dirs.getGlobalLogDirFile()
		);

		assertEquals(dirs.getConfigDir().toFile(),
			dirs.getConfigDirFile("afl")
		);
		assertEquals(dirs.getDataDir().toFile(),
			dirs.getDataDirFile("afl")
		);
		assertEquals(dirs.getCacheDir().toFile(),
			dirs.getCacheDirFile("afl")
		);
		assertEquals(dirs.getTempDir().toFile(),
			dirs.getTempDirFile("afl")
		);
		assertEquals(dirs.getLogDir().toFile(),
			dirs.getLogDirFile("afl")
		);
	}

	@Test
	void gettersLocal(){
		final var dirs = new LocalDirs(
			PROPERTIES,
			new DummyConfig(getProperties("system/dirs.properties"))
		);

		assertEquals(Path.of(".", "bin"), dirs.getBinaryDir());
		assertEquals(Path.of(".", "lib"), dirs.getLibraryDir());
		assertEquals(Path.of(".", "etc"), dirs.getConfigDir());
		assertEquals(Path.of(".", "data"), dirs.getDataDir());
		assertEquals(Path.of(".", "state"), dirs.getStateDir());
		assertEquals(Path.of(".", "state", "log"), dirs.getLogDir());
		assertEquals(Path.of(".", "state", "ptmp"),
			dirs.getPersistentTemporaryDir()
		);
		assertEquals(Path.of(".", "cache"), dirs.getCacheDir());
		assertEquals(Path.of(".", "tmp"), dirs.getTempDir());
	}
	@Test
	void gettersLinux(){
		final var sDirs = new LinuxDirs(
			PROPERTIES,
			new DummyConfig(getProperties("system/linuxDirs.properties")),
			true
		);

		assertEquals(Path.of("/usr/local/bin"), sDirs.getBinaryDir());
		assertEquals(Path.of("/usr/local/lib/pimous.dev/testApp"),
			sDirs.getLibraryDir()
		);
		assertEquals(Path.of("/usr/local/etc/pimous.dev/testApp"),
			sDirs.getConfigDir()
		);
		assertEquals(Path.of("/usr/local/share/pimous.dev/testApp"),
			sDirs.getDataDir()
		);
		assertEquals(Path.of("/var/local/pimous.dev/testApp/state"),
			sDirs.getStateDir()
		);
		assertEquals(Path.of("/var/local/pimous.dev/testApp/state/log"),
			sDirs.getLogDir()
		);
		assertEquals(Path.of("/var/local/pimous.dev/testApp/state/ptmp"),
			sDirs.getPersistentTemporaryDir()
		);
		assertEquals(Path.of("/var/local/pimous.dev/testApp/cache"),
			sDirs.getCacheDir()
		);
		assertEquals(Path.of("undefined/tmp/pimous.dev/testApp"),
			sDirs.getTempDir()
		);

		final var uDirs = new LinuxDirs(
			PROPERTIES,
			new DummyConfig(getProperties("system/linuxDirs.properties")),
			false
		);
		final var home = Path.of("undefined", "home");

		assertEquals(home.resolve(".local/bin"),
			uDirs.getBinaryDir()
		);
		assertEquals(home.resolve(".local/lib/pimous.dev/testApp"),
			uDirs.getLibraryDir()
		);
		assertEquals(home.resolve(".config/pimous.dev/testApp"),
			uDirs.getConfigDir()
		);
		assertEquals(home.resolve(".local/share/pimous.dev/testApp"),
			uDirs.getDataDir()
		);
		assertEquals(home.resolve(".local/state/pimous.dev/testApp"),
			uDirs.getStateDir()
		);
		assertEquals(home.resolve(".local/state/pimous.dev/testApp/log"),
			uDirs.getLogDir()
		);
		assertEquals(home.resolve(".local/state/pimous.dev/testApp/ptmp"),
			uDirs.getPersistentTemporaryDir()
		);
		assertEquals(home.resolve(".cache/pimous.dev/testApp"),
			uDirs.getCacheDir()
		);
		assertEquals(Path.of("undefined/tmp/pimous.dev/testApp"),
			uDirs.getTempDir()
		);
	}
	@Test
	void gettersWindows(){
		final var sDirs = new WindowsDirs(
			PROPERTIES,
			new DummyConfig(getProperties("system/winDirs.properties")),
			true
		);
		final var programFiles = Path.of("/", "Program Files");
		final var programData = Path.of("/", "ProgramData");

		assertEquals(programFiles.resolve("pimous.dev", "testApp", "bin"),
			sDirs.getBinaryDir()
		);
		assertEquals(programFiles.resolve("pimous.dev", "testApp", "lib"),
			sDirs.getLibraryDir()
		);
		assertEquals(programData.resolve("pimous.dev", "testApp", "etc"),
			sDirs.getConfigDir()
		);
		assertEquals(programData.resolve("pimous.dev", "testApp", "data"),
			sDirs.getDataDir()
		);
		assertEquals(programData.resolve("pimous.dev", "testApp", "state"),
			sDirs.getStateDir()
		);
		assertEquals(
			programData.resolve("pimous.dev", "testApp", "state", "log"),
			sDirs.getLogDir()
		);
		assertEquals(
			programData.resolve("pimous.dev", "testApp", "state", "ptmp"),
			sDirs.getPersistentTemporaryDir()
		);
		assertEquals(programData.resolve("pimous.dev", "testApp", "cache"),
			sDirs.getCacheDir()
		);
		assertEquals(Path.of("/", "Windows", "Temp", "pimous.dev", "testApp"),
			sDirs.getTempDir()
		);

		final var uDirs = new WindowsDirs(
			PROPERTIES,
			new DummyConfig(getProperties("system/winDirs.properties")),
			false
		);
		final var home = Path.of("/", "Users", "undefined");
		final var roaming = home.resolve("AppData", "Roaming");
		final var local = home.resolve("AppData", "Local");

		assertEquals(roaming.resolve("pimous.dev", "testApp", "bin"),
			uDirs.getBinaryDir()
		);
		assertEquals(roaming.resolve("pimous.dev", "testApp", "lib"),
			uDirs.getLibraryDir()
		);
		assertEquals(roaming.resolve("pimous.dev", "testApp", "etc"),
			uDirs.getConfigDir()
		);
		assertEquals(roaming.resolve("pimous.dev", "testApp", "data"),
			uDirs.getDataDir()
		);
		assertEquals(local.resolve("pimous.dev", "testApp", "state"),
			uDirs.getStateDir()
		);
		assertEquals(local.resolve("pimous.dev", "testApp", "state", "log"),
			uDirs.getLogDir()
		);
		assertEquals(local.resolve("pimous.dev", "testApp", "state", "ptmp"),
			uDirs.getPersistentTemporaryDir()
		);
		assertEquals(local.resolve("pimous.dev", "testApp", "cache"),
			uDirs.getCacheDir()
		);
		assertEquals(local.resolve("Temp", "pimous.dev", "testApp"),
			uDirs.getTempDir()
		);
	}

	// FUNCTIONS
	private static Properties getProperties(final String resource){
		final Properties props = new Properties();

		try(final InputStream is = ClassLoader.getSystemResourceAsStream(
			resource
		)){
			props.load(is);
		}catch(Exception ignored){}

		return props;
	}

	// INNER CLASSES
	private static class DummyConfig extends Configuration{

		public DummyConfig(final Properties system){
			super(system);
		}
	}
}
