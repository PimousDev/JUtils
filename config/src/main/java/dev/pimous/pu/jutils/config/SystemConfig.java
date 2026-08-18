package dev.pimous.pu.jutils.config;

import dev.pimous.pu.jutils.base.InternalException;

import java.io.File;
import java.nio.file.Path;
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
	private Path workingDir;
	@ConfigField(property="user.home", mandatory=true)
	private Path home;
	@ConfigField(property="user.language")
	private String language;
	@ConfigField(property="user.country")
	private String country;
	@ConfigField(property="user.timezone")
	private String timezone;

	@ConfigField(property="java.io.tmpdir", mandatory=true)
	private Path tmpdir;

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
			case "user.dir", "user.home", "java.io.tmpdir" -> Path::of;
			default -> super.getParser(property);
		};
	}

	public String getOSName(){ return osName; }

	@Deprecated
	public File getWorkingDirFile(){ return workingDir.toFile(); }
	/** @since 1.1.0 */
	public Path getWorkingDir(){ return workingDir; }
	@Deprecated
	public File getHomeFile(){ return home.toFile(); }
	/** @since 1.1.0 */
	public Path getHome(){ return home; }
	public Optional<String> getLanguage(){
		return Optional.ofNullable(language);
	}
	public Optional<String> getCountry(){
		return Optional.ofNullable(country);
	}
	public Optional<String> getTimezone(){
		return Optional.ofNullable(timezone);
	}

	@Deprecated
	public File getTmpDirFile(){ return this.tmpdir.toFile(); }
	/** @since 1.1.0 */
	public Path getTmpDir(){ return this.tmpdir; }
}
