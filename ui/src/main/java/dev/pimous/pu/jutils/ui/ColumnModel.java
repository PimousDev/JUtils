package dev.pimous.pu.jutils.ui;

import java.util.function.Function;

/**
 * @author APG-Gillardeau
 * @since 1.1.0
 */
public interface ColumnModel{

	// GETTERS
	String getName();
	Class<?> getType();
	Function<Object, ?> getParser();
}
