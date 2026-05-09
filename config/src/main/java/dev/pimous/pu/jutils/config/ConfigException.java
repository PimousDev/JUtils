package dev.pimous.pu.jutils.config;

import java.io.Serial;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class ConfigException extends Exception{

	@Serial
	private static final long serialVersionUID = 7247375025255609137L;

	public ConfigException(String message){
		super(message);
	}
}
