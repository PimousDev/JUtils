package dev.pimous.pu.jutils.app;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.logging.*;

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
		lr.setInstant(Instant.ofEpochSecond(1580505243, 24310120));

		Formatter f = new AppLoggerFormatter(false);
		assertTrue(f.getHead(new StreamHandler()).matches(
			"^STARTED logging at \\d{4}/\\d{2}/\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{9}:$"
		));
		assertTrue(f.getTail(new StreamHandler()).matches(
			"^FINISHED logging at \\d{4}/\\d{2}/\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{9}\\.$"
		));
		assertTrue(f.format(lr).matches(
			"\\(\\d+ - 2020/01/31T22:14:03.024310120\\)\\{T:3}\\[SEVERE] test\n"
		));
		lr.setThrown(t);
		assertTrue(f.format(lr).matches(
			"\\(\\d+ - 2020/01/31T22:14:03.024310120\\)\\{T:3}\\[SEVERE] test\n" +
				"java\\.io\\.FileNotFoundException\n" +
				"\tat dev\\.pimous\\.App\\.run\\(App\\.java:5\\)\n"
		));

		f = new AppLoggerFormatter(true);
		assertTrue(f.getHead(new StreamHandler()).isEmpty());
		assertTrue(f.getTail(new StreamHandler()).isEmpty());
		assertTrue(f.format(lr).matches(
			"\\(\\d+ - 2020/01/31T22:14:03.024310120\\)\\{T:3}\\[\u001b\\[1;38;5;1mSEVERE\u001b\\[0m] test\n" +
				"java\\.io\\.FileNotFoundException\n" +
				"\tat dev\\.pimous\\.App\\.run\\(App\\.java:5\\)\n"
		));
		lr.setThrown(null);
		assertTrue(f.format(lr).matches(
			"\\(\\d+ - 2020/01/31T22:14:03.024310120\\)\\{T:3}\\[\u001b\\[1;38;5;1mSEVERE\u001b\\[0m] test\n"
		));
	}
}
