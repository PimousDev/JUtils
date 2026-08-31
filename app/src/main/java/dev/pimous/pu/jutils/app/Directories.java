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

	final String OS_LINUX_NAME = "Linux".toLowerCase();
	final String OS_WINDOWS_NAME = "Windows".toLowerCase();

	// GETTERS
	/** @since 1.1.0 */
	Path getBinaryDir();
	/** @since 1.1.0 */
	Path getLibraryDir();
	/** @since 1.1.0 */
	Path getConfigDir();
	/** @since 1.1.0 */
	Path getDataDir();
	/** @since 1.1.0 */
	Path getStateDir();
	/** @since 1.1.0 */
	Path getLogDir();
	/** @since 1.1.0 */
	Path getPersistentTemporaryDir();
	/** @since 1.1.0 */
	Path getCacheDir();
	/** @since 1.1.0 */
	Path getTemporaryDir();

	// Aliases
	/** @since 1.1.0 */
	default Path getBinDir(){ return getBinaryDir(); }
	/** @since 1.1.0 */
	default Path getLibDir(){ return getLibraryDir(); }
	/** @since 1.1.0 */
	default Path getPTempDir(){ return getPersistentTemporaryDir(); }
	/** @since 1.1.0 */
	default Path getPersistentTempDir(){ return getPersistentTemporaryDir(); }
	/** @since 1.1.0 */
	default Path getTempDir(){ return getTemporaryDir(); }

	// Deprecated File based methods.
	@Deprecated
	default File getGlobalConfigDirFile(){ return getConfigDir().toFile(); }
	@Deprecated
	default File getGlobalDataDirFile(){ return getDataDir().toFile(); }
	@Deprecated
	default File getGlobalCacheDirFile(){ return getCacheDir().toFile(); }
	@Deprecated
	default File getGlobalTempDirFile(){ return getTempDir().toFile(); }
	@Deprecated
	default File getGlobalLogDirFile(){ return getLogDir().toFile(); }
	@Deprecated
	default File getConfigDirFile(final String identifier){
		return getConfigDir().toFile();
	}
	@Deprecated
	default File getDataDirFile(final String identifier){
		return getDataDir().toFile();
	}
	@Deprecated
	default File getCacheDirFile(final String identifier){
		return getCacheDir().toFile();
	}
	@Deprecated
	default File getTempDirFile(final String identifier){
		return getTempDir().toFile();
	}
	@Deprecated
	default File getLogDirFile(final String identifier){
		return getLogDir().toFile();
	}

	// FUNCTIONS
	/** @since 1.1.0 */
	static Directories create(final App<?> context,
		final boolean isSystem
	){
		String os = context.getConfig().getSystem().getOSName().toLowerCase();

		if(os.contains(OS_LINUX_NAME))
			return new LinuxDirs(context, isSystem);
		else if(os.contains(OS_WINDOWS_NAME))
			return new WindowsDirs(context, isSystem);
		else
			return createLocal(context);
	}
	/** @since 1.1.0 */
	static Directories createLocal(final App<?> context){
		return new LocalDirs(context);
	}
}
