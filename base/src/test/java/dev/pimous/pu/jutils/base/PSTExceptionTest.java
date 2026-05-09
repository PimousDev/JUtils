package dev.pimous.pu.jutils.base;

import org.junit.jupiter.api.Test;

import java.net.SocketTimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PSTExceptionTest{

	@Test
	void instanciation(){
		final SocketTimeoutException cause = new SocketTimeoutException();
		cause.bytesTransferred = 20;

		final PreciseSoTimeoutException e = new PreciseSoTimeoutException(
			1, 31, cause
		);
		assertEquals(cause, e.getSuppressed()[0]);
		assertEquals(1, e.delay);
		assertEquals(31, e.expectedBytes);
		assertEquals(20, e.bytesTransferred);
	}
}
