package dev.pimous.pu.jutils.base;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class BadResourceException extends InternalException{

	private static final String MESSAGE_PATH_FORMAT = "%s (%s);";

	public BadResourceException(String path, Throwable cause){
		this("No such or corrupt resource", path, cause);
	}
	public BadResourceException(String message, String path, Throwable cause){
		super(MESSAGE_PATH_FORMAT.formatted(message, path), cause);
	}
}