package org.opengis.cite.gpkg10.core;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.opengis.cite.gpkg10.SuiteAttribute;
import org.testng.ISuite;
import org.testng.ITestContext;

public class VerifySQLiteContainerTests {

    private static ITestContext testContext;
    private static ISuite suite;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void initTestFixture() {
        testContext = mock(ITestContext.class);
        suite = mock(ISuite.class);
        when(testContext.getSuite()).thenReturn(suite);
    }

    @Test
    public void validHeaderString() throws IOException, SQLException, URISyntaxException {
        URL gpkgUrl = getClass().getResource("/gpkg/simple_sewer_features.gpkg");
        File dataFile = new File(gpkgUrl.toURI());
        when(suite.getAttribute(SuiteAttribute.TEST_SUBJ_FILE.getName())).thenReturn(dataFile);
        SQLiteContainerTests iut = new SQLiteContainerTests();
        iut.initFixture(testContext);
        iut.fileHeaderString();
    }

    @Test
    public void validApplicationId() throws IOException, SQLException, URISyntaxException {
        URL gpkgUrl = getClass().getResource("/gpkg/simple_sewer_features.gpkg");
        File dataFile = new File(gpkgUrl.toURI());
        when(suite.getAttribute(SuiteAttribute.TEST_SUBJ_FILE.getName())).thenReturn(dataFile);
        SQLiteContainerTests iut = new SQLiteContainerTests();
        iut.initFixture(testContext);
        iut.applicationID();
    }
}
