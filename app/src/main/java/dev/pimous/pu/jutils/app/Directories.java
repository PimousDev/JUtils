package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.app.dirs.LinuxDirs;
import dev.pimous.pu.jutils.app.dirs.LocalDirs;
import dev.pimous.pu.jutils.config.Configuration;

import java.io.File;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public interface Directories{

	String OS_LINUX_NAME = "Linux".toLowerCase();

	// GETTERS
	File getGlobalConfigDir();
	File getConfigDir(final String identifier);
	File getGlobalDataDir();
	File getDataDir(final String identifier);
	File getGlobalCacheDir();
	File getCacheDir(final String identifier);

	File getGlobalTempDir();
	File getTempDir(final String identifier);
	File getGlobalLogDir();
	File getLogDir(final String identifier);

	// FUNCTIONS
	static Directories create(final Configuration config){
		return create(config, false);
	}
	static Directories create(final Configuration config,
		final boolean shouldDirsExists
	){
		String os = config.getSystem().getOSName().toLowerCase();

		if(os.contains(OS_LINUX_NAME))
			return new LinuxDirs(config, shouldDirsExists);
		else
			return new LocalDirs(config, shouldDirsExists);
	}
}
