package dev.pimous.pu.jutils.logger;

import dev.pimous.pu.jutils.base.InternalException;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.*;

class JULAdapterTest{

	@Test
	void getters(){
		final java.util.logging.Logger jul
			= java.util.logging.Logger.getLogger("afl");
		jul.setLevel(java.util.logging.Level.INFO);
		final Logger l = new JULAdapter(jul);

		assertEquals("afl", l.getName());
		assertTrue(l.isLoggable(Level.FATAL));
		assertTrue(l.isLoggable(Level.CRITICAL));
		assertTrue(l.isLoggable(Level.ERROR));
		assertTrue(l.isLoggable(Level.WARNING));
		assertTrue(l.isLoggable(Level.NOTICE));
		assertFalse(l.isLoggable(Level.INFORMATION));
		assertFalse(l.isLoggable(Level.DEBUG));
		assertFalse(l.isLoggable(Level.TRACE));
	}

	@Test
	void logging(){
		final java.util.logging.Logger jul
			= java.util.logging.Logger.getLogger("afl");
		jul.setLevel(java.util.logging.Level.FINEST);
		jul.setUseParentHandlers(false);
		final BufferHandler bh = new BufferHandler(new SimpleFormatter());
		jul.addHandler(bh);
		final Logger l = new JULAdapter(jul);

		l.log(Level.FATAL, "This is a {0} inches test.", 31);
		assertEquals(java.util.logging.Level.SEVERE, bh.lastRecord.getLevel());
		assertEquals("This is a {0} inches test.", bh.lastRecord.getMessage());
		assertArrayEquals(new Object[]{31}, bh.lastRecord.getParameters());
		assertEquals(java.util.logging.Level.SEVERE, bh.lastRecord.getLevel());

		final Throwable t = new InternalException("Problem.",
			new FileNotFoundException("Problem cause.")
		);
		l.log(Level.FATAL, t);
		assertEquals(java.util.logging.Level.SEVERE, bh.lastRecord.getLevel());
		assertEquals(t, bh.lastRecord.getThrown());
		l.log(Level.FATAL, t, "Ooooh there is a %d problem!", 24);
		assertEquals(java.util.logging.Level.SEVERE, bh.lastRecord.getLevel());
		assertEquals("Ooooh there is a 24 problem!",
			bh.lastRecord.getMessage()
		);
		assertEquals(t, bh.lastRecord.getThrown());

		l.logC(Level.FATAL, A.class);
		assertEquals(java.util.logging.Level.SEVERE, bh.lastRecord.getLevel());
		assertEquals(
			"dev.pimous.pu.jutils.logger.JULAdapterTest$A{f=\"f\"}",
			bh.lastRecord.getMessage()
		);
		l.logC(Level.FATAL, A.class, "Ooooh there is a %d problem!", 24);
		assertEquals(java.util.logging.Level.SEVERE, bh.lastRecord.getLevel());
		assertEquals(
			"Ooooh there is a 24 problem!\ndev.pimous.pu.jutils.logger.JULAdapterTest$A{f=\"f\"}",
			bh.lastRecord.getMessage()
		);

		l.logO(Level.FATAL, new A());
		assertEquals(java.util.logging.Level.SEVERE, bh.lastRecord.getLevel());
		assertEquals(
			"dev.pimous.pu.jutils.logger.JULAdapterTest$A(1){l=\"l\"}",
			bh.lastRecord.getMessage()
		);
		l.logO(Level.FATAL, new A(), "Ooooh there is a %d problem!", 24);
		assertEquals(java.util.logging.Level.SEVERE, bh.lastRecord.getLevel());
		assertEquals(
			"Ooooh there is a 24 problem!\ndev.pimous.pu.jutils.logger.JULAdapterTest$A(1){l=\"l\"}",
			bh.lastRecord.getMessage()
		);
	}

	// INNER CLASSES
	private static class BufferHandler extends Handler{

		private LogRecord lastRecord;

		public BufferHandler(Formatter formatter){
			setFormatter(formatter);
		}

		// GETTERS
		public LogRecord getLastRecord(){
			return lastRecord;
		}

		// FUNCTIONS
		@Override
		public void publish(LogRecord record){
			lastRecord = record;
		}
		@Override
		public void flush(){}
		@Override
		public void close(){}
	}

	private static class A{

		private static String f = "f";
		private String l = "l";

		// FUNCTIONS
		@Override
		public int hashCode(){
			return l.length();
		}
	}
}
