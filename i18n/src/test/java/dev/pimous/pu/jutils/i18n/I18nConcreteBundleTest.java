package dev.pimous.pu.jutils.i18n;

import dev.pimous.pu.jutils.base.BadResourceException;
import dev.pimous.pu.jutils.i18n.impl.I18nConcreteBundle;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
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
		assertEquals("Hello my %s; Did you bring %d pops?", b.get("a"));
		assertEquals(
			"Life is just a sequence of failures, sometimes strewn with forgettable successes.",
			b.get("f")
		);
		assertEquals(
			"Hello my %s; Did you bring %d pops?",
			b.get(new Sentence("a"))
		);
		assertEquals(
			"Life is just a sequence of failures, sometimes strewn with forgettable successes.",
			b.get(new AF())
		);

		Exception e = assertThrows(BadResourceException.class,
			() -> b.loadSection("missing", Charset.defaultCharset())
		);
		assertInstanceOf(NoSuchFileException.class, e.getCause());
		e = assertThrows(BadResourceException.class,
			() -> b.loadSection("malformed", Charset.defaultCharset())
		);
		assertInstanceOf(IllegalArgumentException.class, e.getCause());
	}

	@Test
	void sentence(){
		final I18nConcreteBundle b = new I18nConcreteBundle(
			Locale.of("en", "FR", "Linux")
		);
		final I18nConcreteBundle bParent = new I18nConcreteBundle(
			Locale.of("en", "FR", "Linux")
		);
		b.loadSection("bundle", StandardCharsets.UTF_8);
		bParent.loadSection("parent", StandardCharsets.UTF_8);

		assertEquals("Hello my darling; Did you bring 3 pops?",
			b.get("a", "darling", 3)
		);
		assertEquals(
			"Hello my fiancé; Did you bring 12 pops?",
			b.get(new Sentence("a", "fiancé", 12))
		);
		assertEquals(
			"Life is just a sequence of failures, sometimes strewn with forgettable successes.",
			b.get("f")
		);
		assertEquals(UNDEFINED_TEXT, b.get("l"));
		assertEquals(
			"Nous sommes le 31 January 2020 à 10:13 ou 10:13 am pour les anglo-saxons (Sans oublier: 24.311311)",
			b.getLocalDateTime("date",
				LocalDateTime.of(2020, 1, 31, 10, 13, 24, 311311)
			)
		);

		b.setParent(bParent);
		assertEquals("Hello my darling; Did you bring 3 pops?",
			b.get("a", "darling", 3)
		);
		assertEquals(
			"Life is just a sequence of failures, sometimes strewn with forgettable successes.",
			b.get("f")
		);
		assertEquals("Don't worry, be happy martin!", b.get("l", "martin"));
		assertEquals(UNDEFINED_TEXT, b.get("undefined"));
	}

	// INNER CLASSES
	private static class AF implements Localizable{

		@Override
		public Sentence getSentence(){
			return new Sentence("f");
		}
	}
}
