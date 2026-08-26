package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.app.dirs.LinuxDirs;
import dev.pimous.pu.jutils.app.dirs.LocalDirs;
import dev.pimous.pu.jutils.app.dirs.WindowsDirs;
import dev.pimous.pu.jutils.config.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class DirectoriesTest{

	@Test
	void gettersLocal(){
		final var dirs = new LocalDirs(
			new DummyConfig(getProperties("system/dirs.properties")),
			false
		);

		assertEquals(Path.of("config"), dirs.getGlobalConfigDir());
		assertEquals(Path.of("data"), dirs.getGlobalDataDir());
		assertEquals(Path.of("cache"), dirs.getGlobalCacheDir());
		assertEquals(Path.of("tmp"), dirs.getGlobalTempDir());
		assertEquals(Path.of("log"), dirs.getGlobalLogDir());

		assertEquals(Path.of("config"), dirs.getConfigDir("afl"));
		assertEquals(Path.of("data"), dirs.getDataDir("afl"));
		assertEquals(Path.of("cache"), dirs.getCacheDir("afl"));
		assertEquals(Path.of("tmp"), dirs.getTempDir("afl"));
		assertEquals(Path.of("log"), dirs.getLogDir("afl"));
	}
	@SuppressWarnings("deprecation")
	@Test
	void deprecatedGettersLocal(){
		final var dirs = new LocalDirs(
			new DummyConfig(getProperties("system/dirs.properties")),
			false
		);
		assertEquals(new File("config"), dirs.getGlobalConfigDirFile());
		assertEquals(new File("data"), dirs.getGlobalDataDirFile());
		assertEquals(new File("cache"), dirs.getGlobalCacheDirFile());
		assertEquals(new File("tmp"), dirs.getGlobalTempDirFile());
		assertEquals(new File("log"), dirs.getGlobalLogDirFile());

		assertEquals(new File("config"), dirs.getConfigDirFile("afl"));
		assertEquals(new File("data"), dirs.getDataDirFile("afl"));
		assertEquals(new File("cache"), dirs.getCacheDirFile("afl"));
		assertEquals(new File("tmp"), dirs.getTempDirFile("afl"));
		assertEquals(new File("log"), dirs.getLogDirFile("afl"));
	}

	@Test
	void gettersLinux(){
		final var dirs = new LinuxDirs(
			new DummyConfig(getProperties("system/linuxDirs.properties")),
			false
		);

		assertEquals(Path.of("undefined/home/.config"),
			dirs.getGlobalConfigDir()
		);
		assertEquals(Path.of("undefined/home/.local/share"),
			dirs.getGlobalDataDir()
		);
		assertEquals(Path.of("undefined/home/.cache"),
			dirs.getGlobalCacheDir()
		);
		assertEquals(Path.of("undefined/tmp"), dirs.getGlobalTempDir());
		assertEquals(Path.of("undefined/home/.local/share"),
			dirs.getGlobalLogDir()
		);

		assertEquals(Path.of("undefined/home/.config/afl"),
			dirs.getConfigDir("afl")
		);
		assertEquals(Path.of("undefined/home/.local/share/afl/data"),
			dirs.getDataDir("afl")
		);
		assertEquals(Path.of("undefined/home/.cache/afl"),
			dirs.getCacheDir("afl")
		);
		assertEquals(Path.of("undefined/tmp/afl"), dirs.getTempDir("afl"));
		assertEquals(Path.of("undefined/home/.local/share/afl/log"),
			dirs.getLogDir("afl")
		);
	}
	@SuppressWarnings("deprecation")
	@Test
	void deprecatedGettersLinux(){
		final var dirs = new LinuxDirs(
			new DummyConfig(getProperties("system/linuxDirs.properties")),
			false
		);

		assertEquals(new File("undefined/home/.config"),
			dirs.getGlobalConfigDirFile()
		);
		assertEquals(new File("undefined/home/.local/share"),
			dirs.getGlobalDataDirFile()
		);
		assertEquals(new File("undefined/home/.cache"),
			dirs.getGlobalCacheDirFile()
		);
		assertEquals(new File("undefined/tmp"), dirs.getGlobalTempDirFile());
		assertEquals(new File("undefined/home/.local/share"),
			dirs.getGlobalLogDirFile()
		);

		assertEquals(new File("undefined/home/.config/afl"),
			dirs.getConfigDirFile("afl")
		);
		assertEquals(new File("undefined/home/.local/share/afl/data"),
			dirs.getDataDirFile("afl")
		);
		assertEquals(new File("undefined/home/.cache/afl"),
			dirs.getCacheDirFile("afl")
		);
		assertEquals(new File("undefined/tmp/afl"),
			dirs.getTempDirFile("afl")
		);
		assertEquals(new File("undefined/home/.local/share/afl/log"),
			dirs.getLogDirFile("afl")
		);
	}

	@Test
	void gettersWindows(){
		final var dirs = new WindowsDirs(
			new DummyConfig(getProperties("system/winDirs.properties")),
			false
		);

		assertEquals(Path.of("/c/Users/undefined/AppData/Roaming"),
			dirs.getGlobalConfigDir()
		);
		assertEquals(Path.of("/c/Users/undefined/AppData/Roaming"),
			dirs.getGlobalDataDir()
		);
		assertEquals(Path.of("/c/Users/undefined/AppData/Roaming"),
			dirs.getGlobalCacheDir()
		);
		assertEquals(Path.of("/c/Windows/Temp"), dirs.getGlobalTempDir());
		assertEquals(Path.of("/c/Users/undefined/AppData/Roaming"),
			dirs.getGlobalLogDir()
		);

		assertEquals(Path.of("/c/Users/undefined/AppData/Roaming/afl/config"),
			dirs.getConfigDir("afl")
		);
		assertEquals(Path.of("/c/Users/undefined/AppData/Roaming/afl/data"),
			dirs.getDataDir("afl")
		);
		assertEquals(Path.of("/c/Users/undefined/AppData/Roaming/afl/cache"),
			dirs.getCacheDir("afl")
		);
		assertEquals(Path.of("/c/Windows/Temp/afl"), dirs.getTempDir("afl"));
		assertEquals(Path.of("/c/Users/undefined/AppData/Roaming/afl/log"),
			dirs.getLogDir("afl")
		);
	}
	@SuppressWarnings("deprecation")
	@Test
	void deprecatedGettersWindows(){
		final var dirs = new WindowsDirs(
			new DummyConfig(getProperties("system/winDirs.properties")),
			false
		);

		assertEquals(new File("/c/Users/undefined/AppData/Roaming"),
			dirs.getGlobalConfigDirFile()
		);
		assertEquals(new File("/c/Users/undefined/AppData/Roaming"),
			dirs.getGlobalDataDirFile()
		);
		assertEquals(new File("/c/Users/undefined/AppData/Roaming"),
			dirs.getGlobalCacheDirFile()
		);
		assertEquals(new File("/c/Windows/Temp"), dirs.getGlobalTempDirFile());
		assertEquals(new File("/c/Users/undefined/AppData/Roaming"),
			dirs.getGlobalLogDirFile()
		);

		assertEquals(new File("/c/Users/undefined/AppData/Roaming/afl/config"),
			dirs.getConfigDirFile("afl")
		);
		assertEquals(new File("/c/Users/undefined/AppData/Roaming/afl/data"),
			dirs.getDataDirFile("afl")
		);
		assertEquals(new File("/c/Users/undefined/AppData/Roaming/afl/cache"),
			dirs.getCacheDirFile("afl")
		);
		assertEquals(new File("/c/Windows/Temp/afl"),
			dirs.getTempDirFile("afl")
		);
		assertEquals(new File("/c/Users/undefined/AppData/Roaming/afl/log"),
			dirs.getLogDirFile("afl")
		);
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
	private static class DummyConfig extends Configuration{

		public DummyConfig(final Properties system){
			super(system);
		}
	}
}
