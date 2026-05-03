package dev.pimous.pu.jutils.app.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.util.logging.*;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class AppLoggerFormatter extends Formatter{

	private static final String LOG_HEAD_FORMAT
		= "STARTED logging at %1$tY/%1$tm/%1$tdT%1$tT.%2$09d:%n";
	private static final String LOG_FORMAT
		= "(%1$d - %2$tY/%2$tm/%2$tdT%2$tT.%3$09d){T:%4$d}[%5$s] %6$s%n";
	private static final String LOG_TAIL_FORMAT
		= "FINISHED logging at %1$tY/%1$tm/%1$tdT%1$tT.%2$09d.%n";
	private static final String COLORED_LEVEL_FORMAT
		= "\u001b[1;38;5;%2$dm%1$s\u001b[0m";

	private final boolean inConsole;

	public AppLoggerFormatter(boolean inConsole){
		this.inConsole = inConsole;
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

	@Override
	public String getHead(Handler h){
		final Instant i = Instant.now();
		return inConsole ? super.getHead(h) : LOG_HEAD_FORMAT.formatted(
			i.toEpochMilli(), i.getNano()
		);
	}
	@Override
	public String getTail(Handler h){
		final Instant i = Instant.now();
		return inConsole ? super.getTail(h) : LOG_TAIL_FORMAT.formatted(
			i.toEpochMilli(), i.getNano()
		);
	}

	// FUNCTIONS
	@Override
	public String format(LogRecord record){
		final StringBuilder msg = new StringBuilder();

		if(record.getMessage() != null)
			msg.append(record.getMessage().formatted(record.getParameters()));

		if(record.getThrown() != null){
			if(!msg.isEmpty())
				msg.append(System.lineSeparator());

			final StringWriter sw = new StringWriter();
			final PrintWriter pw = new PrintWriter(sw);
			record.getThrown().printStackTrace(pw);
			pw.close();
			msg.append(sw);
			msg.deleteCharAt(msg.length() - 1);
		}

		return LOG_FORMAT.formatted(
			record.getSequenceNumber(),
			record.getInstant().toEpochMilli(),
			record.getInstant().getNano(),
			record.getLongThreadID(),
			inConsole ? getColoredLevel(record.getLevel()) : record.getLevel(),
			msg.toString()
		);
	}
}