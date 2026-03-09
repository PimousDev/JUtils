package dev.pimous.pu.jutils.logger;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest{

	@Test
	void codes(){
		assertEquals(0, Level.FATAL.code);
		assertEquals(1, Level.CRITICAL.code);
		assertEquals(2, Level.ERROR.code);
		assertEquals(3, Level.WARNING.code);
		assertEquals(4, Level.NOTICE.code);
		assertEquals(5, Level.INFORMATION.code);
		assertEquals(6, Level.DEBUG.code);
		assertEquals(7, Level.TRACE.code);

		assertEquals(Level.OFF, -1);
		assertEquals(Level.VERBOSE, 5);
		assertEquals(Level.TRACE, 7);
	}
	@Test
	void aliases(){
		assertEquals(Level.WARNING, Level.WARN);
		assertEquals(Level.INFO, Level.INFORMATION);
	}

	@Test
	void codeGetter(){
		assertEquals(Level.FATAL, Level.getLevel(0));
		assertThrows(NoSuchElementException.class,
			() -> Level.getLevel(Level.OFF)
		);
	}
}
