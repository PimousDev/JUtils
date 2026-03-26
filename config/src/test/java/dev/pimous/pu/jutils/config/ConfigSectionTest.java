package dev.pimous.pu.jutils.config;

import dev.pimous.pu.jutils.base.Functions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class ConfigSectionTest{

	@Test
	void getters(){
		final TestConfig tc = new TestConfig();

		assertEquals(Function.identity(), tc.getParser("a"));

		assertEquals("test", tc.getString("a").orElseThrow());
		assertThrows(NoSuchElementException.class,
			() -> tc.get("l").orElseThrow()
		);
		assertEquals("F", tc.get("name").orElseThrow());
		assertEquals(20, tc.get("number").orElseThrow());
		assertThrows(ClassCastException.class, () -> tc.getString("number"));
	}

	@Test
	void loading(){
		final TestConfig tc = new TestConfig();

		assertDoesNotThrow(
			() -> tc.load(getProperties("section/test.properties"))
		);
		assertEquals("A", tc.f);
		assertEquals(24, tc.number);
		assertEquals("31.1.20-b.20", tc.version.toString());
		assertEquals("test2", tc.a);

		assertDoesNotThrow(
			() -> tc.load(getProperties("section/testMand.properties"))
		);
		assertEquals("31.1.20-b.24", tc.version.toString());

		assertDoesNotThrow(
			() -> tc.load(getProperties("section/testUndefined.properties"))
		);
		assertEquals("31.1.20-b.26", tc.version.toString());
	}
	@Test
	void loadingError(){
		final TestConfig tc = new TestConfig();

		assertThrows(IllegalArgumentException.class,
			() -> tc.load(getProperties("section/testIllegal.properties"))
		);
		assertThrows(ConfigPropertyException.class,
			() -> tc.load(getProperties("section/testMissing.properties"))
		);
	}

	@Test
	void toProperties(){
		final TestConfig tc = new TestConfig();
		Properties props;

		props = tc.toProperties();
		assertEquals(4, props.size());
		assertEquals("f", props.getProperty("name"));
		assertEquals("20", props.getProperty("number"));
		assertEquals("null", props.getProperty("version"));
		assertEquals("test", props.getProperty("a"));

		props = tc.toSavedProperties();
		assertEquals(1, props.size());
		assertEquals("20", props.getProperty("number"));
	}

	// FUNCTIONS
	private Properties getProperties(String resource){
		final Properties props = new Properties();

		try(final InputStream is = ClassLoader.getSystemResourceAsStream(
			resource
		)){
			props.load(is);
		}catch(Exception ignored){}

		return props;
	}

	// INNER CLASSES
	@SuppressWarnings("CanBeFinal")
	public static class TestConfig extends ConfigSection{

		@ConfigSection.ConfigField(property = "name")
		private String f = "F";
		@ConfigSection.ConfigField(modifiable = true)
		public int number = 20;
		@ConfigSection.ConfigField(mandatory = true)
		private Version version;
		@ConfigSection.ConfigField()
		private String a = "test";

		// GETTERS
		@Override
		protected Function<String, ?> getParser(String property){
			return switch(property){
				case "number" -> Integer::parseInt;
				case "version" -> Version::new;
				default -> super.getParser(property);
			};
		}
		@SuppressWarnings("SwitchStatementWithTooFewBranches")
		@Override
		protected Function<Object, CharSequence> getFormatter(String property){
			return switch(property){
				case "name" -> Functions.castFunction(String.class).andThen(
					String::toLowerCase
				);
				default -> super.getFormatter(property);
			};
		}
	}
}
