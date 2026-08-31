package dev.pimous.pu.jutils.app.dirs;

import dev.pimous.pu.jutils.app.App;
import dev.pimous.pu.jutils.app.AppConfig;
import dev.pimous.pu.jutils.base.NotImplementedException;
import dev.pimous.pu.jutils.config.Configuration;

import java.io.File;
import java.nio.file.Path;

/** For "testApp" under group "pimous.dev", files will be:
 * <ul>
 *     <li>Binary: ./bin/</li>
 *     <li>Library: ./lib/</li>
 *     <li>Config: ./etc/</li>
 *     <li>Data: ./data/</li>
 *     <li>State: ./state/<ul>
 *         <li>Log: ./log/</li>
 *         <li>Persistent Temporary: ./ptmp/</li>
 *     </ul></li>
 *     <li>Cache: ./cache/</li>
 *     <li>Temporary: ./tmp/</li>
 * </ul>
 * @author Xibitol
 * @since 1.0.0
 */
public class LocalDirs extends AbstractDirs{

	/** @since 1.1.0 */
	public LocalDirs(final AppConfig properties, final Configuration config){
		super(properties, config, false);
	}
	/** @since 1.1.0 */
	public LocalDirs(final App<?> context){
		super(context, false);
	}

	// GETTERS
	/** Folder is an assumption.
	 * @since 1.1.0
	 */
	@Override
	public Path getBinaryDir(){
		return config.getSystem().getWorkingDir().resolve(BINARY_DIR);
	}
	/** Folder is an assumption.
	 * @since 1.1.0
	 */
	@Override
	public Path getLibraryDir(){
		return config.getSystem().getWorkingDir().resolve(LIBRARY_DIR);
	}
	/** @since 1.1.0 */
	@Override
	public Path getConfigDir(){
		return composeDir(config.getSystem().getWorkingDir(), CONFIG_DIR);
	}
	/** @since 1.1.0 */
	@Override
	public Path getDataDir(){
		return composeDir(config.getSystem().getWorkingDir(), DATA_DIR);
	}
	/** @since 1.1.0 */
	@Override
	public Path getStateDir(){
		return composeDir(config.getSystem().getWorkingDir(), STATE_DIR);
	}
	/** @since 1.1.0 */
	@Override
	public Path getLogDir(){
		return composeDir(
			config.getSystem().getWorkingDir(),
			STATE_DIR.resolve(LOG_DIR)
		);
	}
	/** @since 1.1.0 */
	@Override
	public Path getPersistentTemporaryDir(){
		return composeDir(
			config.getSystem().getWorkingDir(),
			STATE_DIR.resolve(PERSISTENT_TEMPORARY_DIR)
		);
	}
	/** @since 1.1.0 */
	@Override
	public Path getCacheDir(){
		return composeDir(config.getSystem().getWorkingDir(), CACHE_DIR);
	}
	/** @since 1.1.0 */
	@Override
	public Path getTemporaryDir(){
		return composeDir(config.getSystem().getWorkingDir(), TEMPORARY_DIR);
	}
}