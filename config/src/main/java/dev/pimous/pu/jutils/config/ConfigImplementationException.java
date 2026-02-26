package dev.pimous.pu.jutils.config;

import dev.pimous.pu.jutils.base.InternalException;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class ConfigImplementationException extends InternalException{

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
