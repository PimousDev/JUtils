package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.app.dirs.LinuxDirs;
import dev.pimous.pu.jutils.app.dirs.LocalDirs;
import dev.pimous.pu.jutils.app.dirs.WindowsDirs;
import dev.pimous.pu.jutils.config.Configuration;

import java.io.File;
import java.nio.file.Path;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public interface Directories{

	String OS_LINUX_NAME = "Linux".toLowerCase();
	String OS_WINDOWS_NAME = "Windows".toLowerCase();

	// GETTERS
	/** @since 1.1.0 */
	Path getGlobalConfigDir();
	/** @since 1.1.0 */
	Path getGlobalDataDir();
	/** @since 1.1.0 */
	Path getGlobalCacheDir();
	/** @since 1.1.0 */
	Path getGlobalTempDir();
	/** @since 1.1.0 */
	Path getGlobalLogDir();

	@Deprecated
	default File getGlobalConfigDirFile(){
		return getGlobalConfigDir().toFile();
	}
	@Deprecated
	default File getGlobalDataDirFile(){
		return getGlobalDataDir().toFile();
	}
	@Deprecated
	default File getGlobalCacheDirFile(){
		return getGlobalCacheDir().toFile();
	}
	@Deprecated
	default File getGlobalTempDirFile(){
		return getGlobalTempDir().toFile();
	}
	@Deprecated
	default File getGlobalLogDirFile(){
		return getGlobalLogDir().toFile();
	}

	/** @since 1.1.0 */
	Path getConfigDir(final String identifier);
	/** @since 1.1.0 */
	Path getDataDir(final String identifier);
	/** @since 1.1.0 */
	Path getCacheDir(final String identifier);
	/** @since 1.1.0 */
	Path getTempDir(final String identifier);
	/** @since 1.1.0 */
	Path getLogDir(final String identifier);
	/** @since 1.1.0 */
	Path getConfigDir(final String identifier, final boolean shouldMakeDir);
	/** @since 1.1.0 */
	Path getDataDir(final String identifier, final boolean shouldMakeDir);
	/** @since 1.1.0 */
	Path getCacheDir(final String identifier, final boolean shouldMakeDir);
	/** @since 1.1.0 */
	Path getTempDir(final String identifier, final boolean shouldMakeDir);
	/** @since 1.1.0 */
	Path getLogDir(final String identifier, final boolean shouldMakeDir);

	@Deprecated
	default File getConfigDirFile(final String identifier){
		return getConfigDir(identifier).toFile();
	}
	@Deprecated
	default File getDataDirFile(final String identifier){
		return getDataDir(identifier).toFile();
	}
	@Deprecated
	default File getCacheDirFile(final String identifier){
		return getCacheDir(identifier).toFile();
	}
	@Deprecated
	default File getTempDirFile(final String identifier){
		return getTempDir(identifier).toFile();
	}
	@Deprecated
	default File getLogDirFile(final String identifier){
		return getLogDir(identifier).toFile();
	}

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
		else if(os.contains(OS_WINDOWS_NAME))
			return new WindowsDirs(config, shouldDirsExists);
		else
			return new LocalDirs(config, shouldDirsExists);
	}
}
