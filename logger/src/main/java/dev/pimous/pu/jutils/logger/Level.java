package dev.pimous.pu.jutils.logger;

import java.util.Arrays;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public enum Level{

	FATAL((byte) 0),
	CRITICAL((byte) 1),
	ERROR((byte) 2),
	WARNING((byte) 3),
	NOTICE((byte) 4),
	INFORMATION((byte) 5),
	DEBUG((byte) 6),
	TRACE((byte) 7);

	public static final Level WARN = WARNING;
	public static final Level INFO = INFORMATION;

	public static final byte OFF = -1;
	public static final byte VERBOSE = INFORMATION.code;
	public static final byte ALL = TRACE.code;

	public final byte code;

	private Level(final byte code){
		this.code = code;
	}

	// GETTERS
	public static Level getLevel(final byte code){
		return Arrays.stream(Level.values())
			.filter(l -> l.code == code)
			.findFirst()
			.get();
	}
	public static Level getLevel(final int code){
		return getLevel((byte) code);
	}
}
