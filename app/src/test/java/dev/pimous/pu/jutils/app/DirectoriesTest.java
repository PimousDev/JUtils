package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.app.dirs.LinuxDirs;
import dev.pimous.pu.jutils.app.dirs.LocalDirs;
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
	void getters(){
		final var dirs = new LocalDirs(
			new DummyConfig(getProperties("system/dirs.properties")),
			true
		);

		assertEquals(Path.of("config"), dirs.getGlobalConfigDir());
		assertDoesNotThrow(
			() -> Files.createDirectory(dirs.getGlobalConfigDir())
		);
		assertEquals(Path.of("data"), dirs.getGlobalDataDir());
		assertDoesNotThrow(
			() -> Files.createDirectory(dirs.getGlobalDataDir())
		);
		assertEquals(Path.of("cache"), dirs.getGlobalCacheDir());
		assertDoesNotThrow(
			() -> Files.createDirectory(dirs.getGlobalCacheDir())
		);
		assertEquals(Path.of("tmp"), dirs.getGlobalTempDir());
		assertDoesNotThrow(
			() -> Files.createDirectory(dirs.getGlobalTempDir())
		);
		assertEquals(Path.of("log"), dirs.getGlobalLogDir());
		assertDoesNotThrow(
			() -> Files.createDirectory(dirs.getGlobalLogDir())
		);

		assertEquals(Path.of("config"), dirs.getConfigDir("afl"));
		assertTrue(Files.isDirectory(dirs.getConfigDir("afl")));
		assertEquals(Path.of("data"), dirs.getDataDir("afl"));
		assertTrue(Files.isDirectory(dirs.getDataDir("afl")));
		assertEquals(Path.of("cache"), dirs.getCacheDir("afl"));
		assertTrue(Files.isDirectory(dirs.getCacheDir("afl")));
		assertEquals(Path.of("tmp"), dirs.getTempDir("afl"));
		assertTrue(Files.isDirectory(dirs.getTempDir("afl")));
		assertEquals(Path.of("log"), dirs.getLogDir("afl"));
		assertTrue(Files.isDirectory(dirs.getLogDir("afl")));

		final var lDirs = new LinuxDirs(
			new DummyConfig(getProperties("system/linuxDirs.properties")),
			false
		);
		assertEquals(Path.of("undefined/home/.config"),
			lDirs.getGlobalConfigDir()
		);
		assertDoesNotThrow(
			() -> Files.createDirectories(lDirs.getGlobalConfigDir())
		);
		assertEquals(Path.of("undefined/home/.local/share"),
			lDirs.getGlobalDataDir()
		);
		assertDoesNotThrow(
			() -> Files.createDirectories(lDirs.getGlobalDataDir())
		);
		assertEquals(Path.of("undefined/home/.cache"),
			lDirs.getGlobalCacheDir()
		);
		assertDoesNotThrow(
			() -> Files.createDirectories(lDirs.getGlobalCacheDir())
		);
		assertEquals(Path.of("undefined/tmp"), lDirs.getGlobalTempDir());
		assertDoesNotThrow(
			() -> Files.createDirectories(lDirs.getGlobalTempDir())
		);
		assertEquals(Path.of("undefined/home/.local/share"),
			lDirs.getGlobalLogDir()
		);
		assertDoesNotThrow(
			() -> Files.createDirectories(lDirs.getGlobalLogDir())
		);

		assertEquals(Path.of("undefined/home/.config/afl"),
			lDirs.getConfigDir("afl")
		);
		assertTrue(Files.isDirectory(lDirs.getConfigDir("afl")));
		assertEquals(Path.of("undefined/home/.local/share/afl/data"),
			lDirs.getDataDir("afl")
		);
		assertTrue(Files.isDirectory(lDirs.getDataDir("afl")));
		assertEquals(Path.of("undefined/home/.cache/afl"),
			lDirs.getCacheDir("afl")
		);
		assertTrue(Files.isDirectory(lDirs.getCacheDir("afl")));
		assertEquals(Path.of("undefined/tmp/afl"), lDirs.getTempDir("afl"));
		assertTrue(Files.isDirectory(lDirs.getTempDir("afl")));
		assertEquals(Path.of("undefined/home/.local/share/afl/log"),
			lDirs.getLogDir("afl")
		);
		assertTrue(Files.isDirectory(lDirs.getLogDir("afl")));
	}

	// TODO: Deprecation
	@SuppressWarnings("deprecation")
	void deprecatedGetters(){
		final var dirs = new LocalDirs(
			new DummyConfig(getProperties("system/dirs.properties")),
			true
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

		final var lDirs = new LinuxDirs(
			new DummyConfig(getProperties("system/linuxDirs.properties")),
			false
		);
		assertEquals(new File("undefined/home/.config"),
			lDirs.getGlobalConfigDirFile()
		);
		assertEquals(new File("undefined/home/.local/share"),
			lDirs.getGlobalDataDirFile()
		);
		assertEquals(new File("undefined/home/.cache"),
			lDirs.getGlobalCacheDirFile()
		);
		assertEquals(new File("undefined/tmp"), lDirs.getGlobalTempDirFile());
		assertEquals(new File("undefined/home/.local/share"),
			lDirs.getGlobalLogDirFile()
		);
		assertEquals(new File("undefined/home/.config/afl"),
			lDirs.getConfigDirFile("afl")
		);
		assertEquals(new File("undefined/home/.local/share/afl/data"),
			lDirs.getDataDirFile("afl")
		);
		assertEquals(new File("undefined/home/.cache/afl"),
			lDirs.getCacheDirFile("afl")
		);
		assertEquals(new File("undefined/tmp/afl"),
			lDirs.getTempDirFile("afl")
		);
		assertEquals(new File("undefined/home/.local/share/afl/log"),
			lDirs.getLogDirFile("afl")
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
