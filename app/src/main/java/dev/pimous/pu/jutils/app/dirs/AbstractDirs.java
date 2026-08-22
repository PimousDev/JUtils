package dev.pimous.pu.jutils.app.dirs;

import dev.pimous.pu.jutils.app.Directories;
import dev.pimous.pu.jutils.config.Configuration;
import dev.pimous.pu.jutils.logger.JULAdapter;
import dev.pimous.pu.jutils.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public abstract class AbstractDirs implements Directories{

	private static final Logger LOGGER = new JULAdapter(
		AbstractDirs.class.getName()
	);

	protected static final Path CONFIG_DIR = Path.of("config");
	protected static final Path DATA_DIR = Path.of("data");
	protected static final Path CACHE_DIR = Path.of("cache");
	protected static final Path TEMP_DIR = Path.of("tmp");
	protected static final Path LOG_DIR = Path.of("log");

	private final Configuration config;
	private final boolean shouldDirsExist;

	protected AbstractDirs(final Configuration config,
		final boolean shouldDirsExist
	){
		this.config = config;
		this.shouldDirsExist = shouldDirsExist;
	}

	// GETTERS
	protected Configuration getConfig(){ return config; }
	private boolean shouldMakeDirs(){ return !shouldDirsExist; }
	protected Path getHomeDir(){
		return config.getSystem().getHome();
	}

	/** @since 1.1.0 */
	@Override
	public Path getConfigDir(final String identifier){
		return getConfigDir(identifier, shouldMakeDirs());
	}
	/** @since 1.1.0 */
	@Override
	public Path getDataDir(final String identifier){
		return getDataDir(identifier, shouldMakeDirs());
	}
	/** @since 1.1.0 */
	@Override
	public Path getCacheDir(final String identifier){
		return getCacheDir(identifier, shouldMakeDirs());
	}
	/** @since 1.1.0 */
	@Override
	public Path getTempDir(final String identifier){
		return getTempDir(identifier, shouldMakeDirs());
	}
	/** @since 1.1.0 */
	@Override
	public Path getLogDir(final String identifier){
		return getLogDir(identifier, shouldMakeDirs());
	}

	// SETTERS
	/** @since 1.1.0 */
	protected void makeDir(final Path parent, final Path full){
		try{
			if(!parent.equals(full) && !Files.isDirectory(parent))
				throw new IOException(
					"%s isn't a directory or doesn't exist;".formatted(parent)
				);

			Files.createDirectories(full);
		}catch(IOException e){
			LOGGER.warn("Unable to create %s directory;".formatted(full), e);
		}
	}
	@Deprecated
	protected void makeDir(final File parent, final File full){
		makeDir(parent.toPath(), full.toPath());
	}
}
