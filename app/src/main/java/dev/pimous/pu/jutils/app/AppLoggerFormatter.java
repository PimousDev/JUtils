package dev.pimous.pu.jutils.app;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class AppLoggerFormatter extends Formatter{

	private static final String LOG_FORMAT
		= "(%1$d - %2$tDT%2$tT.%2$tN){T:%3$d}[%4$s] %5$s%n";
	private static final String COLORED_LEVEL_FORMAT
		= "\u001b[1;38;5;%2$dm%1$s\u001b[0m";

	private final boolean colorized;

	public AppLoggerFormatter(boolean colorized){
		this.colorized = colorized;
	}

	// GETTERS
	private static String getColoredLevel(final Level level){
		return switch(level.getName()){
			case "SEVERE" -> COLORED_LEVEL_FORMAT.formatted(level.getName(), 1);
			case "WARNING" -> COLORED_LEVEL_FORMAT.formatted(
				level.getName(), 3
			);
			case "INFO" -> COLORED_LEVEL_FORMAT.formatted(level.getName(), 2);
			case "CONFIG" -> COLORED_LEVEL_FORMAT.formatted(level.getName(), 4);
			default -> COLORED_LEVEL_FORMAT.formatted(level.getName(), 5);
		};
	}

	// FUNCTIONS
	@Override
	public String format(LogRecord record){
		final StringBuilder msg = new StringBuilder(
			record.getMessage().formatted(record.getParameters())
		);

		if(record.getThrown() != null){
			if(!msg.isEmpty())
				msg.append(System.lineSeparator());

			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			record.getThrown().printStackTrace(pw);
			pw.close();
			msg.append(sw.toString());
			msg.deleteCharAt(msg.length() - 1);
		}

		return LOG_FORMAT.formatted(
			record.getSequenceNumber(),
			record.getMillis(),
			record.getLongThreadID(),
			colorized ? getColoredLevel(record.getLevel()) : record.getLevel(),
			msg.toString()
		);
	}
}