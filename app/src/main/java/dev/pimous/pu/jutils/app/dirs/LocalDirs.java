package dev.pimous.pu.jutils.app.dirs;

import dev.pimous.pu.jutils.config.Configuration;

import java.io.File;
import java.nio.file.Path;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class LocalDirs extends AbstractDirs{

	public LocalDirs(final Configuration config, final boolean shouldDirsExist){
		super(config, shouldDirsExist);
	}

	// GETTERS
	/** @since 1.1.0 */
	@Override
	public Path getGlobalConfigDir(){
		return CONFIG_DIR;
	}
	/** @since 1.1.0 */
	@Override
	public Path getConfigDir(final String identifier){
		final var dir = getGlobalConfigDir();
		if(shouldDirsBeMade()) makeDir(dir, dir);
		return dir;
	}
	/** @since 1.1.0 */
	@Override
	public Path getGlobalDataDir(){
		return DATA_DIR;
	}
	/** @since 1.1.0 */
	@Override
	public Path getDataDir(final String identifier){
		final var dir = getGlobalDataDir();
		if(shouldDirsBeMade()) makeDir(dir, dir);
		return dir;
	}
	/** @since 1.1.0 */
	@Override
	public Path getGlobalCacheDir(){
		return CACHE_DIR;
	}
	/** @since 1.1.0 */
	@Override
	public Path getCacheDir(final String identifier){
		final var dir = getGlobalCacheDir();
		if(shouldDirsBeMade()) makeDir(dir, dir);
		return dir;
	}
	@Deprecated
	@Override
	public File getGlobalConfigDirFile(){
		return getGlobalConfigDir().toFile();
	}
	@Deprecated
	@Override
	public File getConfigDirFile(final String identifier){
		return getConfigDir(identifier).toFile();
	}
	@Deprecated
	@Override
	public File getGlobalDataDirFile(){
		return getGlobalDataDir().toFile();
	}
	@Deprecated
	@Override
	public File getDataDirFile(final String identifier){
		return getDataDir(identifier).toFile();
	}
	@Deprecated
	@Override
	public File getGlobalCacheDirFile(){
		return getGlobalCacheDir().toFile();
	}
	@Deprecated
	@Override
	public File getCacheDirFile(final String identifier){
		return getCacheDir(identifier).toFile();
	}

	/** @since 1.1.0 */
	@Override
	public Path getGlobalTempDir(){
		return TEMP_DIR;
	}
	/** @since 1.1.0 */
	@Override
	public Path getTempDir(final String identifier){
		final var dir = getGlobalTempDir();
		if(shouldDirsBeMade()) makeDir(dir, dir);
		return dir;
	}
	/** @since 1.1.0 */
	@Override
	public Path getGlobalLogDir(){
		return LOG_DIR;
	}
	/** @since 1.1.0 */
	@Override
	public Path getLogDir(final String identifier){
		final var dir = getGlobalLogDir();
		if(shouldDirsBeMade()) makeDir(dir, dir);
		return dir;
	}
	@Deprecated
	@Override
	public File getGlobalTempDirFile(){
		return getGlobalTempDir().toFile();
	}
	@Deprecated
	@Override
	public File getTempDirFile(final String identifier){
		return getTempDir(identifier).toFile();
	}
	@Deprecated
	@Override
	public File getGlobalLogDirFile(){
		return getGlobalLogDir().toFile();
	}
	@Deprecated
	@Override
	public File getLogDirFile(final String identifier){
		return getLogDir(identifier).toFile();
	}
}