package dev.pimous.pu.jutils.base;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static org.junit.jupiter.api.Assertions.*;

class ProtectedScheduledExecutorTest{

	@Test
	void shutdown(){
		final ScheduledExecutorService ses
			= Executors.newSingleThreadScheduledExecutor();
		final ScheduledExecutorService protectedSES
			= new ProtectedScheduledExecutor(ses);

		assertThrows(OperationNotPermittedException.class,
			() -> protectedSES.shutdown()
		);
		assertThrows(OperationNotPermittedException.class,
			() -> protectedSES.shutdownNow()
		);

		ses.shutdownNow();
	}
}
