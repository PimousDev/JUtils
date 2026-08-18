package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.app.dirs.LinuxDirs;
import dev.pimous.pu.jutils.app.dirs.LocalDirs;
import dev.pimous.pu.jutils.config.Configuration;

import java.io.File;
import java.nio.file.Path;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public interface Directories{

	String OS_LINUX_NAME = "Linux".toLowerCase();

	// GETTERS
	/** @since 1.1.0 */
	Path getGlobalConfigDir();
	/** @since 1.1.0 */
	Path getConfigDir(final String identifier);
	/** @since 1.1.0 */
	Path getGlobalDataDir();
	/** @since 1.1.0 */
	Path getDataDir(final String identifier);
	/** @since 1.1.0 */
	Path getGlobalCacheDir();
	/** @since 1.1.0 */
	Path getCacheDir(final String identifier);
	@Deprecated
	File getGlobalConfigDirFile();
	@Deprecated
	File getConfigDirFile(final String identifier);
	@Deprecated
	File getGlobalDataDirFile();
	@Deprecated
	File getDataDirFile(final String identifier);
	@Deprecated
	File getGlobalCacheDirFile();
	@Deprecated
	File getCacheDirFile(final String identifier);

	/** @since 1.1.0 */
	Path getGlobalTempDir();
	/** @since 1.1.0 */
	Path getTempDir(final String identifier);
	/** @since 1.1.0 */
	Path getGlobalLogDir();
	/** @since 1.1.0 */
	Path getLogDir(final String identifier);
	@Deprecated
	File getGlobalTempDirFile();
	@Deprecated
	File getTempDirFile(final String identifier);
	@Deprecated
	File getGlobalLogDirFile();
	@Deprecated
	File getLogDirFile(final String identifier);

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
