package dev.pimous.pu.jutils.logger;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public interface Logger{

	// GETTERS
	public abstract String getName();
	public abstract boolean isLoggable(final Level level);

	// FUNCTIONS
	public abstract void log(final Level level,
		final String message, final Object ...arguments
	);
	public abstract void log(final Level level,
		final Throwable throwable
	);
	public abstract void log(final Level level,
		final Throwable throwable,
		final String message, final Object ...arguments
	);
	public abstract void logC(final Level level,
		final Class<?> clazz
	);
	public abstract void logC(final Level level,
		final Class<?> clazz,
		final String message, final Object ...arguments
	);
	public abstract void logO(final Level level,
		final Object object
	);
	public abstract void logO(final Level level,
		final Object object,
		final String message, final Object ...arguments
	);

	public default void fatal(final String message, final Object ...arguments){
		log(Level.FATAL, message, arguments);
	}
	public default void fatal(final Throwable throwable){
		log(Level.FATAL, throwable);
	}
	public default void fatal(final Throwable throwable,
		final String message, final Object ...arguments
	){
		log(Level.FATAL, throwable, message, arguments);
	}
	public default void fatalC(final Class<?> clazz){
		logC(Level.FATAL, clazz);
	}
	public default void fatalC(final Class<?> clazz,
		final String message, final Object ...arguments
	){
		logC(Level.FATAL, clazz, message, arguments);
	}
	public default void fatalO(final Object object){
		logO(Level.FATAL, object);
	}
	public default void fatalO(final Object object,
		final String message, final Object ...arguments
	){
		logO(Level.FATAL, object, message, arguments);
	}

	public default void critical(
		final String message, final Object ...arguments
	){
		log(Level.CRITICAL, message, arguments);
	}
	public default void critical(final Throwable throwable){
		log(Level.CRITICAL, throwable);
	}
	public default void critical(final Throwable throwable,
		final String message, final Object ...arguments
	){
		log(Level.CRITICAL, throwable, message, arguments);
	}
	public default void criticalC(final Class<?> clazz){
		logC(Level.CRITICAL, clazz);
	}
	public default void criticalC(final Class<?> clazz,
		final String message, final Object ...arguments
	){
		logC(Level.CRITICAL, clazz, message, arguments);
	}
	public default void criticalO(final Object object){
		logO(Level.CRITICAL, object);
	}
	public default void criticalO(final Object object,
		final String message, final Object ...arguments
	){
		logO(Level.CRITICAL, object, message, arguments);
	}

	public default void error(
		final String message, final Object ...arguments
	){
		log(Level.ERROR, message, arguments);
	}
	public default void error(final Throwable throwable){
		log(Level.ERROR, throwable);
	}
	public default void error(final Throwable throwable,
		final String message, final Object ...arguments
	){
		log(Level.ERROR, throwable, message, arguments);
	}
	public default void errorC(final Class<?> clazz){
		logC(Level.ERROR, clazz);
	}
	public default void errorC(final Class<?> clazz,
		final String message, final Object ...arguments
	){
		logC(Level.ERROR, clazz, message, arguments);
	}
	public default void errorO(final Object object){
		logO(Level.ERROR, object);
	}
	public default void errorO(final Object object,
		final String message, final Object ...arguments
	){
		logO(Level.ERROR, object, message, arguments);
	}

	public default void warn(final String message, final Object ...arguments){
		log(Level.WARNING, message, arguments);
	}
	public default void warn(final Throwable throwable){
		log(Level.WARNING, throwable);
	}
	public default void warn(final Throwable throwable,
		final String message, final Object ...arguments
	){
		log(Level.WARNING, throwable, message, arguments);
	}
	public default void warnC(final Class<?> clazz){
		logC(Level.WARNING, clazz);
	}
	public default void warnC(final Class<?> clazz,
		final String message, final Object ...arguments
	){
		logC(Level.WARNING, clazz, message, arguments);
	}
	public default void warnO(final Object object){
		logO(Level.WARNING, object);
	}
	public default void warnO(final Object object,
		final String message, final Object ...arguments
	){
		logO(Level.WARNING, object, message, arguments);
	}

	public default void notice(final String message, final Object ...arguments){
		log(Level.NOTICE, message, arguments);
	}
	public default void notice(final Throwable throwable){
		log(Level.NOTICE, throwable);
	}
	public default void notice(final Throwable throwable,
		final String message, final Object ...arguments
	){
		log(Level.NOTICE, throwable, message, arguments);
	}
	public default void noticeC(final Class<?> clazz){
		logC(Level.NOTICE, clazz);
	}
	public default void noticeC(final Class<?> clazz,
		final String message, final Object ...arguments
	){
		logC(Level.NOTICE, clazz, message, arguments);
	}
	public default void noticeO(final Object object){
		logO(Level.NOTICE, object);
	}
	public default void noticeO(final Object object,
		final String message, final Object ...arguments
	){
		logO(Level.NOTICE, object, message, arguments);
	}

	public default void info(final String message, final Object ...arguments){
		log(Level.INFORMATION, message, arguments);
	}
	public default void info(final Throwable throwable){
		log(Level.INFORMATION, throwable);
	}
	public default void info(final Throwable throwable,
		final String message, final Object ...arguments
	){
		log(Level.INFORMATION, throwable, message, arguments);
	}
	public default void infoC(final Class<?> clazz){
		logC(Level.INFORMATION, clazz);
	}
	public default void infoC(final Class<?> clazz,
		final String message, final Object ...arguments
	){
		logC(Level.INFORMATION, clazz, message, arguments);
	}
	public default void infoO(final Object object){
		logO(Level.INFORMATION, object);
	}
	public default void infoO(final Object object,
		final String message, final Object ...arguments
	){
		logO(Level.INFORMATION, object, message, arguments);
	}

	public default void debug(final String message, final Object ...arguments){
		log(Level.DEBUG, message, arguments);
	}
	public default void debug(final Throwable throwable){
		log(Level.DEBUG, throwable);
	}
	public default void debug(final Throwable throwable,
		final String message, final Object ...arguments
	){
		log(Level.DEBUG, throwable, message, arguments);
	}
	public default void debugC(final Class<?> clazz){
		logC(Level.DEBUG, clazz);
	}
	public default void debugC(final Class<?> clazz,
		final String message, final Object ...arguments
	){
		logC(Level.DEBUG, clazz, message, arguments);
	}
	public default void debugO(final Object object){
		logO(Level.DEBUG, object);
	}
	public default void debugO(final Object object,
		final String message, final Object ...arguments
	){
		logO(Level.DEBUG, object, message, arguments);
	}

	public default void trace(final String message, final Object ...arguments){
		log(Level.TRACE, message, arguments);
	}
	public default void trace(final Throwable throwable){
		log(Level.TRACE, throwable);
	}
	public default void trace(final Throwable throwable,
		final String message, final Object ...arguments
	){
		log(Level.TRACE, throwable, message, arguments);
	}
	public default void traceC(final Class<?> clazz){
		logC(Level.TRACE, clazz);
	}
	public default void traceC(final Class<?> clazz,
		final String message, final Object ...arguments
	){
		logC(Level.TRACE, clazz, message, arguments);
	}
	public default void traceO(final Object object){
		logO(Level.TRACE, object);
	}
	public default void traceO(final Object object,
		final String message, final Object ...arguments
	){
		logO(Level.TRACE, object, message, arguments);
	}
}
