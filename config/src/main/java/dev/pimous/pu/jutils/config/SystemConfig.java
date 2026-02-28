package dev.pimous.pu.jutils.config;

import dev.pimous.pu.jutils.base.InternalException;

import java.util.Optional;
import java.util.Properties;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public final class SystemConfig extends ConfigSection{

	@ConfigField(property="os.name", mandatory = true)
	private String osName;

	@ConfigField(property="user.home", mandatory = true)
	private String home;
	@ConfigField(property="user.language")
	private String language;
	@ConfigField(property="user.country")
	private String country;
	@ConfigField(property="user.timezone")
	private String timezone;

	public SystemConfig(final Properties properties){
		try{
			load(properties);
		}catch(ConfigPropertyException e){
			throw new InternalException(e);
		}
	}

	// GETTERS
	public String getOSName(){ return osName; }

	public String getHome(){ return home; }
	public Optional<String> getLanguage(){
		return Optional.ofNullable(language);
	}
	public Optional<String> getCountry(){
		return Optional.ofNullable(country);
	}
	public Optional<String> getTimezone(){
		return Optional.ofNullable(timezone);
	}
}
