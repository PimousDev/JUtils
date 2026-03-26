package dev.pimous.pu.jutils.i18n;

import dev.pimous.pu.jutils.base.BadResourceException;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class I18nTest{

	@Test
	void constructor(){
		assertDoesNotThrow(
			() -> new I18n(Locale.ENGLISH, Collections.emptyList())
		);
		assertThrows(IllegalArgumentException.class,
			() -> new I18n(Locale.CHINA, Collections.emptyList())
		);
	}

	@Test
	void getters(){
		assertTrue(I18n.isLocaleSupported(Locale.ENGLISH));
		assertFalse(I18n.isLocaleSupported(Locale.CHINA));
	}

	@Test
	void loading(){
		final I18n i18n = new I18n(Locale.of("en"), List.of("test"));

		assertEquals(Locale.of("en", "FR", "Linux"),
			i18n.load(Locale.of("en", "FR", "Linux"), StandardCharsets.UTF_8)
		);
		assertEquals(Locale.of("en", "FR", "Linux"),
			i18n.getBundle().getLocale()
		);
		assertEquals(Locale.of("en", "FR"),
			i18n.getBundle().getParent().getLocale()
		);
		assertEquals(Locale.of("en", "", "Linux"),
			i18n.getBundle().getParent().getParent().getLocale()
		);
		assertEquals(Locale.of("en"),
			i18n.getBundle().getParent().getParent().getParent().getLocale()
		);
		assertNull(
			i18n.getBundle().getParent().getParent().getParent().getParent()
		);
		assertEquals("1", i18n.getBundle().getSentence("a"));
		assertEquals("2", i18n.getBundle().getSentence("b"));
		assertEquals("3", i18n.getBundle().getSentence("c"));
		assertEquals("4", i18n.getBundle().getSentence("d"));
		assertEquals("5", i18n.getBundle().getSentence("e"));

		final I18n erroneous = new I18n(Locale.of("en"), List.of("undefined"));
		assertThrows(BadResourceException.class,
			() -> erroneous.load(
				Locale.of("en", "FR", "Linux"), StandardCharsets.UTF_8
			)
		);
	}
}
