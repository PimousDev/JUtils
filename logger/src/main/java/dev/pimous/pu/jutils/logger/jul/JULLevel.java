package dev.pimous.pu.jutils.logger.jul;

import java.io.Serial;
import java.util.logging.Level;

/**
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public final class JULLevel extends Level{

	@Serial
	private static final long serialVersionUID = -2740598380954898298L;

	public static final Level FATAL = new JULLevel("FATAL", 1200);
	public static final Level CRITICAL = new JULLevel("CRITICAL", 1100);
	public static final Level ERROR = new JULLevel("ERROR", 1000);
	public static final Level NOTICE = new JULLevel("NOTICE", 800);
	public static final Level INFORMATION = new JULLevel("INFO", 700);
	public static final Level DEBUG = new JULLevel("DEBUG", 600);
	public static final Level TRACE = new JULLevel("TRACE", 500);

	private JULLevel(final String name, final int value){
		super(name, value);
	}
}
