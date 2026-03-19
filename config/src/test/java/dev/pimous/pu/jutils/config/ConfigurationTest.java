package dev.pimous.pu.jutils.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest{

	final Map<String, String> env = Map.of(
		"java.io.tmpdir", "tmp/"
	);

	@BeforeEach
	void deleteFile(){
		ServerConfig.file.delete();
	}

	@Test
	void getters(){
		final ServerConfig config = new ServerConfig(
			getProperties("config/system.properties"), env
		);

		assertEquals(ServerConfig.file, config.getFile());

		assertTrue(config.hasSection(ConfigSectionTest.TestConfig.class));
		assertFalse(config.hasSection(DummyConfig.class));
		assertDoesNotThrow(
			() -> config.getSection(ConfigSectionTest.TestConfig.class)
		);
		assertDoesNotThrow(() -> config.getSection(SocketConfig.class));
		assertThrows(NoSuchElementException.class,
			() -> config.getSection(DummyConfig.class)
		);

		assertThrows(IllegalArgumentException.class,
			() -> config.get("host").get()
		);
		assertEquals("test", config.getString("test.a").get());
		assertEquals("F", config.get("test.name").get());
		assertEquals(20, config.get("test.number").get());
		assertThrows(ClassCastException.class,
			() -> config.getString("test.number")
		);
		assertEquals(InetAddress.getLoopbackAddress(),
			config.get("socket.host").get()
		);
		assertEquals((short) 31000, config.get("socket.port").get());
		assertThrows(NoSuchElementException.class,
			() -> config.get("undefined.l").get()
		);

		assertEquals("GillardeauOS", config.getSystem().getOSName());
		assertEquals(".", config.getSystem().getHome());

		assertEquals(env.get("java.io.tmpdir"),
			config.getEnv("java.io.tmpdir", "test")
		);
		assertEquals("test", config.getEnv("undefined", "test"));

		assertEquals(new File("config"), config.getConfigDir(""));
		assertEquals(new File("data"), config.getDataDir(""));
		assertEquals(new File("cache"), config.getCacheDir(""));
		assertEquals(new File("tmp"), config.getTempDir(""));
		assertEquals(new File("log"), config.getLogDir(""));
	}

	@Test
	void loadingStream(){
		final ServerConfig config = new ServerConfig(
			getProperties("config/system.properties"), env
		);

		assertDoesNotThrow(() -> config.load(
			ClassLoader.getSystemResourceAsStream("config/server.properties")
		));

		final ConfigSectionTest.TestConfig tc = config.getSection(
			ConfigSectionTest.TestConfig.class
		);
		assertEquals("A", tc.getString("name").get());
		assertEquals(24, tc.get("number").get());
		assertEquals("31.1.20-b.20", tc.get("version").get().toString());
		assertEquals("test2", tc.get("a").get());
		final SocketConfig sc = config.getSection(SocketConfig.class);
		assertEquals(Inet4Address.ofLiteral("84.234.17.190"),
			sc.get("host").get()
		);
		assertEquals((short) 31012, sc.get("port").get());

		assertDoesNotThrow(() -> config.load(
			ClassLoader.getSystemResourceAsStream(
				"config/serverUndefined.properties"
			)
		));
	}

	@Test
	void toProperties(){
		final ServerConfig config = new ServerConfig(
			getProperties("config/system.properties"), env
		);
		Properties props;

		props = config.toProperties();
		assertEquals(6, props.size());
		assertEquals("f", props.getProperty("test.name"));
		assertEquals("20", props.getProperty("test.number"));
		assertEquals("null", props.getProperty("test.version"));
		assertEquals("test", props.getProperty("test.a"));
		assertEquals("localhost/127.0.0.1", props.getProperty("socket.host"));
		assertEquals("31000", props.getProperty("socket.port"));

		props = config.toSavedProperties();
		assertEquals(1, props.size());
		assertEquals("20", props.getProperty("test.number"));
	}

	@Test
	void saving(){
		final ServerConfig config = new ServerConfig(
			getProperties("config/system.properties"), env
		);

		assertTrue(!ServerConfig.file.exists());
		assertDoesNotThrow(() -> config.save(""));
		assertTrue(ServerConfig.file.exists());
		assertTrue(ServerConfig.file.length() > 0);
		try(FileReader fis = new FileReader(ServerConfig.file)){
			final Properties props = new Properties();
			props.load(fis);
			assertEquals(6, props.size());
		}catch(final IOException ignored){}

		config.getSection(ConfigSectionTest.TestConfig.class).number = 124;
		config.getSection(SocketConfig.class).port = 31012;

		final long oldLength = ServerConfig.file.length();
		assertDoesNotThrow(() -> config.save(""));
		assertEquals(oldLength + 1, ServerConfig.file.length());
		try(FileReader fis = new FileReader(ServerConfig.file)){
			final Properties props = new Properties();
			props.load(fis);

			assertEquals(6, props.size());
			assertEquals("124", props.getProperty("test.number"));
			assertEquals("31000", props.getProperty("socket.port"));
		}catch(final IOException ignored){}
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
	private static class SocketConfig extends ConfigSection{

		@ConfigSection.ConfigField
		private InetAddress host = InetAddress.getLoopbackAddress();
		@ConfigSection.ConfigField
		public short port = 31000;

		// GETTERS
		@Override
		protected Function<String, ?> getParser(String property){
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
	private static class DummyConfig extends ConfigSection{}

	private static class ServerConfig extends Configuration{

		public static final File file = new File("server.properties");

		{
			addSection("test", new ConfigSectionTest.TestConfig());
			addSection("socket", new SocketConfig());
		}

		public ServerConfig(final Properties system){
			super(file, system);
		}
		public ServerConfig(
			final Properties system, final Map<String, String> env
		){
			super(file, system, env);
		}
	}
}
