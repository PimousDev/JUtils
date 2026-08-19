package dev.pimous.pu.jutils.config;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public abstract class Configuration{

	public static final char SECTION_DELIMITER = '.';
	public static final Pattern PROPERTY_PATTERN = Pattern.compile(
		"^(?<section>[^.]+)\\.(?<property>.+)$"
	);

	private Path path = null;
	private final Map<String, ConfigSection> sections;
	private final SystemConfig system;
	private final Properties env = new Properties();

	public Configuration(final Properties system){
		this.sections = new HashMap<>();
		this.system = new SystemConfig(system);
	}
	public Configuration(
		final Properties system, final Map<String, String> env
	){
		this(system);

		this.env.putAll(env);
	}

	// GETTERS
	@Deprecated
	public File getFile(){ return path != null ? path.toFile() : null; }
	/** @since 1.1.0 */
	public Path getPath(){ return path; }

	public <S extends ConfigSection> boolean hasSection(
		final Class<S> sectionClass
	){
		return sections.values().stream()
			.anyMatch(sectionClass::isInstance);
	}
	public <S extends ConfigSection> S getSection(final Class<S> sectionClass){
		return sections.values().stream()
			.filter(sectionClass::isInstance)
			.map(sectionClass::cast)
			.findFirst().orElseThrow();
	}
	/** @since 1.1.0 */
	public int getSectionCount(){ return this.sections.size(); }

	public Optional<Object> get(final String property){
		final Matcher m = PROPERTY_PATTERN.matcher(property);
		if(!m.matches())
			throw new IllegalArgumentException(
				"Section name wasn't recognized (Got \"%s\");".formatted(
					property
				)
			);

		return Optional.ofNullable(sections.get(m.group("section")))
			.map(s -> s.get(m.group("property")).orElseThrow());
	}
	public Optional<String> getString(final String property){
		return get(property).map(String.class::cast);
	}
	public SystemConfig getSystem(){ return system; }
	public String getEnv(final String property, final String defaultValue){
		return env.getProperty(property, defaultValue);
	}

	// SETTERS
	@Deprecated
	public void setFile(final File file){
		this.path = file.toPath();
	}
	/** @since 1.1.0 */
	public void setPath(final Path path){
		this.path = path;
	}
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
	public void load() throws
		ConfigPropertyException, IllegalArgumentException, IOException
	{
		if(path == null)
			throw new RuntimeException("path isn't defined;");

		load(path);
	}

	public void load(final Properties properties)
		throws ConfigPropertyException, IllegalArgumentException
	{
		for(Map.Entry<String, ConfigSection> e : sections.entrySet()){
			final Properties sectionProps = new Properties();
			sectionProps.putAll(properties.stringPropertyNames().stream()
				.filter(p -> p.startsWith(e.getKey()))
				.collect(Collectors.toMap(
					Function.identity(),
					properties::getProperty
				))
			);

			e.getValue().load(sectionProps, e.getKey());
		}
	}
	public void load(final InputStream stream)
		throws ConfigPropertyException, IllegalArgumentException, IOException
	{
		Properties props = new Properties();

		try{
			props.load(new InputStreamReader(stream, Charset.defaultCharset()));
		}catch(final IOException|RuntimeException e){
			throw new IOException(
				"Cannot read properties (%s);".formatted(e.getMessage()), e
			);
		}

		load(props);
	}
	/** @since 1.1.0 */
	private void load(final Path path) throws
		ConfigPropertyException, IllegalArgumentException, IOException
	{
		try(var fis = Files.newInputStream(path)){
			load(fis);
		}catch(final IOException e){
			var msg = e.getMessage();

			if(e instanceof NoSuchFileException)
				msg = "No such file";
			if(e instanceof AccessDeniedException)
				msg = "File inaccessible or unreadable";

			throw new IOException(
				"Cannot open configuration file at %s (%s);".formatted(
					path.toAbsolutePath(), msg
				), e
			);
		}
	}
	@Deprecated
	private void load(final File file) throws
		ConfigPropertyException, IllegalArgumentException, IOException
	{
		load(file.toPath());
	}

	// FUNCTIONS
	private Properties toProperties(
		BiFunction<ConfigSection, String, Properties> getter
	){
		final Properties props = new Properties();
		sections.forEach((n, s) -> props.putAll(getter.apply(s, n)));
		return props;
	}
	public Properties toProperties(){
		return toProperties(ConfigSection::toProperties);
	}
	public Properties toSavedProperties(){
		return toProperties(ConfigSection::toSavedProperties);
	}

	// Saving
	public void save(final String comments) throws IOException{
		Properties props;

		// Creates file (and loads initial configuration if exists)
		try{
			if(!Files.exists(path)){
				Files.createFile(path);
				props = toProperties();
			}else{
				props = new Properties();

				try(var fis = Files.newBufferedReader(path,
					Charset.defaultCharset()
				)){
					props.load(fis);
				}catch(final IOException e){
					throw new IOException(
						"Cannot read configuration file at %s;".formatted(
							path.toAbsolutePath()
						), e
					);
				}

				props.putAll(toSavedProperties());
			}
		}catch(final IOException e){
			throw new IOException(
				"Cannot create configuration file at %s;".formatted(
					path.toAbsolutePath()
				), e
			);
		}

		// Saves configuration
		try(var fos = Files.newBufferedWriter(path, Charset.defaultCharset())){
			props.store(fos, comments);
		}catch(final IOException e){
			throw new IOException(
				"Cannot write configuration file at %s;".formatted(
					path.toAbsolutePath()
				), e
			);
		}
	}
}