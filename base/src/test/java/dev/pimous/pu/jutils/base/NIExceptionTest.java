package dev.pimous.pu.jutils.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class INExceptionTest{

	@Test
	void instanciation(){
		final Exception cause = new Exception();
		Exception e;

		e = new NotImplementedException("afl");
		assertEquals("afl", e.getMessage());

		e = new NotImplementedException("afl", cause);
		assertEquals("afl", e.getMessage());
		assertEquals(cause, e.getCause());
	}
}
