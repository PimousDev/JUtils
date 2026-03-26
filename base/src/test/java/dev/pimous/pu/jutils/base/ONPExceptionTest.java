package dev.pimous.pu.jutils.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ONPExceptionTest{

	@Test
	void instanciation(){
		final Exception cause = new Exception();
		Exception e;

		e = new OperationNotPermittedException("afl");
		assertEquals("afl", e.getMessage());

		e = new OperationNotPermittedException("afl", cause);
		assertEquals("afl", e.getMessage());
		assertEquals(cause, e.getCause());

		e = new OperationNotPermittedException(cause);
		assertEquals(cause, e.getCause());
	}
}
