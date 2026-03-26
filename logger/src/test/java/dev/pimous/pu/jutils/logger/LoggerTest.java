package dev.pimous.pu.jutils.logger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoggerTest{

	private static final LoggerImpl l = new LoggerImpl();

	@Test
	void levelLogging(){
		l.fatal("");
		assertEquals(Level.FATAL, l.level);
		l.fatal(new RuntimeException());
		assertEquals(Level.FATAL, l.level);
		l.fatal(new RuntimeException(), "");
		assertEquals(Level.FATAL, l.level);
		l.fatalC(LoggerImpl.class);
		assertEquals(Level.FATAL, l.level);
		l.fatalC(LoggerImpl.class, "");
		assertEquals(Level.FATAL, l.level);
		l.fatalO(l);
		assertEquals(Level.FATAL, l.level);
		l.fatalO(l, "");
		assertEquals(Level.FATAL, l.level);

		l.critical("");
		assertEquals(Level.CRITICAL, l.level);
		l.critical(new RuntimeException());
		assertEquals(Level.CRITICAL, l.level);
		l.critical(new RuntimeException(), "");
		assertEquals(Level.CRITICAL, l.level);
		l.criticalC(LoggerImpl.class);
		assertEquals(Level.CRITICAL, l.level);
		l.criticalC(LoggerImpl.class, "");
		assertEquals(Level.CRITICAL, l.level);
		l.criticalO(l);
		assertEquals(Level.CRITICAL, l.level);
		l.criticalO(l, "");
		assertEquals(Level.CRITICAL, l.level);

		l.error("");
		assertEquals(Level.ERROR, l.level);
		l.error(new RuntimeException());
		assertEquals(Level.ERROR, l.level);
		l.error(new RuntimeException(), "");
		assertEquals(Level.ERROR, l.level);
		l.errorC(LoggerImpl.class);
		assertEquals(Level.ERROR, l.level);
		l.errorC(LoggerImpl.class, "");
		assertEquals(Level.ERROR, l.level);
		l.errorO(l);
		assertEquals(Level.ERROR, l.level);
		l.errorO(l, "");
		assertEquals(Level.ERROR, l.level);

		l.warn("");
		assertEquals(Level.WARNING, l.level);
		l.warn(new RuntimeException());
		assertEquals(Level.WARNING, l.level);
		l.warn(new RuntimeException(), "");
		assertEquals(Level.WARNING, l.level);
		l.warnC(LoggerImpl.class);
		assertEquals(Level.WARNING, l.level);
		l.warnC(LoggerImpl.class, "");
		assertEquals(Level.WARNING, l.level);
		l.warnO(l);
		assertEquals(Level.WARNING, l.level);
		l.warnO(l, "");
		assertEquals(Level.WARNING, l.level);

		l.notice("");
		assertEquals(Level.NOTICE, l.level);
		l.notice(new RuntimeException());
		assertEquals(Level.NOTICE, l.level);
		l.notice(new RuntimeException(), "");
		assertEquals(Level.NOTICE, l.level);
		l.noticeC(LoggerImpl.class);
		assertEquals(Level.NOTICE, l.level);
		l.noticeC(LoggerImpl.class, "");
		assertEquals(Level.NOTICE, l.level);
		l.noticeO(l);
		assertEquals(Level.NOTICE, l.level);
		l.noticeO(l, "");
		assertEquals(Level.NOTICE, l.level);

		l.info("");
		assertEquals(Level.INFORMATION, l.level);
		l.info(new RuntimeException());
		assertEquals(Level.INFORMATION, l.level);
		l.info(new RuntimeException(), "");
		assertEquals(Level.INFORMATION, l.level);
		l.infoC(LoggerImpl.class);
		assertEquals(Level.INFORMATION, l.level);
		l.infoC(LoggerImpl.class, "");
		assertEquals(Level.INFORMATION, l.level);
		l.infoO(l);
		assertEquals(Level.INFORMATION, l.level);
		l.infoO(l, "");
		assertEquals(Level.INFORMATION, l.level);

		l.debug("");
		assertEquals(Level.DEBUG, l.level);
		l.debug(new RuntimeException());
		assertEquals(Level.DEBUG, l.level);
		l.debug(new RuntimeException(), "");
		assertEquals(Level.DEBUG, l.level);
		l.debugC(LoggerImpl.class);
		assertEquals(Level.DEBUG, l.level);
		l.debugC(LoggerImpl.class, "");
		assertEquals(Level.DEBUG, l.level);
		l.debugO(l);
		assertEquals(Level.DEBUG, l.level);
		l.debugO(l, "");
		assertEquals(Level.DEBUG, l.level);

		l.trace("");
		assertEquals(Level.TRACE, l.level);
		l.trace(new RuntimeException());
		assertEquals(Level.TRACE, l.level);
		l.trace(new RuntimeException(), "");
		assertEquals(Level.TRACE, l.level);
		l.traceC(LoggerImpl.class);
		assertEquals(Level.TRACE, l.level);
		l.traceC(LoggerImpl.class, "");
		assertEquals(Level.TRACE, l.level);
		l.traceO(l);
		assertEquals(Level.TRACE, l.level);
		l.traceO(l, "");
		assertEquals(Level.TRACE, l.level);
	}

	// INNER CLASSES
	private static class LoggerImpl implements Logger{

		private Level level;

		@Override
		public String getName(){ return ""; }
		@Override
		public boolean isLoggable(Level level){ return false; }

		@Override
		public void log(Level level, String message, Object... arguments){
			this.level = level;
		}
		@Override
		public void log(Level level, Throwable throwable){
			this.level = level;
		}
		@Override
		public void log(Level level, Throwable throwable,
			String message, Object... arguments
		){
			this.level = level;
		}
		@Override
		public void logC(Level level, Class<?> clazz){
			this.level = level;
		}
		@Override
		public void logC(Level level, Class<?> clazz,
			String message, Object... arguments
		){
			this.level = level;
		}
		@Override
		public void logO(Level level, Object object){
			this.level = level;
		}
		@Override
		public void logO(Level level, Object object,
			String message, Object... arguments
		){
			this.level = level;
		}
	}
}
