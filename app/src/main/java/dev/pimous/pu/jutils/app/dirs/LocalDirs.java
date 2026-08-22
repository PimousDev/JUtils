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
	public Path getGlobalDataDir(){
		return DATA_DIR;
	}
	/** @since 1.1.0 */
	@Override
	public Path getGlobalCacheDir(){
		return CACHE_DIR;
	}
	/** @since 1.1.0 */
	@Override
	public Path getGlobalTempDir(){
		return TEMP_DIR;
	}
	/** @since 1.1.0 */
	@Override
	public Path getGlobalLogDir(){
		return LOG_DIR;
	}

	/** @since 1.1.0 */
	@Override
	public Path getConfigDir(final String identifier,
		final boolean shouldMakeDir
	){
		final var dir = getGlobalConfigDir();
		if(shouldMakeDir) makeDir(dir, dir);
		return dir;
	}
	/** @since 1.1.0 */
	@Override
	public Path getDataDir(final String identifier,
		final boolean shouldMakeDir
	){
		final var dir = getGlobalDataDir();
		if(shouldMakeDir) makeDir(dir, dir);
		return dir;
	}
	/** @since 1.1.0 */
	@Override
	public Path getCacheDir(final String identifier,
		final boolean shouldMakeDir
	){
		final var dir = getGlobalCacheDir();
		if(shouldMakeDir) makeDir(dir, dir);
		return dir;
	}
	/** @since 1.1.0 */
	@Override
	public Path getTempDir(final String identifier,
		final boolean shouldMakeDir
	){
		final var dir = getGlobalTempDir();
		if(shouldMakeDir) makeDir(dir, dir);
		return dir;
	}
	/** @since 1.1.0 */
	@Override
	public Path getLogDir(final String identifier,
		final boolean shouldMakeDir
	){
		final var dir = getGlobalLogDir();
		if(shouldMakeDir) makeDir(dir, dir);
		return dir;
	}
}