package dev.pimous.pu.jutils.app.dirs;

import dev.pimous.pu.jutils.config.Configuration;

import java.nio.file.Path;

/**
 * @author Xibitol
 * @since 1.1.0
 */
public class WindowsDirs extends AbstractDirs{

	private static final Path USER_DIR = Path.of("AppData", "Roaming");

	public WindowsDirs(final Configuration config,
		final boolean shouldDirsExist
	){
		super(config, shouldDirsExist);
	}

	// GETTERS
	protected Path getGlobalUserDir(){
		return getHomeDir().resolve(USER_DIR);
	}
	/** @since 1.1.0 */
	@Override
	public Path getGlobalConfigDir(){ return getGlobalUserDir(); }
	/** @since 1.1.0 */
	@Override
	public Path getGlobalDataDir(){ return getGlobalUserDir(); }
	/** @since 1.1.0 */
	@Override
	public Path getGlobalCacheDir(){ return getGlobalUserDir(); }
	/** @since 1.1.0 */
	@Override
	public Path getGlobalTempDir(){
		return getConfig().getSystem().getTmpDir();
	}
	/** @since 1.1.0 */
	@Override
	public Path getGlobalLogDir(){ return getGlobalUserDir(); }

	/** @since 1.1.0 */
	@Override
	public Path getConfigDir(final String identifier,
		final boolean shouldMakeDir
	){
		final var parent = getGlobalConfigDir();
		final var full = parent.resolve(identifier).resolve(CONFIG_DIR);
		if(shouldMakeDir) makeDir(parent, full);
		return full;
	}
	/** @since 1.1.0 */
	@Override
	public Path getDataDir(final String identifier,
		final boolean shouldMakeDir
	){
		final var parent = getGlobalDataDir();
		final var full = parent.resolve(identifier).resolve(DATA_DIR);
		if(shouldMakeDir) makeDir(parent, full);
		return full;
	}
	/** @since 1.1.0 */
	@Override
	public Path getCacheDir(final String identifier,
		final boolean shouldMakeDir
	){
		final var parent = getGlobalCacheDir();
		final var full = parent.resolve(identifier).resolve(CACHE_DIR);
		if(shouldMakeDir) makeDir(parent, full);
		return full;
	}
	/** @since 1.1.0 */
	@Override
	public Path getTempDir(final String identifier,
		final boolean shouldMakeDir
	){
		final var parent = getGlobalTempDir();
		final var full = parent.resolve(identifier);
		if(shouldMakeDir) makeDir(parent, full);
		return full;
	}
	/** @since 1.1.0 */
	@Override
	public Path getLogDir(final String identifier,
		final boolean shouldMakeDir
	){
		final var parent = getGlobalLogDir();
		final var full = parent.resolve(identifier).resolve(LOG_DIR);
		if(shouldMakeDir) makeDir(parent, full);
		return full;
	}
}
