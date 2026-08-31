package dev.pimous.pu.jutils.app.util;

import dev.pimous.pu.jutils.app.App;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.FileHandler;

/**
 * @author Xibitol
 * @since 1.0.0
 */
public class AppFileHandler extends FileHandler{

	private static final String LOG_FILE_NAME_FORMAT = "%s_%%g.log";
	private static final byte LOG_FILE_COUNT_MAX = 8;
	private static final long LOG_FILE_SIZE_MAX = 5*1024*1024;

	public AppFileHandler(final App<?> context)
		throws IOException
	{
		super(
			context.getDirs().getLogDir().resolve(
				LOG_FILE_NAME_FORMAT.formatted(
					context.getProperties().getIdentifier()
				)
			).toString(),
			LOG_FILE_SIZE_MAX,
			LOG_FILE_COUNT_MAX,
			false
		);
	}
}