package dev.pimous.pu.jutils.config;

import dev.pimous.pu.jutils.base.BadResourceException;
import dev.pimous.pu.jutils.base.ResourcePaths;

import java.io.*;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public abstract class Configuration{

	public static final String COMMENT_FORMAT = "%s - Saved at %s";
	public static final char SECTION_DELIMITER = '.';

	private final File file;
	private final Map<String, ConfigSection> sections;
	private final Directories dirs;
	private final SystemConfig system;
	private final Properties env = new Properties();

	public Configuration(final File file, final Properties system){
		this.file = file;
		this.sections = new HashMap<>();
		this.system = new SystemConfig(system);
		this.dirs = Directories.create(this);
	}
	public Configuration(final File file,
		final Properties system, final Map<String, String> env
	){
		this(file, system);

		this.env.putAll(env);
	}

	// GETTERS
	public <S extends ConfigSection> S getSection(final Class<S> sectionClass){
		return sections.values().stream()
			.filter(sectionClass::isInstance)
			.map(sectionClass::cast)
			.findFirst().get();
	}
	public SystemConfig getSystem(){ return system; }
	public String getEnv(final String property, final String defaultValue){
		return env.getProperty(property, defaultValue);
	}

	public File getConfigDir(final String identifier){
		return dirs.getConfigDir(identifier);
	}
	public File getDataDir(final String identifier){
		return dirs.getDataDir(identifier);
	}
	public File getCacheDir(final String identifier){
		return dirs.getCacheDir(identifier);
	}
	public File getTempDir(final String identifier){
		return dirs.getTempDir(identifier);
	}
	public File getLogDir(final String identifier){
		return dirs.getLogDir(identifier);
	}

	// SETTERS
	protected void addSection(final String name, final ConfigSection section){
		if(name.contains(String.valueOf(SECTION_DELIMITER)))
			throw new IllegalArgumentException(
				"Section name cannot contains '%c' (Got \"%s\");".formatted(
					SECTION_DELIMITER, name
				)
			);
		else if(sections.containsKey(name))
			throw new IllegalArgumentException(
				"Section named %s already exists;".formatted(name)
			);

		try{
			getSection(section.getClass());

			throw new IllegalArgumentException(
				"Section of type %s already exists;".formatted(
					section.getClass().getSimpleName()
				)
			);
		}catch(NoSuchElementException e){
			sections.put(name, section);
		}
	}

	// Loading
	public void load() throws FileNotFoundException, IOException{
		load(file);
	}

	private void load(final Properties properties){
		// TODO: Implement.
	}
	public void load(final InputStream stream) throws IOException{
		Properties props = new Properties();

		try{
			props.load(new InputStreamReader(stream, Charset.defaultCharset()));
		}catch(final IOException e){
			throw new IOException(
				"Cannot read properties (%s);".formatted(e.getMessage()), e
			);
		}

		load(props);
	}
	private void load(final File file) throws FileNotFoundException, IOException{
		try(FileInputStream fis = new FileInputStream(file)){
			load(fis);
		}catch(final FileNotFoundException e){
			final FileNotFoundException except = new FileNotFoundException(
				"No such configuration file at %s;".formatted(
					file.getAbsolutePath()
				)
			);
			except.addSuppressed(e);

			throw except;
		}catch(final IOException e){
			throw new IOException(
				"Cannot open configuration file at %s (%s);".formatted(
					file.getAbsolutePath(), e.getMessage()
				), e
			);
		}
	}

	// FUNCTIONS
	public Properties toProperties(){
		Properties props = new Properties();
		return props;
	}

	// Saving
	public void save(final String comments) throws IOException{
		try{
			file.createNewFile();
		}catch(final IOException e){
			throw new IOException(
				"Cannot create configuration file at %s.".formatted(file), e
			);
		}

		// FIXME: Handle user timezone.
		try(FileWriter fos = new FileWriter(file, Charset.defaultCharset())){
			toProperties().store(fos, COMMENT_FORMAT.formatted(comments,
				OffsetDateTime.now(ZoneOffset.UTC)
			));
		}catch(final IOException e){
			throw new IOException(
				"Cannot write configuration file at %s.".formatted(file), e
			);
		}
	}
}