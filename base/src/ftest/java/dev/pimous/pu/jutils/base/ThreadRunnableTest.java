package dev.pimous.pu.jutils.base;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreadRunnableTest{

	@Test
	void run(){
		Worker w = new Worker(null);
		assertFalse(w.mainFlag);
		assertNull(w.thrown);
		assertFalse(w.endFlag);
		assertDoesNotThrow(w::run);
		assertTrue(w.mainFlag);
		assertNull(w.thrown);
		assertTrue(w.endFlag);

		final RuntimeException e = new RuntimeException();
		w = new Worker(e);
		assertFalse(w.mainFlag);
		assertNull(w.thrown);
		assertFalse(w.endFlag);
		assertDoesNotThrow(w::run);
		assertFalse(w.mainFlag);
		assertEquals(e, w.thrown);
		assertTrue(w.endFlag);
	}

	// INNER CLASSES
	private static class Worker extends ThreadRunnable{

		final RuntimeException toThrow;
		Throwable thrown;
		boolean mainFlag = false;
		boolean endFlag = false;

		public Worker(final RuntimeException toThrow){
			this.toThrow = toThrow;
		}

		// INNER CLASSES
		@Override
		public void threadMain(){
			if(toThrow != null)
				throw toThrow;

			mainFlag = true;
		}
		@Override
		public void catchException(Exception e){
			thrown = e;
		}
		@Override
		public void threadEnd(){
			endFlag = true;
		}
	}
}
