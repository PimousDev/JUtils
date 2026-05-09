package dev.pimous.pu.jutils.app.dirs;

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

	public LinuxDirs(final Configuration config, final boolean shouldDirsExist){
		super(config, shouldDirsExist);
	}

	// GETTERS
	@Override
	public File getGlobalConfigDir(){
		return new File(getHomeDir().toFile(), GLOBAL_CONFIG_DIR);
	}
	@Override
	public File getConfigDir(final String identifier){
		final File parent = getGlobalConfigDir();
		final File full = new File(parent, identifier);

		if(shouldDirsBeMade())
			makeDir(parent, full);

		return full;
	}
	@Override
	public File getGlobalDataDir(){
		return new File(getHomeDir().toFile(), GLOBAL_SHARE_DIR);
	}
	@Override
	public File getDataDir(final String identifier){
		final File parent = getGlobalDataDir();
		final File full = Path.of(
			parent.toString(), identifier, DATA_DIR
		).toFile();

		if(shouldDirsBeMade())
			makeDir(parent, full);

		return full;
	}
	@Override
	public File getGlobalCacheDir(){
		return new File(getHomeDir().toFile(), GLOBAL_CACHE_DIR);
	}
	@Override
	public File getCacheDir(final String identifier){
		final File parent = getGlobalCacheDir();
		final File full = new File(parent, identifier);

		if(shouldDirsBeMade())
			makeDir(parent, full);

		return full;
	}

	@Override
	public File getGlobalTempDir(){
		return getConfig().getSystem().getTmpDir();
	}
	@Override
	public File getTempDir(final String identifier){
		final File parent = getGlobalTempDir();
		final File full = new File(parent, identifier);

		if(shouldDirsBeMade())
			makeDir(parent, full);

		return full;
	}
	@Override
	public File getGlobalLogDir(){
		return new File(getHomeDir().toFile(), GLOBAL_SHARE_DIR);
	}
	@Override
	public File getLogDir(final String identifier){
		File parent = getGlobalLogDir();
		final File full = Path.of(
			parent.toString(), identifier, LOG_DIR
		).toFile();

		if(shouldDirsBeMade())
			makeDir(parent, full);

		return full;
	}
}