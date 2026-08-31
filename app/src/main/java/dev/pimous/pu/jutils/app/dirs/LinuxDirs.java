package dev.pimous.pu.jutils.app.dirs;

import dev.pimous.pu.jutils.app.App;
import dev.pimous.pu.jutils.app.AppConfig;
import dev.pimous.pu.jutils.base.NotImplementedException;
import dev.pimous.pu.jutils.config.Configuration;

import java.nio.file.Path;

/** For "testApp" under group "pimous.dev", files will be:
 * <ul>
 *     <li>System: <ul>
 *         <li>Binary: /usr/local/bin/</li>
 *         <li>Library: /usr/local/lib/pimous.dev/testApp/</li>
 *         <li>Config: /usr/local/etc/pimous.dev/testApp/</li>
 *         <li>Data: /usr/local/share/pimous.dev/testApp/</li>
 *         <li>State: /var/local/pimous.dev/testApp/state/<ul>
 *             <li>Log: ./log/</li>
 *             <li>Persistent Temporary: ./ptmp/</li>
 *         </ul></li>
 *         <li>Cache: /var/local/pimous.dev/testApp/cache/</li>
 *         <li>Temporary: /tmp/pimous.dev/testApp/</li>
 *     </ul></li>
 *     <li>User: <ul>
 *         <li>Binary: $HOME/.local/bin/</li>
 *         <li>Library: $HOME/.local/lib/pimous.dev/testApp/</li>
 *         <li>Config: $HOME/.config/pimous.dev/testApp/</li>
 *         <li>Data: $HOME/.local/share/pimous.dev/testApp/</li>
 *         <li>State: $HOME/.local/state/pimous.dev/testApp/<ul>
 *             <li>Log: ./log/</li>
 *             <li>Persistent Temporary: ./ptmp/</li>
 *         </ul></li>
 *         <li>Cache: $HOME/.cache/pimous.dev/testApp/</li>
 *         <li>Temporary: /tmp/pimous.dev/testApp/</li>
 *     </ul></li>
 * </ul>
 * @author Xibitol
 * @since 1.0.0
 */
public class LinuxDirs extends AbstractDirs{

	private static final Path SYSTEM_LOCAL_DIR = Path.of("/usr/local");
	private static final Path SYSTEM_VARIABLE_DIR = Path.of("/var/local");
	private static final Path USER_CONFIG_DIR = Path.of(".config");
	private static final Path USER_LOCAL_DIR = Path.of(".local");
	private static final Path USER_CACHE_DIR = Path.of(".cache");

	private static final Path SYSTEM_BINARY_DIR
		= SYSTEM_LOCAL_DIR.resolve("bin");
	private static final Path SYSTEM_LIBRARY_DIR
		= SYSTEM_LOCAL_DIR.resolve("lib");
	private static final Path SYSTEM_CONFIG_DIR
		= SYSTEM_LOCAL_DIR.resolve("etc");
	private static final Path SYSTEM_DATA_DIR
		= SYSTEM_LOCAL_DIR.resolve("share");

	private static final Path USER_BINARY_DIR = USER_LOCAL_DIR.resolve("bin");
	private static final Path USER_LIBRARY_DIR = USER_LOCAL_DIR.resolve("lib");
	private static final Path USER_DATA_DIR = USER_LOCAL_DIR.resolve("share");
	private static final Path USER_STATE_DIR = USER_LOCAL_DIR.resolve("state");

	/** @since 1.1.0 */
	public LinuxDirs(
		final AppConfig properties, final Configuration config,
		final boolean isSystem
	){
		super(properties, config, isSystem);
	}
	/** @since 1.1.0 */
	public LinuxDirs(final App<?> context, final boolean isSystem){
		super(context, isSystem);
	}

	// GETTERS
	private Path getUserBinaryDir(){
		return getHomeDir().resolve(USER_BINARY_DIR);
	}
	private Path getUserLibraryDir(){
		return getHomeDir().resolve(USER_LIBRARY_DIR);
	}
	private Path getUserConfigDir(){
		return getHomeDir().resolve(USER_CONFIG_DIR);
	}
	private Path getUserDataDir(){
		return getHomeDir().resolve(USER_DATA_DIR);
	}
	private Path getUserStateDir(){
		return getHomeDir().resolve(USER_STATE_DIR);
	}
	private Path getUserCacheDir(){
		return getHomeDir().resolve(USER_CACHE_DIR);
	}
	private Path getRelativeStateDir(){
		return isSystem ? subfolders.resolve(STATE_DIR) : subfolders;
	}

	/** Folder is an assumption.
	 * @since 1.1.0
	 */
	@Override
	public Path getBinaryDir(){
		return isSystem ? SYSTEM_BINARY_DIR : getUserBinaryDir();
	}
	/** Folder is an assumption.
	 * @since 1.1.0
	 */
	@Override
	public Path getLibraryDir(){
		return composeDir(
			isSystem ? SYSTEM_LIBRARY_DIR : getUserLibraryDir(),
			subfolders
		);
	}
	/** @since 1.1.0 */
	@Override
	public Path getConfigDir(){
		return composeDir(
			isSystem ? SYSTEM_CONFIG_DIR : getUserConfigDir(),
			subfolders
		);
	}
	/** @since 1.1.0 */
	@Override
	public Path getDataDir(){
		return composeDir(
			isSystem ? SYSTEM_DATA_DIR : getUserDataDir(),
			subfolders
		);
	}
	/** @since 1.1.0 */
	@Override
	public Path getStateDir(){
		return composeDir(
			isSystem ? SYSTEM_VARIABLE_DIR : getUserStateDir(),
			getRelativeStateDir()
		);
	}
	/** @since 1.1.0 */
	@Override
	public Path getLogDir(){
		return composeDir(
			isSystem ? SYSTEM_VARIABLE_DIR : getUserStateDir(),
			getRelativeStateDir().resolve(LOG_DIR)
		);
	}
	/** @since 1.1.0 */
	@Override
	public Path getPersistentTemporaryDir(){
		return composeDir(
			isSystem ? SYSTEM_VARIABLE_DIR : getUserStateDir(),
			getRelativeStateDir().resolve(PERSISTENT_TEMPORARY_DIR)
		);
	}
	/** @since 1.1.0 */
	@Override
	public Path getCacheDir(){
		return composeDir(
			isSystem ? SYSTEM_VARIABLE_DIR : getUserCacheDir(),
			isSystem ? subfolders.resolve(CACHE_DIR) : subfolders
		);
	}
	/** @since 1.1.0 */
	@Override
	public Path getTemporaryDir(){
		return composeDir(config.getSystem().getTmpDir(), subfolders);
	}
}