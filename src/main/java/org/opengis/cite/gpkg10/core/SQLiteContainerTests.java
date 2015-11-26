package org.opengis.cite.gpkg10.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;

import org.opengis.cite.gpkg10.ErrorMessage;
import org.opengis.cite.gpkg10.ErrorMessageKeys;
import org.opengis.cite.gpkg10.GPKG10;
import org.opengis.cite.gpkg10.SuiteAttribute;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
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
public class SQLiteContainerTests {

    private File gpkgFile;
    private Connection dbConnection;

    @BeforeClass
    public void initFixture(ITestContext testContext) {
        Object testFile = testContext.getSuite().getAttribute(SuiteAttribute.TEST_SUBJ_FILE.getName());
        if (null == testFile || !File.class.isInstance(testFile)) {
            throw new IllegalArgumentException(
                    String.format("Suite attribute value is not a File: %s", SuiteAttribute.TEST_SUBJ_FILE.getName()));
        }
        this.gpkgFile = File.class.cast(testFile);
        // TODO create SQLite db connection
    }

    /**
     * A GeoPackage shall be a SQLite database file using version 3 of the
     * SQLite file format. The first 16 bytes of a GeoPackage must contain the
     * (ASCII) string “SQLite format 3”, including the terminating NULL
     * character.
     * 
     * @throws IOException
     *             If an I/O error occurs while trying to read the data file.
     * 
     * @see <a href="http://www.geopackage.org/spec/#_requirement-1" target=
     *      "_blank">File Format: Requirement 1</a>
     */
    @Test(description = "See OGC 12-128r12: Requirement 1")
    public void validHeaderString() throws IOException {
        final byte[] headerString = new byte[GPKG10.SQLITE_MAGIC_HEADER.length];
        try (FileInputStream fileInputStream = new FileInputStream(this.gpkgFile)) {
            fileInputStream.read(headerString);
        }
        Assert.assertEquals(headerString, GPKG10.SQLITE_MAGIC_HEADER, ErrorMessage
                .format(ErrorMessageKeys.INVALID_HEADER_STR, new String(headerString, StandardCharsets.US_ASCII)));
    }
}
