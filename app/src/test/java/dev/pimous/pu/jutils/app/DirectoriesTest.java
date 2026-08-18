package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.app.dirs.LinuxDirs;
import dev.pimous.pu.jutils.app.dirs.LocalDirs;
import dev.pimous.pu.jutils.config.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectoriesTest{

	@Test
	void getters(){
		Directories dirs = new LocalDirs(
			new DummyConfig(getProperties("system/dirs.properties")),
			true
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

		// TODO: All deprecated
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

		dirs = new LinuxDirs(
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

		// TODO: All deprecated
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
		assertEquals(new File("undefined/tmp/afl"), dirs.getTempDirFile("afl"));
		assertEquals(new File("undefined/home/.local/share/afl/log"),
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
