package dev.pimous.pu.jutils.logger;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public interface Logger{

	// GETTERS
	public abstract String getName();
	public abstract boolean isEnabled(final Level level);

	// FUNCTIONS
	public abstract void log(final Level level,
		final String message, final Object ...arguments
	);
	public abstract void logThrown(final Level level,
		final Throwable throwable
	);
	public abstract void logThrown(final Level level,
		final Throwable throwable,
		final String message, final Object ...arguments
	);
	public abstract void logClass(final Level level,
		final Class<?> clazz
	);
	public abstract void logClass(final Level level,
		final Class<?> clazz,
		final String message, final Object ...arguments
	);
	public abstract void logObject(final Level level,
		final Object object
	);
	public abstract void logObject(final Level level,
		final Object object,
		final String message, final Object ...arguments
	);
}
