package dev.pimous.pu.jutils.config;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public abstract class ConfigSection{

	public ConfigSection(final Properties properties){
		load(properties);
	}

	// GETTERS
	private final Stream<Field> getFields(){
		return Arrays.stream(getClass().getDeclaredFields())
			.filter(f -> f.isAnnotationPresent(ConfigField.class));
	}

	public final Optional<Object> get(final String property){
		return getFields().filter(f -> f.getName().equals(property))
			.map(f -> {
				try{
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
		return get(property)
			.filter(String.class::isInstance)
			.map(String.class::cast);
	}
	
	protected Function<String, Object> getParser(final String property){
		return String::new;
	}

	// SETTERS
	public final void load(final Properties properties){
		getFields().forEach(f -> this.load(properties, f));
	}
	private final void load(final Properties properties, final Field f){
		final String p;

		ConfigField cf = f.getAnnotation(ConfigField.class);
		if(cf.property() != null && cf.property().length() != 0)
			p = cf.property();
		else
			p = f.getName();

		final Object o = getParser(p).apply(properties.getProperty(p));
		try{
			f.set(this, o);
		}catch(IllegalArgumentException e){
			throw new ConfigImplementationException(
				"Incompatible types between %s field and parser's return value;"
					.formatted(p),
				e
			);
		}catch(Exception e){
			throw new ConfigImplementationException(
				"Unable to set value of %s field;".formatted(p), e
			);
		}
	}

	// INNER CLASSES
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	@Documented
	public @interface ConfigField{

		public String property() default "";
		public boolean readonly() default true;
	}
}