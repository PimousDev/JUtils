package dev.pimous.pu.jutils.logger;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.Arrays;
import java.util.logging.*;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

class JULLoggerFormatterTest{

	@SuppressWarnings("LongLine")
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
		lr.setInstant(Instant.ofEpochSecond(1580505243, 24310120));

		Formatter f = new JULLoggerFormatter(false);
		assertTrue(f.getHead(new StreamHandler()).matches(
			"^STARTED logging at \\d{4}/\\d{2}/\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{9}:\n$"
		));
		assertTrue(f.getTail(new StreamHandler()).matches(
			"^FINISHED logging at \\d{4}/\\d{2}/\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{9}\\.\n$"
		));
		assertTrue(f.format(lr).matches(
			"\\(\\d+ - 2020/01/31T22:14:03.024310120\\)\\{T:3}\\[ERROR] test\n"
		));
		lr.setThrown(t);
		assertTrue(f.format(lr).matches(
			"""
			\\(\\d+ - 2020/01/31T22:14:03.024310120\\)\\{T:3}\\[ERROR] test
			java\\.io\\.FileNotFoundException
			\tat dev\\.pimous\\.App\\.run\\(App\\.java:5\\)
			"""
		));
		lr.setMessage(null);
		assertTrue(f.format(lr).matches(
			"""
			\\(\\d+ - 2020/01/31T22:14:03.024310120\\)\\{T:3}\\[ERROR] java\\.io\\.FileNotFoundException
			\tat dev\\.pimous\\.App\\.run\\(App\\.java:5\\)
			"""
		));

		f = new JULLoggerFormatter(true);
		assertTrue(f.getHead(new StreamHandler()).isEmpty());
		assertTrue(f.getTail(new StreamHandler()).isEmpty());
		assertTrue(f.format(lr).matches(
			"""
			\\(\\d+ - 2020/01/31T22:14:03.024310120\\)\\{T:3}\\[\u001b\\[1;38;5;1mERROR\u001b\\[0m] java\\.io\\.FileNotFoundException
			\tat dev\\.pimous\\.App\\.run\\(App\\.java:5\\)
			"""
		));
		lr.setMessage("test");
		assertTrue(f.format(lr).matches(
			"""
			\\(\\d+ - 2020/01/31T22:14:03.024310120\\)\\{T:3}\\[\u001b\\[1;38;5;1mERROR\u001b\\[0m] test
			java\\.io\\.FileNotFoundException
			\tat dev\\.pimous\\.App\\.run\\(App\\.java:5\\)
			"""
		));
		lr.setThrown(null);
		assertTrue(f.format(lr).matches(
			"\\(\\d+ - 2020/01/31T22:14:03.024310120\\)\\{T:3}\\[\u001b\\[1;38;5;1mERROR\u001b\\[0m] test\n"
		));
	}

	@Test
	void withSpecifier(){
		final LogRecord lr = new LogRecord(
			Level.SEVERE,
			"test %s %%s"
		);
		lr.setInstant(Instant.ofEpochSecond(1580505243, 24310120));

		Formatter f = new JULLoggerFormatter(false);
		assertEquals(
			"(0 - 2020/01/31T22:14:03.024310120){T:3}[ERROR] test %s %%s\n",
			assertDoesNotThrow(() -> f.format(lr))
		);
	}
}
