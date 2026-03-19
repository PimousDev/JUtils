package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.config.Configuration;
import dev.pimous.pu.jutils.i18n.I18n;
import org.junit.jupiter.api.Test;

import java.io.Console;
import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectoriesTest{

	@Test
	void getters(){
		TestApp app = new TestApp(0);
		app.load(new DummyConfig(getProperties("system.properties")));
		assertEquals(new File("config"), app.getConfigDir());
		assertEquals(new File("data"), app.getDataDir());
		assertEquals(new File("cache"), app.getCacheDir());
		assertEquals(new File("tmp"), app.getTempDir());
		assertEquals(new File("log"), app.getLogDir());

		app = new TestApp(0);
		app.load(new DummyConfig(getProperties("linuxSystem.properties")));
		assertEquals(new File("/home/rulietta/.config/testApp"),
			app.getConfigDir()
		);
		assertEquals(new File("/home/rulietta/.local/share/testApp/data"),
			app.getDataDir()
		);
		assertEquals(new File("/home/rulietta/.cache/testApp"),
			app.getCacheDir()
		);
		assertEquals(new File("/tmp/testApp"),
			app.getTempDir()
		);
		assertEquals(new File("/home/rulietta/.local/share/testApp/log"),
			app.getLogDir()
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
	private static class TestApp extends App<Configuration>{

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
		public void load(final Configuration config){
			super.load(config, language, false);
		}

		@Override
		public void run(String[] args){}
		@Override
		public void run(Console console, String[] args){}
	}
	private static class DummyConfig extends Configuration{

		public DummyConfig(final Properties system){
			super(null, system);
		}
	}
}
