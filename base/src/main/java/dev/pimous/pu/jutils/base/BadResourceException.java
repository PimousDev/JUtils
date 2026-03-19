package dev.pimous.pu.jutils.base;

import java.io.Serial;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class BadResourceException extends InternalException{

	@Serial
	private static final long serialVersionUID = 6850334159946758557L;

	private static final String MESSAGE_PATH_FORMAT = "%s (%s);";

	public BadResourceException(String path, Throwable cause){
		this("No such or corrupt resource", path, cause);
	}
	public BadResourceException(String message, String path, Throwable cause){
		super(MESSAGE_PATH_FORMAT.formatted(message, path), cause);
	}
}