package dev.pimous.pu.jutils.config;

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
