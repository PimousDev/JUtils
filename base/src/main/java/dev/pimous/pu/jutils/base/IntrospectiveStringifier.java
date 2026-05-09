package dev.pimous.pu.jutils.base;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public final class IntrospectiveStringifier{

	private IntrospectiveStringifier(){}

	// FUNCTIONS
	public static String fromPublics(final Class<?> clazz){
		return fromPublics(clazz, null,
			f -> Modifier.isStatic(f.getModifiers())
		);
	}
	public static String fromPublics(final Object object){
		return fromPublics(object.getClass(), object,
			f -> !Modifier.isStatic(f.getModifiers())
		);
	}
	private static String fromPublics(final Class<?> clazz, final Object object,
		final Predicate<Field> predicate
	){
		Builder b = new Builder(clazz);
		b.setObject(object);
		Arrays.stream(clazz.getFields()).filter(predicate).forEach(b::addField);
		return b.build();
	}

	public static String fromAll(final Class<?> clazz){
		return fromAll(clazz, null,
			f -> Modifier.isStatic(f.getModifiers())
		);
	}
	public static String fromAll(final Object object){
		return fromAll(object.getClass(), object,
			f -> !Modifier.isStatic(f.getModifiers())
		);
	}
	public static String fromAll(final Class<?> clazz, final Object object,
		final Predicate<Field> predicate
	){
		Builder b = new Builder(clazz);
		b.setObject(object);
		Arrays.stream(clazz.getDeclaredFields())
			.filter(predicate)
			.forEach(b::addField);
		return b.build();
	}

	// INNER CLASSES
	private static final class Builder{

		private static final char FIELD_LIST_START_DELIMITER = '{';
		private static final char FIELD_VALUE_DELIMITER = '=';
		private static final String INACCESSIBLE_FIELD = "(INACCESSIBLE)";
		private static final String UNDEFINED_FIELD = "(UNDEFINED)";
		private static final String CHARACTER_FORMAT = "'%c'";
		private static final String STRING_FORMAT = "\"%s\"";
		private static final char FIELD_LIST_ELEMENT_DELIMITER = ';';
		private static final char FIELD_LIST_END_DELIMITER = '}';
		private static final String OBJECT_HASHCODE_FORMAT = "(%d)";

		private final Class<?> clazz;
		private Object object = null;
		private final Collection<Field> fields = new ArrayList<>();

		public Builder(final Class<?> clazz){
			this.clazz = clazz;
		}

		// SETTERS
		public void setObject(final Object object){
			this.object = object;
		}
		public void addField(final Field field){
			fields.add(field);
		}

		// FUNCTIONS
		public String fieldToString(final Field field){
			StringBuilder sb = new StringBuilder(field.getName());

			if(!field.trySetAccessible()){
				sb.append(INACCESSIBLE_FIELD);
				return sb.toString();
			}

			sb.append(FIELD_VALUE_DELIMITER);

			try{
				if(field.getType().equals(char.class))
					sb.append(CHARACTER_FORMAT.formatted(
						field.getChar(object)
					));
				else if(field.getType().equals(String.class))
					sb.append(STRING_FORMAT.formatted(
						((String) field.get(object)).replace("\"", "\\\"")
					));
				else
					sb.append(field.get(object));
			}catch(IllegalAccessException e){
				sb.append(INACCESSIBLE_FIELD);
			}catch(NullPointerException e){
				sb.append(UNDEFINED_FIELD);
			}

			return sb.toString();
		}
		public String build(){
			StringBuilder sb = new StringBuilder(clazz.getName());

			if(object != null)
				sb.append(OBJECT_HASHCODE_FORMAT.formatted(object.hashCode()));

			sb.append(
				fields.stream()
					.map(this::fieldToString)
					.collect(Collectors.joining(
						String.valueOf(FIELD_LIST_ELEMENT_DELIMITER),
						String.valueOf(FIELD_LIST_START_DELIMITER),
						String.valueOf(FIELD_LIST_END_DELIMITER)
					))
			);

			return sb.toString();
		}
	}
}
