package dev.pimous.pu.jutils.config;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public abstract class ConfigSection{

	public ConfigSection(){}

	// GETTERS
	private Stream<Field> getFields(){
		return Arrays.stream(getClass().getDeclaredFields())
			.filter(f -> f.isAnnotationPresent(ConfigField.class));
	}
	private static String getPropertyName(final Field field){
		final ConfigField cf = field.getAnnotation(ConfigField.class);
		return cf.property() != null && !cf.property().isEmpty() ?
			cf.property() : field.getName();
	}

	public final Optional<Object> get(final String property){
		return getFields()
			.filter(f -> getPropertyName(f).equals(property))
			.map(f -> {
				try{
					f.setAccessible(true); // BUG: Is this can fail?
					return f.get(this);
				}catch(Exception e){
					throw new ConfigImplementationException(
						"Unable to set value of %s field;"
							.formatted(f.getName()),
						e
					);
				}
			})
			.findFirst();
	}
	public final Optional<String> getString(final String property){
		return get(property).map(String.class::cast);
	}
	
	protected Function<String, ?> getParser(final String property){
		return Function.identity();
	}
	protected Function<Object, CharSequence> getFormatter(
		final String property
	){
		return String::valueOf;
	}

	// SETTERS
	public final void load(final Properties properties)
		throws ConfigPropertyException, IllegalArgumentException
	{
		for(final Field f : getFields().toList())
			this.load(properties, f);
	}
	private void load(final Properties properties, final Field field)
		throws ConfigPropertyException, IllegalArgumentException
	{
		final String p = getPropertyName(field);

		// Presence
		if(properties.getProperty(p) == null){
			if(field.getAnnotation(ConfigField.class).mandatory())
				throw new ConfigPropertyException(
					"Property %s is mandatory but not found;".formatted(p)
				);

			return;
		}

		// Value
		final Object o = getParser(p).apply(properties.getProperty(p));
		try{
			field.setAccessible(true); // BUG: Is this can fail?
			field.set(this, o);
		}catch(IllegalArgumentException e){
			throw new ConfigImplementationException(
				"Incompatible types between %s field and parser's return value;"
					.formatted(field.getName()),
				e
			);
		}catch(Exception e){
			throw new ConfigImplementationException(
				"Unable to set value of %s field;".formatted(field.getName()), e
			);
		}
	}

	// FUNCTIONS
	private Properties toProperties(Predicate<Field> filter){
		final Properties props = new Properties();

		getFields().filter(filter).forEach(f -> {
			final String p = getPropertyName(f);
			try{
				f.setAccessible(true); // BUG: Is this can fail?
				props.setProperty(getPropertyName(f),
					getFormatter(p).apply(f.get(this)).toString()
				);
			}catch(Exception e){
				throw new ConfigImplementationException(
					"Unable to get value of %s field;".formatted(f.getName()), e
				);
			}
		});

		return props;
	}
	public final Properties toProperties(){
		return toProperties(_ -> true);
	}
	public final Properties toSavedProperties(){
		return toProperties(
			f -> f.getAnnotation(ConfigField.class).modifiable()
		);
	}

	// INNER CLASSES
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface ConfigField{

		String property() default "";
		boolean mandatory() default false;
		boolean modifiable() default false;
	}
}