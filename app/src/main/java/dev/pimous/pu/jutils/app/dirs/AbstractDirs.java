package dev.pimous.pu.jutils.app.dirs;

import dev.pimous.pu.jutils.app.App;
import dev.pimous.pu.jutils.app.AppConfig;
import dev.pimous.pu.jutils.app.Directories;
import dev.pimous.pu.jutils.base.NotImplementedException;
import dev.pimous.pu.jutils.config.Configuration;
import dev.pimous.pu.jutils.logger.JULAdapter;
import dev.pimous.pu.jutils.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public abstract class AbstractDirs implements Directories{

	private static final Logger LOGGER = new JULAdapter(
		AbstractDirs.class.getName()
	);

	protected static final Path BINARY_DIR = Path.of("bin");
	protected static final Path LIBRARY_DIR = Path.of("lib");
	protected static final Path CONFIG_DIR = Path.of("etc");
	protected static final Path DATA_DIR = Path.of("data");
	protected static final Path STATE_DIR = Path.of("state");
	protected static final Path LOG_DIR = Path.of("log");
	protected static final Path PERSISTENT_TEMPORARY_DIR = Path.of("ptmp");
	protected static final Path CACHE_DIR = Path.of("cache");
	protected static final Path TEMPORARY_DIR = Path.of("tmp");

	protected final Configuration config;
	protected final boolean isSystem;
	protected final Path subfolders;

	/** @since 1.1.0 */
	protected AbstractDirs(
		final AppConfig properties, final Configuration config,
		final boolean isSystem
	){
		this.config = config;
		this.isSystem = isSystem;
		this.subfolders = Path.of(
			Optional.ofNullable(properties.getGroup()).orElse(""),
			properties.getIdentifier()
		);
	}
	/** @since 1.1.0 */
	protected AbstractDirs(final App<?> context, final boolean isSystem){
		this(context.getProperties(), context.getConfig(), isSystem);
	}

	// GETTERS
	protected final Path getHomeDir(){
		return config.getSystem().getHome();
	}

	// SETTERS
	/** @since 1.1.0 */
	protected final Path composeDir(final Path parent, final Path child){
		var full = parent.resolve(child);

		try{
			if(!parent.equals(full) && !Files.isDirectory(parent))
				throw new IOException(
					"%s isn't a directory or doesn't exist;".formatted(parent)
				);

			Files.createDirectories(full);
		}catch(IOException e){
			LOGGER.warn("Unable to create %s directory;".formatted(full), e);
		}

		return full;
	}
	@Deprecated
	protected final void makeDir(final File parent, final File full){
		composeDir(parent.toPath(), full.toPath().relativize(parent.toPath()));
	}
}
