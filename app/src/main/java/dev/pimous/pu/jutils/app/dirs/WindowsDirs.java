package dev.pimous.pu.jutils.app.dirs;

import dev.pimous.pu.jutils.app.App;
import dev.pimous.pu.jutils.app.AppConfig;
import dev.pimous.pu.jutils.base.NotImplementedException;
import dev.pimous.pu.jutils.config.Configuration;

import java.nio.file.Path;

/** For "testApp" under group "pimous.dev", files will be:
 * <ul>
 *     <li>System: <ul>
 *         <li>Binary: C:\\Program Files\pimous.dev\testApp\bin</li>
 *         <li>Library: C:\\Program Files\pimous.dev\testApp\lib</li>
 *         <li>Config: C:\\ProgramData\pimous.dev\testApp\etc</li>
 *         <li>Data: C:\\ProgramData\pimous.dev\testApp\data</li>
 *         <li>State: C:\\ProgramData\pimous.dev\testApp\state<ul>
 *             <li>Log: .\log\</li>
 *             <li>Persistent Temporary: .\ptmp\</li>
 *         </ul></li>
 *         <li>Cache: C:\\ProgramData\pimous.dev\testApp\cache</li>
 *         <li>Temporary: C:\\Windows\Temp\pimous.dev\testApp</li>
 *     </ul></li>
 *     <li>User: <ul>
 *         <li>Binary: %USERPROFILE%\AppData\Roaming\pimous.dev\testApp\bin</li>
 *         <li>Library: %USERPROFILE%\AppData\Roaming\pimous.dev\testApp\lib</li>
 *         <li>Config: %USERPROFILE%\AppData\Roaming\pimous.dev\testApp\etc</li>
 *         <li>Data: %USERPROFILE%\AppData\Roaming\pimous.dev\testApp\data</li>
 *         <li>State: %USERPROFILE%\AppData\Local\pimous.dev\testApp\state<ul>
 *             <li>Log: .\log\</li>
 *             <li>Persistent Temporary: .\ptmp\</li>
 *         </ul></li>
 *         <li>Cache: %USERPROFILE%\AppData\Local\pimous.dev\testApp\cache\</li>
 *         <li>Temporary: %USERPROFILE%\AppData\Local\Temp\</li>
 *     </ul></li>
 * </ul>
 * @author Xibitol
 * @since 1.1.0
 */
public class WindowsDirs extends AbstractDirs{

	private static final Path SYSTEM_PROGRAM_DIR = Path.of("/Program Files");
	private static final Path SYSTEM_DATA_DIR = Path.of("/ProgramData");
	private static final Path SYSTEM_TEMP_DIR = Path.of("/Windows", "Temp");
	private static final Path USER_DATA_DIR = Path.of("AppData");
	private static final Path USER_ROAMING_DIR = USER_DATA_DIR.resolve(
		"Roaming"
	);
	private static final Path USER_LOCAL_DIR = USER_DATA_DIR.resolve("Local");

	public WindowsDirs(
		final AppConfig properties, final Configuration config,
		final boolean isSystem
	){
		super(properties, config, isSystem);
	}
	public WindowsDirs(final App<?> context, final boolean isSystem){
		super(context, isSystem);
	}

	// GETTERS
	private Path getUserRoamingDir(){
		return config.getSystem().getHome().resolve(USER_ROAMING_DIR);
	}
	private Path getUserLocalDir(){
		return config.getSystem().getHome().resolve(USER_LOCAL_DIR);
	}

	/** Folder is an assumption. */
	@Override
	public Path getBinaryDir(){
		return (isSystem ? SYSTEM_PROGRAM_DIR : getUserRoamingDir()).resolve(
			subfolders, BINARY_DIR
		);
	}
	/** Folder is an assumption. */
	@Override
	public Path getLibraryDir(){
		return composeDir(
			isSystem ? SYSTEM_PROGRAM_DIR : getUserRoamingDir(),
			subfolders.resolve(LIBRARY_DIR)
		);
	}
	@Override
	public Path getConfigDir(){
		return composeDir(
			isSystem ? SYSTEM_DATA_DIR : getUserRoamingDir(),
			subfolders.resolve(CONFIG_DIR)
		);
	}
	@Override
	public Path getDataDir(){
		return composeDir(
			isSystem ? SYSTEM_DATA_DIR : getUserRoamingDir(),
			subfolders.resolve(DATA_DIR)
		);
	}
	@Override
	public Path getStateDir(){
		return composeDir(
			isSystem ? SYSTEM_DATA_DIR : getUserLocalDir(),
			subfolders.resolve(STATE_DIR)
		);
	}
	@Override
	public Path getLogDir(){
		return composeDir(
			isSystem ? SYSTEM_DATA_DIR : getUserLocalDir(),
			subfolders.resolve(STATE_DIR, LOG_DIR)
		);
	}
	@Override
	public Path getPersistentTemporaryDir(){
		return composeDir(
			isSystem ? SYSTEM_DATA_DIR : getUserLocalDir(),
			subfolders.resolve(STATE_DIR, PERSISTENT_TEMPORARY_DIR)
		);
	}
	@Override
	public Path getCacheDir(){
		return composeDir(
			isSystem ? SYSTEM_DATA_DIR : getUserLocalDir(),
			subfolders.resolve(CACHE_DIR)
		);
	}
	@Override
	public Path getTemporaryDir(){
		return composeDir(
			isSystem ? SYSTEM_TEMP_DIR : config.getSystem().getTmpDir(),
			subfolders
		);
	}
}
