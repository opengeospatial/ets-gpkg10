package org.opengis.cite.gpkg10;

import java.nio.charset.StandardCharsets;

/**
 * Provides various constants pertaining to GeoPackage 1.0 data containers.
 */
public class GPKG10 {

    /** SQLite v3 header string (terminated with a NULL character). */
    public static final byte[] SQLITE_MAGIC_HEADER = new String("SQLite format 3\0")
            .getBytes(StandardCharsets.US_ASCII);
}
