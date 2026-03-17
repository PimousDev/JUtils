package dev.pimous.pu.jutils.app;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static org.junit.jupiter.api.Assertions.*;

class AppLoggerFormatterTest{

	@Test
	void formatting(){
		final Throwable t = new FileNotFoundException();
		t.setStackTrace(new StackTraceElement[]{
			new StackTraceElement("dev.pimous.App", "run", "App.java", 5)
		});
		final LogRecord lr = new LogRecord(
			Level.SEVERE,
			"test"
		);
		lr.setInstant(Instant.ofEpochMilli(1580505243101L));

		Formatter f = new AppLoggerFormatter(false);
		assertEquals(
			"(0 - 01/31/20T22:14:03.101000000){T:3}[SEVERE] test\n",
			f.format(lr)
		);
		lr.setThrown(t);
		assertEquals(
			"(0 - 01/31/20T22:14:03.101000000){T:3}[SEVERE] test\n" +
				"java.io.FileNotFoundException\n" +
				"\tat dev.pimous.App.run(App.java:5)\n",
			f.format(lr)
		);

		f = new AppLoggerFormatter(true);
		assertEquals(
			"(0 - 01/31/20T22:14:03.101000000){T:3}[\u001b[1;38;5;1mSEVERE\u001b[0m] test\n" +
				"java.io.FileNotFoundException\n" +
				"\tat dev.pimous.App.run(App.java:5)\n",
			f.format(lr)
		);
		lr.setThrown(null);
		assertEquals(
			"(0 - 01/31/20T22:14:03.101000000){T:3}[\u001b[1;38;5;1mSEVERE\u001b[0m] test\n",
			f.format(lr)
		);
	}
}
