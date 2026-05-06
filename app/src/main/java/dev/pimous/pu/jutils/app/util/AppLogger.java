package dev.pimous.pu.jutils.app.util;

import dev.pimous.pu.jutils.app.App;
import dev.pimous.pu.jutils.logger.JULAdapter;

import java.io.IOException;
import java.util.logging.*;

public class AppLogger extends JULAdapter{

	private final Logger rootJulLogger;

	public AppLogger(final String identifier){
		super(Logger.getLogger(identifier));

		rootJulLogger = Logger.getLogger("");
		for(Handler h : rootJulLogger.getHandlers())
			rootJulLogger.removeHandler(h);
	}

	// SETTERS
	public void setLevel(final dev.pimous.pu.jutils.logger.Level level){
		rootJulLogger.setLevel(JULAdapter.mapLevel(level));
	}

	// FUNCTIONS
	public void loadConsoleHandlers(final App<?> context, final boolean hasGUI){
		final Formatter formatter = new AppLoggerFormatter(true);
		final Handler errHandler = new AutoFlushStreamHandler(
			context.err, formatter
		);
		errHandler.setLevel(Level.ALL);

		if(hasGUI){
			errHandler.setLevel(Level.WARNING);

			final Handler outHandler = new AutoFlushStreamHandler(
				context.out, formatter
			);
			outHandler.setLevel(Level.ALL);
			rootJulLogger.addHandler(outHandler);
		}
		rootJulLogger.addHandler(errHandler);
	}
	public void loadFileHandler(final App<?> context) throws IOException{
		final FileHandler fh = new AppFileHandler(context);
		fh.setFormatter(new AppLoggerFormatter(false));
		fh.setLevel(Level.ALL);
		rootJulLogger.addHandler(fh);
	}
}
