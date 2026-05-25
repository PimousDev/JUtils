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

	private static final String APP_RESOURCE_FILE = "app.properties";
	private static final String DEFAULT_CONFIG_FILENAME = "config.properties";

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
			APP_RESOURCE_FILE
		)){
			if(is == null)
				throw new FileNotFoundException(APP_RESOURCE_FILE);

			final Properties props = new Properties();
			props.load(is);
			appConfig.load(props);
		}catch(final IOException|ConfigPropertyException e){
			throw new BadResourceException(APP_RESOURCE_FILE, e);
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

	public File getConfigDir(){ return dirs.getConfigDir(getIdentifier()); }
	public File getDataDir(){ return dirs.getDataDir(getIdentifier()); }
	public File getCacheDir(){ return dirs.getCacheDir(getIdentifier()); }
	public File getTempDir(){ return dirs.getTempDir(getIdentifier()); }
	public File getLogDir(){ return dirs.getLogDir(getIdentifier()); }

	// SETTERS
	public void setLoggingLevel(final Level level){
		logger.setLevel(level);
	}

	// FUNCTIONS
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
		dirs = Directories.create(config);
		Exception configException = null;

		if(config.getFile() == null)
			config.setFile(new File(getConfigDir(), DEFAULT_CONFIG_FILENAME));

		try{
			config.load();
		}catch(Exception e){
			configException = e;
		}

		// I18n from defaults and system
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
		Exception configDefaultsException = null;
		Exception configReloadException = null;

		if(configException instanceof FileNotFoundException){
			try{
				config.save(getI18n().get("config.comment.defaults"));
			}catch(Exception e){
				configDefaultsException = e;
			}

			try{
				config.load();
			}catch(ConfigPropertyException | IllegalArgumentException e){
				configReloadException = e;
			}catch(IOException ignored){}
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
				getI18n().get("log.error.open", getLogDir())
			);

		if(configException == null){
			getLogger().notice(getI18n().get("config.current",
				getConfig().getFile().getAbsolutePath()
			));
		}else if(configException instanceof FileNotFoundException){
			if(configDefaultsException instanceof IOException)
				getLogger().error(configDefaultsException);
			else
				getLogger().notice(getI18n().get(
					"config.generated",
					config.getFile().getAbsolutePath()
				));

			if(configReloadException != null){
				getLogger().fatal(getI18n().get(
					"config.error.reload",
					getConfig().getFile().getAbsolutePath(),
					configReloadException.getMessage()
				));
				throw new RuntimeException(configReloadException);
			}
		}else{
			getLogger().fatal(getI18n().get(
				"config.error.load",
				getConfig().getFile().getAbsolutePath(),
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
