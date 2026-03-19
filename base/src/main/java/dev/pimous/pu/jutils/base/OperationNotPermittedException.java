package dev.pimous.pu.jutils.base;

import java.io.Serial;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class OperationNotPermittedException extends InternalException{

	@Serial
	private static final long serialVersionUID = 6497150854528146844L;

	public OperationNotPermittedException(Throwable cause){
		super(cause);
	}
	public OperationNotPermittedException(String message, Throwable cause){
		super(message, cause);
	}
	protected OperationNotPermittedException(String message){
		super(message);
	}
}
