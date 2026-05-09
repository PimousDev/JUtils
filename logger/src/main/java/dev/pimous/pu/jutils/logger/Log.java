package dev.pimous.pu.jutils.logger;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public record Log<T>(
	long universalNumber,
	long number,
	Instant instant,
	Thread thread,
	Level level,
	String message, Object[] arguments,
	T supplement
){

	private static final AtomicLong UNIVERSAL_NUMBER = new AtomicLong();

	@SuppressWarnings("unused")
	public Log(final long number, final Thread thread,
		final Level level,
		final String message, final Object[] arguments,
		final T supplement
	){
		this(
			UNIVERSAL_NUMBER.getAndIncrement(), number,
			Instant.now(),
			Thread.currentThread(),
			level,
			message, arguments,
			supplement
		);
	}
}
