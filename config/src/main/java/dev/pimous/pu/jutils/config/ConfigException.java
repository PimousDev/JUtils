package dev.pimous.pu.jutils.config;

public class ConfigException extends Exception{

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
