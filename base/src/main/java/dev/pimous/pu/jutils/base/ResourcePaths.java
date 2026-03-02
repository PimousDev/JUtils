package dev.pimous.pu.jutils.base;

import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class ResourcePaths{

	private static final char RESOURCE_DIRECTORY_SEPARATOR = '/';
	public static final String PROPERTIES_FILENAME_FORMAT = "%s.properties";

	private ResourcePaths(){}

	// GETTERS
	public static String get(String first, String... more){
		return Path.of(first, more).toString().replace(
			FileSystems.getDefault().getSeparator().charAt(0),
			RESOURCE_DIRECTORY_SEPARATOR
		);
	}

	// INNER CLASSES
	public static class Builder{

		private String path;

		public Builder(final String base){
			this.path = base;
		}
		public Builder(){
			this("");
		}

		// SETTERS
		public Builder append(final String string){
			this.path += string;
			return this;
		}
		public Builder appendNode(final String filename){
			if(this.path.length() > 0)
				this.path += RESOURCE_DIRECTORY_SEPARATOR;

			this.path += filename;
			return this;
		}

		// FUNCTIONS
		@Override
		public String toString(){ return path; }
	}
}