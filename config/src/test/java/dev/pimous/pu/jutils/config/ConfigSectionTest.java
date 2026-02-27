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
	private static class TestConfig extends ConfigSection{

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
	private static class SocketConfig extends ConfigSection{

		@ConfigSection.ConfigField
		private InetAddress host = InetAddress.getLoopbackAddress();
		@ConfigSection.ConfigField
		private short port = 31000;

		// GETTERS
		@Override
		protected Function<? super String, ?> getParser(String property){
			return switch(property){
				case "host" -> this::parseHost;
				case "port" -> this::parsePort;
				default -> super.getParser(property);
			};
		}

		// FUNCTIONS
		private InetAddress parseHost(final String value){
			try{
				return InetAddress.getByName(value);
			}catch(UnknownHostException e){
				throw new IllegalArgumentException(e);
			}
		}
		private short parsePort(final String value){
			try{
				Integer port = Integer.parseInt(value);

				if(port < 0 || port >= 65535)
					throw new NumberFormatException();

				return port.shortValue();
			}catch(final NumberFormatException e){
				throw new IllegalArgumentException(e);
			}
		}
	}
}
