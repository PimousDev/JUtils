package dev.pimous.pu.jutils.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Stricter https://semver.org/.
 * @author APG-Gillardeau
 * @since 1.0.0
 */
public final class Version{

	public static final Pattern PATTERN = Pattern.compile(
		"^(?<major>[1-9]\\d*|0)\\.(?<minor>[1-9]\\d*|0)\\.(?<patch>[1-9]\\d*|0)(?:-(?<preRelease>.*))?$"
	);
	private static final String VERSION_FORMAT = "%d.%d.%d";
	private static final String PRERELEASE_DELIMITER = "-";

	public final byte major;
	public final byte minor;
	public final byte patch;
	private PreRelease preRelease = null;

	public Version(int major, int minor, int patch){
		assertUnsignedByte(major);
		assertUnsignedByte(minor);
		assertUnsignedByte(patch);

		this.major = (byte) major;
		this.minor = (byte) minor;
		this.patch = (byte) patch;
	}
	public Version(int major, int minor, int patch, PreRelease preRelease){
		this(major, minor, patch);

		this.preRelease = preRelease;
	}
	public Version(int major, int minor, int patch,
		char preReleaseIdentifier, int preReleaseNumber
	){
		this(major, minor, patch,
			new PreRelease(preReleaseIdentifier, preReleaseNumber)
		);
	}
	public Version(String version){
		Matcher m = PATTERN.matcher(version);
		if(!m.matches())
			throw new IllegalArgumentException(
				"Unrecognized version (Got %s);".formatted(version)
			);

		PreRelease preRelease = null;
		if(m.group("preRelease") != null)
			preRelease = new PreRelease(m.group("preRelease"));

		this(
			Integer.parseUnsignedInt(m.group("major")),
			Integer.parseUnsignedInt(m.group("minor")),
			Integer.parseUnsignedInt(m.group("patch")),
			preRelease
		);
	}

	// GETTERS
	public boolean hasPreRelease(){ return preRelease != null; }
	public PreRelease getPreRelease(){ return preRelease; }

	// FUNCTIONS
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder(
			VERSION_FORMAT.formatted(major, minor, patch)
		);

		if(hasPreRelease()){
			sb.append(PRERELEASE_DELIMITER);
			sb.append(getPreRelease());
		}

		return sb.toString();
	}

	// ASSERTIONS
	private static void assertUnsignedByte(int number){
		if(number < 0 || number >= Math.powExact(2, 8))
			throw new IllegalArgumentException(
				"Version numbers should fit in bytes (Got %d);"
					.formatted(number)
			);
	}

	// INNER CLASSES
	/**
	 * @see Version
	 * @author APG-Gillardeau
	 * @since 1.0.0
	 */
	public static final class PreRelease{

		public static final Pattern PATTERN = Pattern.compile(
			"^(?<identifier>[a-z])\\.(?<number>[1-9]\\d*|0)$"
		);
		private static final String PRERELEASE_FORMAT = "%s.%d";

		public final char identifier;
		public final byte number;

		public PreRelease(char identifier, int number){
			assertIdentifier(identifier);
			assertUnsignedByte(number);

			this.identifier = identifier;
			this.number = (byte) number;
		}
		private PreRelease(String preRelease){
			Matcher m = PATTERN.matcher(preRelease);
			if(!m.matches())
				throw new IllegalArgumentException(
					"Unrecognized pre-release (Got %s);".formatted(preRelease)
				);

			this(
				(char) m.group("identifier").charAt(0),
				(byte) Integer.parseUnsignedInt(m.group("number"))
			);
		}

		// FUNCTIONS
		@Override
		public String toString(){
			return PRERELEASE_FORMAT.formatted(identifier, number);
		}

		// ASSERTIONS
		private static void assertIdentifier(char identifier){
			if(identifier < 'a' || identifier > 'z')
				throw new IllegalArgumentException(
					"Pre-release identifier must be an ASCII lowercase letter (Got '%s');"
						.formatted(identifier)
				);
		}
	}
}
