package org.opengis.cite.gpkg10;

import java.nio.charset.StandardCharsets;

/**
 * Provides various constants pertaining to GeoPackage 1.0 data containers.
 */
public class GPKG10 {

	/** Length of SQLite database file header (bytes). */
	public static final int DB_HEADER_LENGTH = 100;

	/** Starting offset of "Application ID" field in file header (4 bytes). */
	public static final int APP_ID_OFFSET = 68;

	/** SQLite v3 header string (terminated with a NULL character). */
	public static final byte[] SQLITE_MAGIC_HEADER = new String("SQLite format 3\0")
		.getBytes(StandardCharsets.US_ASCII);

	/** Application id for OGC GeoPackage 1.0. */
	public static final byte[] APP_GP10 = new String("GP10").getBytes(StandardCharsets.US_ASCII);

	/** GeoPackage file name extension. */
	public static final String GPKG_FILENAME_SUFFIX = ".gpkg";

	// String that must be returned upon a pragma integrity check
	/** Constant <code>PRAGMA_INTEGRITY_CHECK="ok"</code> */
	public static final String PRAGMA_INTEGRITY_CHECK = "ok";

}
