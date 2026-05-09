package dev.pimous.pu.jutils.i18n;

import dev.pimous.pu.jutils.i18n.util.LocaleResPaths;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class LocaleResPathsTest{

	@Test
	void getter(){
		assertEquals("i18n/en/a.properties",
			LocaleResPaths.getLocaleResourcePath(Locale.of("en"), "a")
		);
		assertEquals("i18n/en_FR/f.properties",
			LocaleResPaths.getLocaleResourcePath(
				Locale.of("en", "FR"), "f"
			)
		);
		assertEquals("i18n/en_FR/Linux/l.properties",
			LocaleResPaths.getLocaleResourcePath(
				Locale.of("en", "FR", "Linux"), "l"
			)
		);
		assertEquals("i18n/en/Linux/m.properties",
			LocaleResPaths.getLocaleResourcePath(
				Locale.of("en", "", "Linux"), "m"
			)
		);
	}
}
