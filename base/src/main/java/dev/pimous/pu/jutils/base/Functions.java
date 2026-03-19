package dev.pimous.pu.jutils.base;

import java.util.function.Function;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public final class Functions{

	private Functions(){}

	// GETTERS
	public static <T> Function<Object, T> castFunction(final Class<T> clazz){
		return ((Function<Object, T>) clazz::cast);
	}
}
