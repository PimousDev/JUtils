package dev.pimous.pu.jutils.config;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class ConfigPropertyException extends ConfigException{

	public ConfigPropertyException(Throwable cause){
		super(cause);
	}
	public ConfigPropertyException(String message, Throwable cause){
		super(message, cause);
	}
	protected ConfigPropertyException(String message){
		super(message);
	}
}
