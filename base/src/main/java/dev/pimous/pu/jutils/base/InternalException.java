package dev.pimous.pu.jutils.base;

/** An exception relative to internal implementation issues.
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class InternalException extends RuntimeException{

	public InternalException(Throwable cause){
		super(cause);
	}
	public InternalException(String message, Throwable cause){
		super(message, cause);
	}
	protected InternalException(String message){
		super(message);
	}
}
