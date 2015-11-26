package org.opengis.cite.gpkg10.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.opengis.cite.gpkg10.GPKG10;
import org.opengis.cite.gpkg10.SuiteAttribute;
import org.testng.ISuite;
import org.testng.ITestContext;

public class VerifySQLiteContainerTests {

    private static ITestContext testContext;
    private static ISuite suite;
    private File dataFile;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void initTestFixture() {
        testContext = mock(ITestContext.class);
        suite = mock(ISuite.class);
        when(testContext.getSuite()).thenReturn(suite);
    }

    @Before
    public void createDataFile() throws IOException {
        this.dataFile = File.createTempFile("testData", ".db");
        when(suite.getAttribute(SuiteAttribute.TEST_SUBJ_FILE.getName())).thenReturn(this.dataFile);
    }

    @After
    public void deleteDataFile() {
        this.dataFile.delete();
    }

    @Test
    public void validHeaderString() throws IOException {
        try (FileOutputStream fileOut = new FileOutputStream(this.dataFile)) {
            fileOut.write(GPKG10.SQLITE_MAGIC_HEADER);
        }
        SQLiteContainerTests iut = new SQLiteContainerTests();
        iut.initFixture(testContext);
        iut.validHeaderString();
    }

    @Test
    public void invalidHeaderStringValue() throws IOException {
        thrown.expect(AssertionError.class);
        thrown.expectMessage("Data file has unexpected header string");
        try (FileOutputStream fileOut = new FileOutputStream(this.dataFile)) {
            fileOut.write("kVrjdEww2APTzhc\0".getBytes(StandardCharsets.US_ASCII));
        }
        SQLiteContainerTests iut = new SQLiteContainerTests();
        iut.initFixture(testContext);
        iut.validHeaderString();
    }
}
