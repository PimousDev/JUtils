package dev.pimous.pu.jutils.config.dirs;

import dev.pimous.pu.jutils.config.Configuration;

import java.io.File;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class LocalDirs extends AbstractDirs{

	public LocalDirs(Configuration config){
		super(config);
	}

	// GETTERS
	@Override
	public File getGlobalConfigDir(){
		return new File(CONFIG_DIR);
	}
	@Override
	public File getConfigDir(final String identifier){
		File dir = getGlobalConfigDir();
		return makeDir(dir, dir);
	}
	@Override
	public File getGlobalDataDir(){
		return new File(DATA_DIR);
	}
	@Override
	public File getDataDir(final String identifier){
		File dir = getGlobalDataDir();
		return makeDir(dir, dir);
	}
	@Override
	public File getGlobalCacheDir(){
		return new File(CACHE_DIR);
	}
	@Override
	public File getCacheDir(final String identifier){
		File dir = getGlobalCacheDir();
		return makeDir(dir, dir);
	}

	@Override
	public File getGlobalTempDir(){
		return new File(TEMP_DIR);
	}
	@Override
	public File getTempDir(final String identifier){
		File dir = getGlobalTempDir();
		return makeDir(dir, dir);
	}
	@Override
	public File getGlobalLogDir(){
		return new File(LOG_DIR);
	}
	@Override
	public File getLogDir(final String identifier){
		File dir = getGlobalLogDir();
		return makeDir(dir, dir);
	}
}