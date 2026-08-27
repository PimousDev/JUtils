package dev.pimous.pu.jutils.i18n;

import dev.pimous.pu.jutils.base.BadResourceException;
import dev.pimous.pu.jutils.i18n.impl.I18nConcreteBundle;
import dev.pimous.pu.jutils.i18n.util.LocaleResPaths;

import java.nio.charset.Charset;
import java.util.*;

/**
 * @apiNote "base" section is reserved for JUtils app sentences.
 *
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public class I18n{

	public static final String PROPERTIES_DIR = "i18n";
	private static final String BASE_SECTION_NAME = "base";

	public final Locale defaultLocale;
	private final Collection<String> sections;
	private I18nBundle bundle;

	public I18n(final Locale defaultLocale, final Collection<String> sections){
		this.defaultLocale = defaultLocale;
		this.sections = new ArrayList<>();
		this.sections.addAll(sections);
	}

	// GETTERS
	@Deprecated
	public static boolean isLocaleSupported(final Locale locale){
		return isLocaleSupported(locale, "general");
	}
	private static boolean isLocaleSupported(final Locale locale,
		String section
	){
		return ClassLoader.getSystemResource(
			LocaleResPaths.getLocaleResourcePath(locale, "general")
		) != null;
	}

	/** If the desired locale is de-CH-Linux and default locale is en-GB,
	 * candidates locales will be from most to least significant: de-CH-Linux,
	 * de-CH, de-Linux, de, en-GB-Linux and en-GB.
	 */
	private SequencedCollection<Locale> getCandidateLocales(
		final Locale locale
	){
		final List<Locale> locales = new ArrayList<>(2);

		if(!locale.getCountry().isEmpty())
			locales.add(Locale.of(locale.getLanguage(), locale.getCountry()));
		locales.add(Locale.of(locale.getLanguage()));
		if(!locales.contains(defaultLocale))
			locales.add(defaultLocale);

		if(!locale.getVariant().isEmpty()){
			for(int i = 0; i < locales.size(); i++){
				if(locales.get(i).getVariant().isEmpty()){
					locales.add(i, Locale.of(
						locales.get(i).getLanguage(),
						locales.get(i).getCountry(),
						locale.getVariant()
					));
					i++;
				}
			}
		}

		return locales;
	}
	public I18nBundle getBundle(){ return bundle; }

	// SETTERS
	public Locale load(final Locale locale, final Charset charset)
		throws BadResourceException
	{
		I18nConcreteBundle lastBundle = null;

		for(Locale l : getCandidateLocales(locale)){
			final I18nConcreteBundle b = loadBundle(l,
				charset,
				defaultLocale.equals(l)
			);

			if(b != null){
				if(lastBundle != null)
					lastBundle.setParent(b);
				else
					bundle = b;

				lastBundle = b;
			}
		}

		return bundle.getLocale();
	}

	// FUNCTIONS
	private I18nConcreteBundle loadBundle(final Locale locale,
		final Charset charset,
		final boolean strictCheck
	) throws BadResourceException{
		final I18nConcreteBundle b = new I18nConcreteBundle(locale);
		loadBundleSection(b, BASE_SECTION_NAME, charset, strictCheck);
		sections.forEach(s -> loadBundleSection(b, s, charset, strictCheck));
		return b;
	}
	private boolean loadBundleSection(final I18nConcreteBundle bundle,
		final String section,
		final Charset charset,
		final boolean strictCheck
	) throws BadResourceException{
		try{
			bundle.loadSection(section, charset);
		}catch(BadResourceException e){
			if(strictCheck) throw e;
			return false;
		}
		return true;
	}
}