package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.app.dirs.LinuxDirs;
import dev.pimous.pu.jutils.app.dirs.LocalDirs;
import dev.pimous.pu.jutils.config.Configuration;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectoriesTest{

	@Test
	void getters(){
		Directories dirs = new LocalDirs(
			new DummyConfig(getProperties("system/dirs.properties")),
			true
		);
		assertEquals(new File("config"), dirs.getGlobalConfigDir());
		assertEquals(new File("data"), dirs.getGlobalDataDir());
		assertEquals(new File("cache"), dirs.getGlobalCacheDir());
		assertEquals(new File("tmp"), dirs.getGlobalTempDir());
		assertEquals(new File("log"), dirs.getGlobalLogDir());
		assertEquals(new File("config"), dirs.getConfigDir("afl"));
		assertEquals(new File("data"), dirs.getDataDir("afl"));
		assertEquals(new File("cache"), dirs.getCacheDir("afl"));
		assertEquals(new File("tmp"), dirs.getTempDir("afl"));
		assertEquals(new File("log"), dirs.getLogDir("afl"));

		dirs = new LinuxDirs(
			new DummyConfig(getProperties("system/linuxDirs.properties")),
			false
		);
		assertEquals(new File("undefined/home/.config"),
			dirs.getGlobalConfigDir()
		);
		assertEquals(new File("undefined/home/.local/share"),
			dirs.getGlobalDataDir()
		);
		assertEquals(new File("undefined/home/.cache"),
			dirs.getGlobalCacheDir()
		);
		assertEquals(new File("undefined/tmp"), dirs.getGlobalTempDir());
		assertEquals(new File("undefined/home/.local/share"),
			dirs.getGlobalLogDir()
		);
		assertEquals(new File("undefined/home/.config/afl"),
			dirs.getConfigDir("afl")
		);
		assertEquals(new File("undefined/home/.local/share/afl/data"),
			dirs.getDataDir("afl")
		);
		assertEquals(new File("undefined/home/.cache/afl"),
			dirs.getCacheDir("afl")
		);
		assertEquals(new File("undefined/tmp/afl"), dirs.getTempDir("afl"));
		assertEquals(new File("undefined/home/.local/share/afl/log"),
			dirs.getLogDir("afl")
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
