package dev.pimous.pu.jutils.base;

import java.io.Serial;

/** An exception relative to internal implementation issues.
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class InternalException extends RuntimeException{

	@Serial
	private static final long serialVersionUID = 8506515975301024720L;

	public InternalException(Throwable cause){
		super(cause);
	}
	public InternalException(String message, Throwable cause){
		super(message, cause);
	}
	protected InternalException(String message){
		super(message);
	}
	protected InternalException(){
		super();
	}
}
