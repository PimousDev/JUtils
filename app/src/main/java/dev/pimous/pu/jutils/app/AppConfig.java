package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.config.ConfigSection;
import dev.pimous.pu.jutils.config.Version;

import java.util.function.Function;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class AppConfig extends ConfigSection{

	@ConfigField(mandatory = true)
	private String identifier;
	@ConfigField(mandatory = true)
	private String name;
	@ConfigField
	private String description = "";
	@ConfigField(mandatory = true)
	private Version version;
	@ConfigField
	private String license = "";
	@ConfigField
	private String author = "";

	public AppConfig(){}

	// GETTERS
	@Override
	protected Function<String, ?> getParser(final String property){
		return switch(property){
			case "version" -> Version::new;
			default -> super.getParser(property);
		};
	}

	public String getIdentifier(){ return identifier; }
	public String getName(){ return name; }
	public String getDescription(){ return description; }
	public Version getVersion(){ return version; }
	public String getLicense(){ return license; }
	public String getAuthor(){ return author; }
}
