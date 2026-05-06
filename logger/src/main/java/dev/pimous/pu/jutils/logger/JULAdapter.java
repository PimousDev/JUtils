package dev.pimous.pu.jutils.logger;

import dev.pimous.pu.jutils.base.IntrospectiveStringifier;
import dev.pimous.pu.jutils.logger.jul.JULLevel;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class JULAdapter implements Logger{

	private final java.util.logging.Logger logger;

	public JULAdapter(final java.util.logging.Logger logger){
		this.logger = logger;
	}

	// GETTERS
	@Override
	public String getName(){ return logger.getName(); }
	@Override
	public boolean isLoggable(final Level level){
		return logger.isLoggable(mapLevel(level));
	}

	// FUNCTIONS
	public static java.util.logging.Level mapLevel(final Level level){
		return switch(level){
			case FATAL -> JULLevel.FATAL;
			case CRITICAL -> JULLevel.CRITICAL;
			case ERROR -> JULLevel.ERROR;
			case WARNING -> JULLevel.WARNING;
			case NOTICE -> JULLevel.NOTICE;
			case INFORMATION -> JULLevel.INFORMATION;
			case DEBUG -> JULLevel.DEBUG;
			case TRACE -> JULLevel.TRACE;
		};
	}

	public void log(final Level level,
		final String message, final Object ...arguments
	){
		logger.log(mapLevel(level), message, arguments);
	}
	public void log(final Level level, final Throwable throwable){
		logger.log(mapLevel(level), throwable.getMessage(), throwable);
	}
	public void log(final Level level,
		final Throwable throwable,
		final String message, final Object ...arguments
	){
		logger.log(mapLevel(level), message.formatted(arguments), throwable);
	}
	public void logC(final Level level, final Class<?> clazz){
		log(level, IntrospectiveStringifier.fromAll(clazz));
	}
	public void logC(final Level level,
		final Class<?> clazz,
		final String message, final Object ...arguments
	){
		log(level,
			message.formatted(arguments)
				+ "\n"
				+ IntrospectiveStringifier.fromAll(clazz)
		);
	}
	public void logO(final Level level, final Object object){
		log(level, IntrospectiveStringifier.fromAll(object));
	}
	public void logO(final Level level,
		final Object object,
		final String message, final Object ...arguments
	){
		log(level,
			message.formatted(arguments)
				+ "\n"
				+ IntrospectiveStringifier.fromAll(object)
		);
	}
}
