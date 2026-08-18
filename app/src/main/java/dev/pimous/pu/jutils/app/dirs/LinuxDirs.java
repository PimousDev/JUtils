package dev.pimous.pu.jutils.app.dirs;

import dev.pimous.pu.jutils.config.Configuration;

import java.io.File;
import java.nio.file.Path;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class LinuxDirs extends AbstractDirs{

	private static final Path GLOBAL_CONFIG_DIR = Path.of(".config");
	private static final Path GLOBAL_SHARE_DIR = Path.of(".local/share");
	private static final Path GLOBAL_CACHE_DIR = Path.of(".cache");

	public LinuxDirs(final Configuration config, final boolean shouldDirsExist){
		super(config, shouldDirsExist);
	}

	// GETTERS
	/** @since 1.1.0 */
	@Override
	public Path getGlobalConfigDir(){
		return getHomeDir().resolve(GLOBAL_CONFIG_DIR);
	}
	/** @since 1.1.0 */
	@Override
	public Path getConfigDir(final String identifier){
		final var parent = getGlobalConfigDir();
		final var full = parent.resolve(identifier);

		if(shouldDirsBeMade())
			makeDir(parent, full);

		return full;
	}
	/** @since 1.1.0 */
	@Override
	public Path getGlobalDataDir(){
		return getHomeDir().resolve(GLOBAL_SHARE_DIR);
	}
	/** @since 1.1.0 */
	@Override
	public Path getDataDir(final String identifier){
		final var parent = getGlobalDataDir();
		final var full = parent.resolve(identifier).resolve(DATA_DIR);

		if(shouldDirsBeMade())
			makeDir(parent, full);

		return full;
	}
	/** @since 1.1.0 */
	@Override
	public Path getGlobalCacheDir(){
		return getHomeDir().resolve(GLOBAL_CACHE_DIR);
	}
	/** @since 1.1.0 */
	@Override
	public Path getCacheDir(final String identifier){
		final var parent = getGlobalCacheDir();
		final var full = parent.resolve(identifier);

		if(shouldDirsBeMade())
			makeDir(parent, full);

		return full;
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
		return getConfig().getSystem().getTmpDir();
	}
	/** @since 1.1.0 */
	@Override
	public Path getTempDir(final String identifier){
		final var parent = getGlobalTempDir();
		final var full = parent.resolve(identifier);

		if(shouldDirsBeMade())
			makeDir(parent, full);

		return full;
	}
	/** @since 1.1.0 */
	@Override
	public Path getGlobalLogDir(){
		return getHomeDir().resolve(GLOBAL_SHARE_DIR);
	}
	/** @since 1.1.0 */
	@Override
	public Path getLogDir(final String identifier){
		final var parent = getGlobalLogDir();
		final var full = parent.resolve(identifier).resolve(LOG_DIR);

		if(shouldDirsBeMade())
			makeDir(parent, full);

		return full;
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