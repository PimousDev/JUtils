package dev.pimous.pu.jutils.logger;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public interface Logger{

	// GETTERS
	String getName();
	boolean isLoggable(final Level level);

	// FUNCTIONS
	void log(final Level level,
		final String message, final Object... arguments
	);
	void log(final Level level,
		final Throwable throwable
	);
	void log(final Level level,
		final Throwable throwable,
		final String message, final Object... arguments
	);
	void logC(final Level level,
		final Class<?> clazz
	);
	void logC(final Level level,
		final Class<?> clazz,
		final String message, final Object... arguments
	);
	void logO(final Level level,
		final Object object
	);
	void logO(final Level level,
		final Object object,
		final String message, final Object... arguments
	);

	default void fatal(final String message, final Object... arguments){
		log(Level.FATAL, message, arguments);
	}
	default void fatal(final Throwable throwable){
		log(Level.FATAL, throwable);
	}
	default void fatal(final Throwable throwable,
		final String message, final Object... arguments
	){
		log(Level.FATAL, throwable, message, arguments);
	}
	default void fatalC(final Class<?> clazz){
		logC(Level.FATAL, clazz);
	}
	default void fatalC(final Class<?> clazz,
		final String message, final Object... arguments
	){
		logC(Level.FATAL, clazz, message, arguments);
	}
	default void fatalO(final Object object){
		logO(Level.FATAL, object);
	}
	default void fatalO(final Object object,
		final String message, final Object... arguments
	){
		logO(Level.FATAL, object, message, arguments);
	}

	default void critical(
		final String message, final Object... arguments
	){
		log(Level.CRITICAL, message, arguments);
	}
	default void critical(final Throwable throwable){
		log(Level.CRITICAL, throwable);
	}
	default void critical(final Throwable throwable,
		final String message, final Object... arguments
	){
		log(Level.CRITICAL, throwable, message, arguments);
	}
	default void criticalC(final Class<?> clazz){
		logC(Level.CRITICAL, clazz);
	}
	default void criticalC(final Class<?> clazz,
		final String message, final Object... arguments
	){
		logC(Level.CRITICAL, clazz, message, arguments);
	}
	default void criticalO(final Object object){
		logO(Level.CRITICAL, object);
	}
	default void criticalO(final Object object,
		final String message, final Object... arguments
	){
		logO(Level.CRITICAL, object, message, arguments);
	}

	default void error(
		final String message, final Object... arguments
	){
		log(Level.ERROR, message, arguments);
	}
	default void error(final Throwable throwable){
		log(Level.ERROR, throwable);
	}
	default void error(final Throwable throwable,
		final String message, final Object... arguments
	){
		log(Level.ERROR, throwable, message, arguments);
	}
	default void errorC(final Class<?> clazz){
		logC(Level.ERROR, clazz);
	}
	default void errorC(final Class<?> clazz,
		final String message, final Object... arguments
	){
		logC(Level.ERROR, clazz, message, arguments);
	}
	default void errorO(final Object object){
		logO(Level.ERROR, object);
	}
	default void errorO(final Object object,
		final String message, final Object... arguments
	){
		logO(Level.ERROR, object, message, arguments);
	}

	default void warn(final String message, final Object... arguments){
		log(Level.WARNING, message, arguments);
	}
	default void warn(final Throwable throwable){
		log(Level.WARNING, throwable);
	}
	default void warn(final Throwable throwable,
		final String message, final Object... arguments
	){
		log(Level.WARNING, throwable, message, arguments);
	}
	default void warnC(final Class<?> clazz){
		logC(Level.WARNING, clazz);
	}
	default void warnC(final Class<?> clazz,
		final String message, final Object... arguments
	){
		logC(Level.WARNING, clazz, message, arguments);
	}
	default void warnO(final Object object){
		logO(Level.WARNING, object);
	}
	default void warnO(final Object object,
		final String message, final Object... arguments
	){
		logO(Level.WARNING, object, message, arguments);
	}

	default void notice(final String message, final Object... arguments){
		log(Level.NOTICE, message, arguments);
	}
	default void notice(final Throwable throwable){
		log(Level.NOTICE, throwable);
	}
	default void notice(final Throwable throwable,
		final String message, final Object... arguments
	){
		log(Level.NOTICE, throwable, message, arguments);
	}
	default void noticeC(final Class<?> clazz){
		logC(Level.NOTICE, clazz);
	}
	default void noticeC(final Class<?> clazz,
		final String message, final Object... arguments
	){
		logC(Level.NOTICE, clazz, message, arguments);
	}
	default void noticeO(final Object object){
		logO(Level.NOTICE, object);
	}
	default void noticeO(final Object object,
		final String message, final Object... arguments
	){
		logO(Level.NOTICE, object, message, arguments);
	}

	default void info(final String message, final Object... arguments){
		log(Level.INFORMATION, message, arguments);
	}
	default void info(final Throwable throwable){
		log(Level.INFORMATION, throwable);
	}
	default void info(final Throwable throwable,
		final String message, final Object... arguments
	){
		log(Level.INFORMATION, throwable, message, arguments);
	}
	default void infoC(final Class<?> clazz){
		logC(Level.INFORMATION, clazz);
	}
	default void infoC(final Class<?> clazz,
		final String message, final Object... arguments
	){
		logC(Level.INFORMATION, clazz, message, arguments);
	}
	default void infoO(final Object object){
		logO(Level.INFORMATION, object);
	}
	default void infoO(final Object object,
		final String message, final Object... arguments
	){
		logO(Level.INFORMATION, object, message, arguments);
	}

	default void debug(final String message, final Object... arguments){
		log(Level.DEBUG, message, arguments);
	}
	default void debug(final Throwable throwable){
		log(Level.DEBUG, throwable);
	}
	default void debug(final Throwable throwable,
		final String message, final Object... arguments
	){
		log(Level.DEBUG, throwable, message, arguments);
	}
	default void debugC(final Class<?> clazz){
		logC(Level.DEBUG, clazz);
	}
	default void debugC(final Class<?> clazz,
		final String message, final Object... arguments
	){
		logC(Level.DEBUG, clazz, message, arguments);
	}
	default void debugO(final Object object){
		logO(Level.DEBUG, object);
	}
	default void debugO(final Object object,
		final String message, final Object... arguments
	){
		logO(Level.DEBUG, object, message, arguments);
	}

	default void trace(final String message, final Object... arguments){
		log(Level.TRACE, message, arguments);
	}
	default void trace(final Throwable throwable){
		log(Level.TRACE, throwable);
	}
	default void trace(final Throwable throwable,
		final String message, final Object... arguments
	){
		log(Level.TRACE, throwable, message, arguments);
	}
	default void traceC(final Class<?> clazz){
		logC(Level.TRACE, clazz);
	}
	default void traceC(final Class<?> clazz,
		final String message, final Object... arguments
	){
		logC(Level.TRACE, clazz, message, arguments);
	}
	default void traceO(final Object object){
		logO(Level.TRACE, object);
	}
	default void traceO(final Object object,
		final String message, final Object... arguments
	){
		logO(Level.TRACE, object, message, arguments);
	}
}
