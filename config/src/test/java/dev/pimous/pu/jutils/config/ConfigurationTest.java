package dev.pimous.pu.jutils.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest{

	public static final Path PATH = Path.of("server.properties");

	final Map<String, String> env = Map.of(
		"java.io.tmpdir", "tmp/"
	);

	@BeforeEach
	void deleteFile(){
		try{
			Files.deleteIfExists(PATH);
		}catch(IOException ignored){}

		if(Files.exists(PATH))
			throw new RuntimeException();
	}

	@Test
	void getters(){
		final ServerConfig config = new ServerConfig(
			getProperties("config/system.properties"), env
		);

		assertNull(config.getPath());
		assertNull(config.getFile()); // TODO: Deprecated
		config.setPath(PATH);
		assertEquals(PATH, config.getPath());
		config.setFile(PATH.toFile()); // TODO: Deprecated
		assertEquals(PATH.toFile(), config.getFile()); // TODO: Deprecated

		assertTrue(config.hasSection(ConfigSectionTest.TestConfig.class));
		assertFalse(config.hasSection(DummyConfig.class));
		assertDoesNotThrow(
			() -> config.getSection(ConfigSectionTest.TestConfig.class)
		);
		assertDoesNotThrow(() -> config.getSection(SocketConfig.class));
		assertThrows(NoSuchElementException.class,
			() -> config.getSection(DummyConfig.class)
		);
		assertEquals(2, config.getSectionCount());

		assertThrows(IllegalArgumentException.class,
			() -> config.get("host").orElseThrow()
		);
		assertEquals("test", config.getString("test.a").orElseThrow());
		assertEquals("F", config.get("test.name").orElseThrow());
		assertEquals(20, config.get("test.number").orElseThrow());
		assertThrows(ClassCastException.class,
			() -> config.getString("test.number")
		);
		assertEquals(InetAddress.getLoopbackAddress(),
			config.get("socket.host").orElseThrow()
		);
		assertEquals((short) 31000, config.get("socket.port").orElseThrow());
		assertThrows(NoSuchElementException.class,
			() -> config.get("undefined.l").orElseThrow()
		);

		assertEquals("GillardeauOS", config.getSystem().getOSName());
		assertEquals(Path.of("."), config.getSystem().getHome());
		assertEquals(new File("."), config.getSystem().getHomeFile()); // TODO: Deprecated
		assertEquals(Path.of("."), config.getSystem().getWorkingDir());
		assertEquals(new File("."), config.getSystem().getWorkingDirFile()); // TODO: Deprecated

		assertEquals(env.get("java.io.tmpdir"),
			config.getEnv("java.io.tmpdir", "test")
		);
		assertEquals("test", config.getEnv("undefined", "test"));
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
		assertEquals("A", tc.getString("name").orElseThrow());
		assertEquals(24, tc.get("number").orElseThrow());
		assertEquals("31.1.20-b.20",
			tc.get("version").orElseThrow().toString()
		);
		assertEquals("test2", tc.get("a").orElseThrow());
		final SocketConfig sc = config.getSection(SocketConfig.class);
		assertEquals(Inet4Address.ofLiteral("84.234.17.190"),
			sc.get("host").orElseThrow()
		);
		assertEquals((short) 31012, sc.get("port").orElseThrow());

		assertDoesNotThrow(() -> config.load(
			ClassLoader.getSystemResourceAsStream(
				"config/serverUndefined.properties"
			)
		));
	}

	@Test
	void filesystemException(){
		final ServerConfig config = new ServerConfig(
			getProperties("config/system.properties"), env
		);

		config.setPath(PATH);
		var e = assertThrows(IOException.class, () -> config.load());
		assertInstanceOf(NoSuchFileException.class, e.getCause());
	}

	@Test
	void toProperties(){
		final ServerConfig config = new ServerConfig(
			getProperties("config/system.properties"), env
		);
		Properties props;

		props = config.toProperties();
		assertEquals(5, props.size());
		assertEquals("f", props.getProperty("test.name"));
		assertEquals("20", props.getProperty("test.number"));
		assertNull(props.getProperty("test.version"));
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

		assertThrows(RuntimeException.class, () -> config.save(""));

		config.setPath(PATH);

		assertFalse(Files.exists(PATH));
		assertDoesNotThrow(() -> config.save(""));
		assertTrue(Files.exists(PATH));
		assertDoesNotThrow(() -> assertTrue(Files.size(PATH) > 0));
		try(BufferedReader fbr = Files.newBufferedReader(PATH)){
			final Properties props = new Properties();
			props.load(fbr);
			assertEquals(5, props.size());
		}catch(final IOException ignored){}

		config.getSection(ConfigSectionTest.TestConfig.class).number = 124;
		config.getSection(SocketConfig.class).port = 31012;

		final long oldLength = assertDoesNotThrow(() -> Files.size(PATH));
		assertDoesNotThrow(() -> config.save(""));
		assertEquals(oldLength + 1, assertDoesNotThrow(() -> Files.size(PATH)));
		try(BufferedReader fbr = Files.newBufferedReader(PATH)){
			final Properties props = new Properties();
			props.load(fbr);

			assertEquals(5, props.size());
			assertEquals("124", props.getProperty("test.number"));
			assertEquals("31000", props.getProperty("socket.port"));
		}catch(final IOException ignored){}
	}

	// FUNCTIONS
	@SuppressWarnings("SameParameterValue")
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
				int port = Integer.parseInt(value);

				if(port < 0 || port >= 65535)
					throw new NumberFormatException();

				return (short) port;
			}catch(final NumberFormatException e){
				throw new IllegalArgumentException(e);
			}
		}
	}
	private static class DummyConfig extends ConfigSection{}

	private static class ServerConfig extends Configuration{

		{
			addSection("test", new ConfigSectionTest.TestConfig());
			addSection("socket", new SocketConfig());
		}

		public ServerConfig(final Properties system){
			super(system);
		}
		public ServerConfig(
			final Properties system, final Map<String, String> env
		){
			super(system, env);
		}
	}
}
