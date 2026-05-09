package dev.pimous.pu.jutils.config;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VersionTest{

	@Test
	void constructors(){
		assertDoesNotThrow(() -> new Version(0, 0, 0));
		assertDoesNotThrow(
			() -> new Version(31, 1, 20, new Version.PreRelease('f', 24))
		);
		assertDoesNotThrow(() -> new Version(31, 1, 20, 'f', 24));
		assertDoesNotThrow(() -> new Version("31.1.20"));
		assertDoesNotThrow(() -> new Version("31.1.20-f.24"));

		assertDoesNotThrow(() -> new Version(0, 0, 0));
		assertDoesNotThrow(() -> new Version(255, 255, 255));
		assertThrows(IllegalArgumentException.class,
			() -> new Version(-1, -1, -1)
		);
		assertThrows(IllegalArgumentException.class,
			() -> new Version(256, 256, 256)
		);
	}
	@Test
	void getters(){
		Version v = new Version(31, 0, 20, 'f', 24);

		assertEquals('f', v.getPreRelease().identifier);
		assertEquals(24, v.getPreRelease().number);
	}
	@Test
	void stringify(){
		assertEquals("31.0.20", new Version(31, 0, 20).toString());
		assertEquals("31.0.20-f.24",
			new Version(31, 0, 20, 'f', 24).toString()
		);
	}

	@Nested
	class PreReleaseTest{

		@Test
		void constructors(){
			assertDoesNotThrow(() -> new Version.PreRelease('f', 31));

			assertDoesNotThrow(() -> new Version.PreRelease('a', 0));
			assertDoesNotThrow(() -> new Version.PreRelease('z', 255));
			assertThrows(IllegalArgumentException.class,
				() -> new Version.PreRelease((char) ('a' - 1), -1)
			);
			assertThrows(IllegalArgumentException.class,
				() -> new Version.PreRelease('a', -1)
			);
			assertThrows(IllegalArgumentException.class,
				() -> new Version.PreRelease((char) ('z' + 1), 255)
			);
			assertThrows(IllegalArgumentException.class,
				() -> new Version.PreRelease('z', 256)
			);
		}
		@Test
		void stringify(){
			assertEquals("f.31", new Version.PreRelease('f', 31).toString());
		}
	}
}
