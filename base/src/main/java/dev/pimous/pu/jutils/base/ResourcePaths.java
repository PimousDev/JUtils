package dev.pimous.pu.jutils.base;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class ResourcePaths{

	private static final char RESOURCE_DIRECTORY_SEPARATOR = '/';

	private ResourcePaths(){}

	// GETTERS
	public static String get(String first, String... more){
		return Path.of(first, more).toString().replace(
			FileSystems.getDefault().getSeparator().charAt(0),
			RESOURCE_DIRECTORY_SEPARATOR
		);
	}
}