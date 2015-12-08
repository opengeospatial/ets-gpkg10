package org.opengis.cite.gpkg10.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.opengis.cite.gpkg10.CommonFixture;
import org.opengis.cite.gpkg10.ErrorMessage;
import org.opengis.cite.gpkg10.ErrorMessageKeys;
import org.opengis.cite.gpkg10.GPKG10;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Defines test methods that apply to an SQLite database file. The GeoPackage
 * standard defines a SQL database schema designed for use with the SQLite
 * software library.
 *
 * <p style="margin-bottom: 0.5em">
 * <strong>Sources</strong>
 * </p>
 * <ul>
 * <li><a href="http://www.geopackage.org/spec/#_sqlite_container" target=
 * "_blank">GeoPackage Encoding Standard - SQLite Container</a> (OGC 12-128r12)
 * </li>
 * <li><a href="http://www.sqlite.org/fileformat2.html" target= "_blank">SQLite
 * Database File Format</a></li>
 * </ul>
 */
public class SQLiteContainerTests extends CommonFixture {

    /**
     * A GeoPackage shall be a SQLite database file using version 3 of the
     * SQLite file format. The first 16 bytes of a GeoPackage must contain the
     * (UTF-8/ASCII) string "SQLite format 3", including the terminating NULL
     * character.
     *
     * @throws IOException
     *             If an I/O error occurs while trying to read the data file.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-1" target=
     *      "_blank">File Format - Requirement 1</a>
     */
    @Test(description = "See OGC 12-128r12: Requirement 1")
    public void fileHeaderString() throws IOException {
        final byte[] headerString = new byte[GPKG10.SQLITE_MAGIC_HEADER.length];
        try (FileInputStream fileInputStream = new FileInputStream(this.gpkgFile)) {
            fileInputStream.read(headerString);
        }
        Assert.assertEquals(headerString, GPKG10.SQLITE_MAGIC_HEADER, ErrorMessage
                .format(ErrorMessageKeys.INVALID_HEADER_STR, new String(headerString, StandardCharsets.US_ASCII)));
    }

    /**
     * A GeoPackage shall contain 0x47503130 ("GP10" in UTF-8/ASCII) in the
     * "Application ID" field of the database header. The field is located at
     * offset 64 (a 32-bit unsigned big-endian integer).
     *
     * @throws IOException
     *             If an I/O error occurs while trying to read the data file.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-2" target=
     *      "_blank">File Format - Requirement 2</a>
     * @see <a href=
     *      "http://www.sqlite.org/src/artifact?ci=trunk&filename=magic.txt"
     *      target= "_blank">Assigned application IDs</a>
     */
    @Test(description = "See OGC 12-128r12: Requirement 2")
    public void applicationID() throws IOException {
        final byte[] headerBytes = new byte[GPKG10.DB_HEADER_LENGTH];
        try (FileInputStream fileInputStream = new FileInputStream(this.gpkgFile)) {
            fileInputStream.read(headerBytes);
        }
        byte[] appID = Arrays.copyOfRange(headerBytes, GPKG10.APP_ID_OFFSET, GPKG10.APP_ID_OFFSET + 4);
        Assert.assertEquals(appID, GPKG10.APP_GP10,
                ErrorMessage.format(ErrorMessageKeys.UNKNOWN_APP_ID, new String(appID, StandardCharsets.US_ASCII)));
    }

    /**
     * A GeoPackage shall have the file extension name ".gpkg".
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-3" target=
     *      "_blank">File Extension Name - Requirement 3</a>
     */
    @Test(description = "See OGC 12-128r12: Requirement 3")
    public void filenameExtension() {
        String fileName = this.gpkgFile.getName();
        String suffix = fileName.substring(fileName.indexOf('.'));
        Assert.assertEquals(suffix, GPKG10.GPKG_FILENAME_SUFFIX,
                ErrorMessage.format(ErrorMessageKeys.INVALID_SUFFIX, suffix));
    }

    /**
     * A GeoPackage shall only contain data elements, SQL constructs and
     * GeoPackage extensions with the “gpkg” author name specified in this
     * encoding standard.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-4" target=
     *      "_blank">File Contents - Requirement 4</a>
     */
    @Test(description = "See OGC 12-128r12: Requirement 4")
    public void fileContents() {
        // TODO: Look for tables, columns, data types, etc. NOT allowed by spec
        // Ignore tables and columns called out in extensions?
    }
}
