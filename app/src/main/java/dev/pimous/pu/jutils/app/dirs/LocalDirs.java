package dev.pimous.pu.jutils.app.dirs;

import dev.pimous.pu.jutils.config.Configuration;

import java.io.File;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class LocalDirs extends AbstractDirs{

	public LocalDirs(final Configuration config, final boolean shouldDirsExist){
		super(config, shouldDirsExist);
	}

	// GETTERS
	@Override
	public File getGlobalConfigDir(){
		return new File(CONFIG_DIR);
	}
	@Override
	public File getConfigDir(final String identifier){
		File dir = getGlobalConfigDir();
		if(shouldDirsBeMade()) makeDir(dir, dir);
		return dir;
	}
	@Override
	public File getGlobalDataDir(){
		return new File(DATA_DIR);
	}
	@Override
	public File getDataDir(final String identifier){
		File dir = getGlobalDataDir();
		if(shouldDirsBeMade()) makeDir(dir, dir);
		return dir;
	}
	@Override
	public File getGlobalCacheDir(){
		return new File(CACHE_DIR);
	}
	@Override
	public File getCacheDir(final String identifier){
		File dir = getGlobalCacheDir();
		if(shouldDirsBeMade()) makeDir(dir, dir);
		return dir;
	}

	@Override
	public File getGlobalTempDir(){
		return new File(TEMP_DIR);
	}
	@Override
	public File getTempDir(final String identifier){
		File dir = getGlobalTempDir();
		if(shouldDirsBeMade()) makeDir(dir, dir);
		return dir;
	}
	@Override
	public File getGlobalLogDir(){
		return new File(LOG_DIR);
	}
	@Override
	public File getLogDir(final String identifier){
		File dir = getGlobalLogDir();
		if(shouldDirsBeMade()) makeDir(dir, dir);
		return dir;
	}
}