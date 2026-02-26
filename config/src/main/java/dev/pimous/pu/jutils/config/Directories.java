package dev.pimous.pu.jutils.config;

import dev.pimous.pu.jutils.config.dirs.LinuxDirs;
import dev.pimous.pu.jutils.config.dirs.LocalDirs;

import java.io.File;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public interface Directories{

	public static final String OS_WIN_NAME = "win";
	public static final String OS_LINUX_NAME = "linux";

	// GETTERS
	public abstract File getGlobalConfigDir();
	public abstract File getConfigDir(final String identifier);
	public abstract File getGlobalDataDir();
	public abstract File getDataDir(final String identifier);
	public abstract File getGlobalCacheDir();
	public abstract File getCacheDir(final String identifier);

	public abstract File getGlobalTempDir();
	public abstract File getTempDir(final String identifier);
	public abstract File getGlobalLogDir();
	public abstract File getLogDir(final String identifier);

	// FUNCTIONS
	public static Directories create(final Configuration config){
		String os = config.getSystem().getOSName().toLowerCase();

		if(os.contains(OS_LINUX_NAME))
			return new LinuxDirs(config);
		else
			return new LocalDirs(config);
	}
}
