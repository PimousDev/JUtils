package dev.pimous.pu.jutils.config;

import java.util.Properties;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class SystemConfig extends ConfigSection{

	@ConfigField(property="os.name")
	private String osName;
	@ConfigField(property="user.home")
	private String home;

	public SystemConfig(final Properties properties){
		super(properties);
	}

	// GETTERS
	public String getOSName(){ return osName; }
	public String getHome(){ return home; }
}
