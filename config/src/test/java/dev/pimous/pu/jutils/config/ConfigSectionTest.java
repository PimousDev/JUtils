package dev.pimous.pu.jutils.config;

import org.junit.jupiter.api.Test;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class ConfigSectionTest{

	@Test
	void getters(){
		final TestConfig tc = new TestConfig();

		assertEquals(Function.identity(), tc.getParser("a"));

		assertEquals("test", tc.getString("a").get());
		assertThrows(NoSuchElementException.class, () -> tc.get("l").get());
		assertEquals("F", tc.get("name").get());
		assertEquals(20, tc.get("number").get());
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

	// FUNCTIONS
	private Properties getProperties(String resource){
		final Properties props = new Properties();

		try{
			props.load(ClassLoader.getSystemResourceAsStream(resource));
		}catch(Exception ignored){}

		return props;
	}

	// INNER CLASSES
	public static class TestConfig extends ConfigSection{

		@ConfigSection.ConfigField(property = "name")
		private String f = "F";
		@ConfigSection.ConfigField(readonly = false)
		private int number = 20;
		@ConfigSection.ConfigField(mandatory = true)
		private Version version;
		@ConfigSection.ConfigField()
		private String a = "test";

		// GETTERS
		@Override
		protected Function<? super String, ?> getParser(String property){
			return switch(property){
				case "number" -> Integer::parseInt;
				case "version" -> Version::new;
				default -> super.getParser(property);
			};
		}
	}
}
