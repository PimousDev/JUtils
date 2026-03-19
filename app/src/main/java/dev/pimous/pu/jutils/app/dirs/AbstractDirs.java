package dev.pimous.pu.jutils.app.dirs;

import dev.pimous.pu.jutils.app.Directories;
import dev.pimous.pu.jutils.config.Configuration;

import java.io.File;
import java.nio.file.Path;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public abstract class AbstractDirs implements Directories{

	protected static final String CONFIG_DIR = "config";
	protected static final String DATA_DIR = "data";
	protected static final String CACHE_DIR = "cache";
	protected static final String TEMP_DIR = "tmp";
	protected static final String LOG_DIR = "log";

	private final Configuration config;

	protected AbstractDirs(final Configuration config){
		this.config = config;
	}

	// GETTERS
	protected Configuration getConfig(){ return config; }
	protected Path getHomeDir(){
		return Path.of(config.getSystem().getHome());
	}

	// SETTERS
	protected File makeDir(final File parent, final File full){
		if(parent.exists())
			full.mkdirs();

		return full;
	}
}
