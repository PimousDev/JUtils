package dev.pimous.pu.jutils.config;

import dev.pimous.pu.jutils.base.InternalException;

import java.io.File;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public final class SystemConfig extends ConfigSection{

	@ConfigField(property="os.name", mandatory=true)
	private String osName;

	@ConfigField(property="user.dir", mandatory=true)
	private File workingDir;
	@ConfigField(property="user.home", mandatory=true)
	private File home;
	@ConfigField(property="user.language")
	private String language;
	@ConfigField(property="user.country")
	private String country;
	@ConfigField(property="user.timezone")
	private String timezone;

	@ConfigField(property="java.io.tmpdir", mandatory=true)
	private File tmpdir;

	public SystemConfig(final Properties properties){
		try{
			load(properties);
		}catch(ConfigPropertyException e){
			throw new InternalException(e);
		}
	}

	// GETTERS
	@Override
	protected Function<String, ?> getParser(String property){
		return switch(property){
			case "user.dir", "user.home", "java.io.tmpdir" -> File::new;
			default -> super.getParser(property);
		};
	}

	public String getOSName(){ return osName; }

	public File getWorkingDir(){ return workingDir; }
	public File getHome(){ return home; }
	public Optional<String> getLanguage(){
		return Optional.ofNullable(language);
	}
	public Optional<String> getCountry(){
		return Optional.ofNullable(country);
	}
	public Optional<String> getTimezone(){
		return Optional.ofNullable(timezone);
	}

	public File getTmpDir(){ return this.tmpdir; }
}
