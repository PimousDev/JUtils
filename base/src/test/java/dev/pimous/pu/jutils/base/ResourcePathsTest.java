package dev.pimous.pu.jutils.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourcePathsTest{

	@Test
	void getter(){
		assertEquals("test/f/a/love.properties",
			ResourcePaths.get("test", "f/a", "love.properties")
		);
	}

	@Test
	void builder(){
		assertEquals("fa/l",
			new ResourcePaths.Builder()
				.appendNode("f")
				.append("a")
				.appendNode("l")
				.toString()
		);

		assertEquals("f/al",
			new ResourcePaths.Builder("f")
				.appendNode("a")
				.append("l")
				.toString()
		);
	}
}
