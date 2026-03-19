package dev.pimous.pu.jutils.config;

import dev.pimous.pu.jutils.base.InternalException;

import java.io.Serial;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class ConfigImplementationException extends InternalException{

	@Serial
	private static final long serialVersionUID = 4720453774831240204L;

	public ConfigImplementationException(Throwable cause){
		super(cause);
	}
	public ConfigImplementationException(String message, Throwable cause){
		super(message, cause);
	}
	protected ConfigImplementationException(String message){
		super(message);
	}
}
