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
}
