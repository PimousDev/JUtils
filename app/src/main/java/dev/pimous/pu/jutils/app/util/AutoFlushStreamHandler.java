package dev.pimous.pu.jutils.app.util;

import java.io.PrintStream;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class AutoFlushStreamHandler extends StreamHandler{

	public AutoFlushStreamHandler(
		final PrintStream stream,
		final Formatter formatter
	){
		super(stream, formatter);
	}

	// GETTERS
	@Override
	public void publish(LogRecord record){
		super.publish(record);
		super.flush();
	}
}
