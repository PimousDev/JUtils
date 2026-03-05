package dev.pimous.pu.jutils.i18n;

import dev.pimous.pu.jutils.base.BadResourceException;
import dev.pimous.pu.jutils.i18n.impl.I18nConcreteBundle;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;


class I18nConcreteBundleTest{

	static final String UNDEFINED_TEXT = "#UNDEFINED#";

	@Test
	void fieldGetters(){
		final I18nConcreteBundle b = new I18nConcreteBundle(Locale.UK);

		assertEquals(Locale.UK, b.getLocale());
		assertNull(b.getParent());

		assertThrows(IllegalArgumentException.class, () -> b.setParent(b));
	}

	@Test
	void loading(){
		final I18nConcreteBundle b = new I18nConcreteBundle(
			Locale.of("en", "FR", "Linux")
		);

		assertDoesNotThrow(() -> b.loadSection("bundle", Charset.defaultCharset()));
		assertEquals("Hello my %s; Did you bring %d pops?", b.getSentence("a"));
		assertEquals(
			"Life is just a sequence of failures, sometimes strewn with forgettable successes.",
			b.getSentence("f")
		);

		Exception e = assertThrows(BadResourceException.class,
			() -> b.loadSection("missing", Charset.defaultCharset())
		);
		assertTrue(e.getCause() instanceof FileNotFoundException);
		e = assertThrows(BadResourceException.class,
			() -> b.loadSection("malformed", Charset.defaultCharset())
		);
		assertTrue(e.getCause() instanceof IllegalArgumentException);
	}

	@Test
	void sentence(){
		final I18nConcreteBundle b = new I18nConcreteBundle(
			Locale.of("en", "FR", "Linux")
		);
		final I18nConcreteBundle bParent = new I18nConcreteBundle(
			Locale.of("en", "FR", "Linux")
		);
		b.loadSection("bundle", Charset.forName("UTF-8"));
		bParent.loadSection("parent", Charset.forName("UTF-8"));

		assertEquals("Hello my darling; Did you bring 3 pops?",
			b.getSentence("a", "darling", 3)
		);
		assertEquals(
			"Life is just a sequence of failures, sometimes strewn with forgettable successes.",
			b.getSentence("f")
		);
		assertEquals(UNDEFINED_TEXT, b.getSentence("l"));
		assertEquals(
			"Nous sommes le 31 January 2020 à 10:13 ou 10:13 am pour les algos axons (Sans oublier: 24.311311)",
			b.getLocalDateTime("date",
				LocalDateTime.of(2020, 1, 31, 10, 13, 24, 311311)
			)
		);

		b.setParent(bParent);
		assertEquals("Hello my darling; Did you bring 3 pops?",
			b.getSentence("a", "darling", 3)
		);
		assertEquals(
			"Life is just a sequence of failures, sometimes strewn with forgettable successes.",
			b.getSentence("f")
		);
		assertEquals(
			"Don't worry, be happy martin!",
			b.getSentence("l", "martin")
		);
		assertEquals(UNDEFINED_TEXT, b.getSentence("undefined"));
	}
}
