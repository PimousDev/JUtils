package dev.pimous.pu.jutils.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ONPExceptionTest{

	@Test
	void instanciation(){
		Exception e;

		e = new OperationNotPermittedException();
		assertNull(e.getMessage());

		e = new OperationNotPermittedException("afl");
		assertEquals("afl", e.getMessage());
	}
}
