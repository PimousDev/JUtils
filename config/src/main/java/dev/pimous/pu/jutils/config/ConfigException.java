package dev.pimous.pu.jutils.config;

import java.io.Serial;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class ConfigException extends Exception{

	@Serial
	private static final long serialVersionUID = 7247375025255609137L;

	public ConfigException(Throwable cause){
		super(cause);
	}
	public ConfigException(String message, Throwable cause){
		super(message, cause);
	}
	protected ConfigException(String message){
		super(message);
	}
}
