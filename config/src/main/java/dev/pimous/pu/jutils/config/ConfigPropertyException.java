package dev.pimous.pu.jutils.config;

import java.io.Serial;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class ConfigPropertyException extends ConfigException{

	@Serial
	private static final long serialVersionUID = 4686574098775400032L;

	public ConfigPropertyException(String message){
		super(message);
	}
}
