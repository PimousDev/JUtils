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
	private final boolean shouldDirsExist;

	protected AbstractDirs(final Configuration config,
		final boolean shouldDirsExist
	){
		this.config = config;
		this.shouldDirsExist = shouldDirsExist;
	}

	// GETTERS
	protected Configuration getConfig(){ return config; }
	protected boolean shouldDirsBeMade(){ return !shouldDirsExist; }
	protected Path getHomeDir(){
		return config.getSystem().getHome().toPath();
	}

	// SETTERS
	protected void makeDir(final File parent, final File full){
		if(parent.exists() && !full.exists() && !full.mkdirs())
			throw new RuntimeException(
				"Unable to create %s directory;".formatted(full)
			);
	}
}
