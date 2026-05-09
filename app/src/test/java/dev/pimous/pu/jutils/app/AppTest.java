package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.config.Configuration;
import dev.pimous.pu.jutils.config.Version;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class AppTest{

	@Test
	void getters(){
		final App<Configuration> app = new TestApp(31);

		assertEquals(System.in, app.in);
		assertEquals(System.out, app.out);

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
		assertNotNull(app.getExecutor());
		// FIXME: Unable to test whether a pool of exactly 31 alive threads has been created.
//		assertEquals(31,
//			((ScheduledThreadPoolExecutor) app.getExecutor()).getPoolSize()
//		);
		assertFalse(app.isLoaded());

		assertNull(app.getConfig());
		assertThrows(NullPointerException.class, app::getI18n);
		assertEquals(ZoneId.of("UTC"), app.getTimeZone().toZoneId());
	}

	@Test
	void running(){
		final TestApp app = new TestApp(0);

		app.run(new String[]{"afl"});
		assertEquals(1, app.argc);

		app.run(System.console(), new String[]{"a", "f", "l"});
		assertEquals(3, app.argc);
		assertNull(app.console);
	}

	@Test
	void executor(){
		TestApp app = new TestApp(0);
		app.shutdownExecutor();
		assertTrue(app.getExecutor().isShutdown());

		app = new TestApp(0);
		app.shutdownExecutorNow();
		assertTrue(app.getExecutor().isShutdown());
	}

	// INNER CLASSES
	private static class TestApp extends App<Configuration>{

		public Console console;
		public int argc;

		public TestApp(final int threads){
			super(threads);
		}

		// FUNCTIONS
		@Override
		public void run(String[] args){
			argc = args.length;
		}
		@Override
		public void run(Console console, String[] args){
			this.console = console;
			run(args);
		}
	}
}
