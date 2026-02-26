package dev.pimous.pu.jutils.config.dirs;

import dev.pimous.pu.jutils.config.Configuration;

import java.io.File;
import java.nio.file.Path;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class LinuxDirs extends AbstractDirs{

	private static final String GLOBAL_CONFIG_DIR = ".config";
	private static final String GLOBAL_SHARE_DIR = ".local/share";
	private static final String GLOBAL_CACHE_DIR = ".cache";

	private static final String TEMP_DIR_PROP_NAME = "java.io.tmpdir";

	public LinuxDirs(Configuration config){
		super(config);
	}

	// GETTERS
	@Override
	public File getGlobalConfigDir(){
		return new File(getHomeDir().toFile(), GLOBAL_CONFIG_DIR);
	}
	@Override
	public File getConfigDir(final String identifier){
		File parent = getGlobalConfigDir();
		return makeDir(parent, new File(parent, identifier));
	}
	@Override
	public File getGlobalDataDir(){
		return new File(getHomeDir().toFile(), GLOBAL_SHARE_DIR);
	}
	@Override
	public File getDataDir(final String identifier){
		File parent = getGlobalDataDir();
		return makeDir(parent,
			Path.of(parent.toString(), identifier, DATA_DIR).toFile()
		);
	}
	@Override
	public File getGlobalCacheDir(){
		return new File(getHomeDir().toFile(), GLOBAL_CACHE_DIR);
	}
	@Override
	public File getCacheDir(final String identifier){
		File parent = getGlobalCacheDir();
		return makeDir(parent, new File(parent, identifier));
	}

	@Override
	public File getGlobalTempDir(){
		return new File(getConfig().getEnv(TEMP_DIR_PROP_NAME));
	}
	@Override
	public File getTempDir(final String identifier){
		File parent = getGlobalTempDir();
		return makeDir(parent, new File(parent, identifier));
	}
	@Override
	public File getGlobalLogDir(){
		return new File(getHomeDir().toFile(), GLOBAL_SHARE_DIR);
	}
	@Override
	public File getLogDir(final String identifier){
		File parent = getGlobalLogDir();
		return makeDir(parent,
			Path.of(parent.toString(), identifier, LOG_DIR).toFile()
		);
	}
}