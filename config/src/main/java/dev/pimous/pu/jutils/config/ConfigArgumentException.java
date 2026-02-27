package dev.pimous.pu.jutils.config;

public class ConfigArgumentException extends ConfigException{

	public ConfigArgumentException(Throwable cause){
		super(cause);
	}
	public ConfigArgumentException(String message, Throwable cause){
		super(message, cause);
	}
	protected ConfigArgumentException(String message){
		super(message);
	}
}
