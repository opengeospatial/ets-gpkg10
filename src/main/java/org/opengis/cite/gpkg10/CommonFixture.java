package org.opengis.cite.gpkg10;

import java.io.File;

import javax.sql.DataSource;

import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteConfig.JournalMode;
import org.sqlite.SQLiteConfig.SynchronousMode;
import org.sqlite.SQLiteDataSource;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;

/**
 * A supporting base class that sets up a common test fixture. These
 * configuration methods are invoked before any that may be defined in a
 * subclass.
 */
public class CommonFixture {

    /** Root test suite package (absolute path). */
    public static final String ROOT_PKG_PATH = "/org/opengis/cite/gpkg10/";
    /** A SQLite database file containing a GeoPackage. */
    protected File gpkgFile;
    /** A JDBC DataSource for accessing the SQLite database. */
    protected DataSource dataSource;

    /**
     * Initializes the common test fixture. The fixture includes the following
     * components:
     * <ul>
     * <li>a File representing a GeoPackage;</li>
     * <li>a DataSource for accessing a SQLite database.</li>
     * </ul>
     * 
     * @param testContext
     *            The test context that contains all the information for a test
     *            run, including suite attributes.
     */
    @BeforeClass
    public void initCommonFixture(ITestContext testContext) {
        Object testFile = testContext.getSuite().getAttribute(SuiteAttribute.TEST_SUBJ_FILE.getName());
        if (null == testFile || !File.class.isInstance(testFile)) {
            throw new IllegalArgumentException(
                    String.format("Suite attribute value is not a File: %s", SuiteAttribute.TEST_SUBJ_FILE.getName()));
        }
        this.gpkgFile = File.class.cast(testFile);
        SQLiteConfig dbConfig = new SQLiteConfig();
        dbConfig.setSynchronous(SynchronousMode.OFF);
        dbConfig.setJournalMode(JournalMode.MEMORY);
        dbConfig.enforceForeignKeys(true);
        SQLiteDataSource sqliteSource = new SQLiteDataSource(dbConfig);
        sqliteSource.setUrl("jdbc:sqlite:" + this.gpkgFile.getPath());
        this.dataSource = sqliteSource;
    }

}
