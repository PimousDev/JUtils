package dev.pimous.pu.jutils.app;

import dev.pimous.pu.jutils.app.util.AppLogger;
import dev.pimous.pu.jutils.base.BadResourceException;
import dev.pimous.pu.jutils.base.ProtectedScheduledExecutor;
import dev.pimous.pu.jutils.config.ConfigPropertyException;
import dev.pimous.pu.jutils.config.Configuration;
import dev.pimous.pu.jutils.config.LocalizationConfig;
import dev.pimous.pu.jutils.i18n.I18n;
import dev.pimous.pu.jutils.i18n.I18nBundle;
import dev.pimous.pu.jutils.logger.Level;
import dev.pimous.pu.jutils.logger.Logger;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public abstract class App<C extends Configuration>{

	private static final String APP_RESOURCE_FILENAME = "app.properties";
	public static final Path DEFAULT_CONFIG_FILENAME = Path.of(
		"config.properties"
	);

	private final AppConfig appConfig = new AppConfig();
	public final InputStream in;
	public final PrintStream out;
	public final PrintStream err;
	private final ScheduledExecutorService executor;
	private volatile boolean loaded = false;

	private C config;
	private Directories dirs;
	protected I18n language;
	private TimeZone timeZone = TimeZone.getTimeZone(ZoneOffset.UTC);
	private final AppLogger logger;

	public App(final int threads,
		final InputStream in,
		final PrintStream out,
		final PrintStream err
	){
		this.in = in;
		this.out = out;
		this.err = err;

		// App config section
		try(final InputStream is = ClassLoader.getSystemResourceAsStream(
			APP_RESOURCE_FILENAME
		)){
			if(is == null)
				throw new FileNotFoundException(APP_RESOURCE_FILENAME);

			final Properties props = new Properties();
			props.load(is);
			appConfig.load(props);
		}catch(final IOException|ConfigPropertyException e){
			throw new BadResourceException(APP_RESOURCE_FILENAME, e);
		}

		// Logger
		logger = new AppLogger(appConfig.getIdentifier());
		logger.setLevel(Level.NOTICE);

		// Threads pool
		executor = Executors.newScheduledThreadPool(threads);
	}
	public App(final int threads){
		this(threads, System.in, System.out, System.err);
	}

	// GETTERS
	public String getIdentifier(){ return getProperties().getIdentifier(); }
	public AppConfig getProperties(){ return appConfig; }
	public boolean isLoaded(){ return loaded; }

	public Directories getDirs(){ return dirs; }
	public C getConfig(){ return config; }
	public I18nBundle getI18n(){ return language.getBundle(); }
	public TimeZone getTimeZone(){ return timeZone; }
	public Logger getLogger(){ return logger; }
	public ScheduledExecutorService getExecutor(){
		return new ProtectedScheduledExecutor(executor);
	}
	protected final void shutdownExecutor(){
		executor.shutdown();
	}
	protected final List<Runnable> shutdownExecutorNow(){
		return executor.shutdownNow();
	}

	@Deprecated
	public File getConfigDirFile(){ return dirs.getConfigDir().toFile(); }
	@Deprecated
	public File getDataDirFile(){ return dirs.getDataDir().toFile(); }
	@Deprecated
	public File getCacheDirFile(){ return dirs.getCacheDir().toFile(); }
	@Deprecated
	public File getTempDirFile(){ return dirs.getTempDir().toFile(); }
	@Deprecated
	public File getLogDirFile(){ return dirs.getLogDir().toFile(); }

	// SETTERS
	public void setLoggingLevel(final Level level){
		logger.setLevel(level);
	}

	// FUNCTIONS
	/** @apiNote If you specify your own config path, the app default
	 * configuration directory might not be created automatically.
	 */
	public void load(
		final C config,
		final I18n language,
		final boolean hasGUI
	){
		if(isLoaded())
			throw new RuntimeException(
				"%s app cannot be loaded multiple times;".formatted(
					getIdentifier()
				)
			);

		// Configuration without errors
		this.config = config;
		// TODO: Work on systems app or local dirs only.
		dirs = Directories.create(this, false);
		Throwable configException = null;

		if(this.config.getSectionCount() > 0){
			if(config.getPath() == null)
				config.setPath(
					dirs.getConfigDir().resolve(DEFAULT_CONFIG_FILENAME)
				);

			try{
				config.load();
			}catch(Exception e){
				configException = e;
			}
		}

		// I18n from defaults and system
		// I18n is loaded before config reload because defaults have already
		// been defined either in I18n or in LocalizationConfig; Reloading
		// implies no new values, as defaults are written out to a new file and
		// comes from system variables or otherwise, from the constructor.
		this.language = language;
		Locale locale = language.defaultLocale;
		timeZone = TimeZone.getTimeZone(ZoneOffset.UTC);
		boolean languageSupported = true;

		if(config.hasSection(LocalizationConfig.class)){
			final LocalizationConfig lc = config.getSection(
				LocalizationConfig.class
			);
			locale = lc.getLocale();
			timeZone = lc.getTimeZone();
		}

		if(!language.load(locale, Charset.defaultCharset()).equals(locale))
			languageSupported = false;

		// Configuration defaults without errors
		Throwable configDefaultsException = null;
		Throwable configReloadException = null;

		if(this.config.getSectionCount() > 0
			&& configException != null
			&& configException.getCause() instanceof NoSuchFileException
		){
			try{
				config.save(getI18n().get("config.comment.defaults"));
			}catch(Exception e){
				configDefaultsException = e;
			}

			try{
				config.load(config.toProperties());
			}catch(ConfigPropertyException|IllegalArgumentException e){
				configReloadException = e;
			}
		}

		// Logging without errors
		logger.loadConsoleHandlers(this, hasGUI);

		Exception fhException = null;
		try{
			logger.loadFileHandler(this);
		}catch(IOException e){
			fhException = e;
		}

		// Logging start
		getLogger().notice(getI18n().get("app.loading",
			getProperties().getName(),
			getProperties().getIdentifier(),
			getProperties().getVersion()
		));

		if(fhException != null)
			getLogger().error(fhException,
				getI18n().get("log.error.open", getDirs().getLogDir())
			);

		if(this.config.getSectionCount() == 0)
			getLogger().info(getI18n().get("config.empty"));
		else if(configException == null)
			getLogger().notice(getI18n().get("config.current",
				getConfig().getPath().toAbsolutePath()
			));
		else if(configException.getCause() instanceof NoSuchFileException){
			if(configDefaultsException != null)
				getLogger().error(getI18n().get(
					"config.error.default",
					getConfig().getPath().toAbsolutePath(),
					configDefaultsException.getMessage()
				));
			else
				getLogger().notice(getI18n().get(
					"config.generated",
					config.getPath().toAbsolutePath()
				));

			if(configReloadException != null){
				getLogger().fatal(getI18n().get(
					"config.error.reload",
					getConfig().getPath().toAbsolutePath(),
					configReloadException.getMessage()
				));
				throw new RuntimeException(configReloadException);
			}
		}else{
			getLogger().fatal(getI18n().get(
				"config.error.load",
				getConfig().getPath().toAbsolutePath(),
				configException.getMessage()
			));
			throw new RuntimeException(configException);
		}

		if(!languageSupported)
			getLogger().warn(getI18n().get("i18n.error.support",
				locale, getI18n().getLocale()
			));

		getLogger().notice(getI18n().get("i18n.current",
			getI18n().getLocale().getDisplayName(),
			timeZone.getID()
		));

		loaded = true;
	}
	public abstract void run(final String[] args);
	public abstract void run(final Console console, final String[] args);
}
