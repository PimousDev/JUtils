package dev.pimous.pu.jutils.i18n.util;

import dev.pimous.pu.jutils.base.ResourcePaths;
import dev.pimous.pu.jutils.i18n.I18n;

import java.util.Locale;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class LocaleResPaths{

	private static final String LANGUAGE_SEPARATOR = "_";

	private LocaleResPaths(){}

	// GETTERS
	public static String getLocaleResourcePath(final Locale locale,
		final String section
	){
		ResourcePaths.Builder rpb = new ResourcePaths.Builder(
			I18n.PROPERTIES_DIR
		);

		rpb.appendNode(locale.getLanguage());
		if(!locale.getCountry().isEmpty())
			rpb.append(LANGUAGE_SEPARATOR + locale.getCountry());

		if(!locale.getVariant().isEmpty())
			rpb.appendNode(locale.getVariant());

		return rpb.appendNode(
			ResourcePaths.PROPERTIES_FILENAME_FORMAT.formatted(section)
		).toString();
	}
}
