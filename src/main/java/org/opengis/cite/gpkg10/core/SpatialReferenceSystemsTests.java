package org.opengis.cite.gpkg10.core;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

import org.opengis.cite.gpkg10.ColumnDefinition;
import org.opengis.cite.gpkg10.CommonFixture;
import org.opengis.cite.gpkg10.ErrorMessage;
import org.opengis.cite.gpkg10.ErrorMessageKeys;
import org.opengis.cite.gpkg10.TableVerifier;
import org.testng.annotations.Test;

/**
 * Defines test methods that apply to spatial reference systems defined in a
 * GeoPackage. The coordinate reference systems relate feature geometries and
 * tile images in user tables to locations on the Earth.
 *
 * <p style="margin-bottom: 0.5em">
 * <strong>Sources</strong>
 * </p>
 * <ul>
 * <li><a href="http://www.geopackage.org/spec/#spatial_ref_sys" target=
 * "_blank">GeoPackage Encoding Standard - Spatial Reference Systems</a> (OGC
 * 12-128r12)</li>
 * <li><a href="http://www.epsg.org/" target= "_blank">EPSG Geodetic Parameter
 * Dataset</a></li>
 * </ul>
 *
 * @author Luke Lambert
 */
public class SpatialReferenceSystemsTests extends CommonFixture {
    /**
     * A GeoPackage SHALL include a {@code gpkg_spatial_ref_sys} table per
     * clause 1.1.2.1.1 <a href=
     * "http://www.geopackage.org/spec/#spatial_ref_sys_data_table_definition">
     * Table Definition</a>, Table
     * <a href= "http://www.geopackage.org/spec/#gpkg_spatial_ref_sys_cols">
     * Spatial Ref Sys Table Definition</a> and Table
     * <a href= "http://www.geopackage.org/spec/#gpkg_spatial_ref_sys_sql">
     * gpkg_spatial_ref_sys Table Definition SQL</a>.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-10" target=
     *      "_blank">Table Definition - Requirement 10</a>
     *
     * @throws SQLException
     *             If an SQL query causes an error
     */
    @Test(description = "See OGC 12-128r12: Requirement 10")
    public void srsTableDefinition() throws SQLException
    {
        try
        {
            final Map<String, ColumnDefinition> spatialReferenceSystemColumns = new HashMap<>();

            spatialReferenceSystemColumns.put("srs_name",                 new ColumnDefinition("TEXT",    true,  false, false, null));
            spatialReferenceSystemColumns.put("srs_id",                   new ColumnDefinition("INTEGER", true,  true,  true,  null));
            spatialReferenceSystemColumns.put("organization",             new ColumnDefinition("TEXT",    true,  false, false, null));
            spatialReferenceSystemColumns.put("organization_coordsys_id", new ColumnDefinition("INTEGER", true,  false, false, null));
            spatialReferenceSystemColumns.put("definition",               new ColumnDefinition("TEXT",    true,  false, false, null));
            spatialReferenceSystemColumns.put("description",              new ColumnDefinition("TEXT",    false, false, false, null));

            TableVerifier.verifyTable(this.databaseConnection,
                                      "gpkg_spatial_ref_sys",
                                      spatialReferenceSystemColumns,
                                      Collections.emptySet(),
                                      Collections.emptyList());
        }
        catch(final Throwable th)
        {
            fail(ErrorMessage.format(ErrorMessageKeys.BAD_SRS_TABLE_DEFINITION, th.getMessage()));
        }
    }

    /**
     * The {@code gpkg_spatial_ref_sys} table in a GeoPackage SHALL contain a
     * record for organization
     * <a href="http://www.epsg.org/Geodetic.html">EPSG or epsg</a> and
     * {@code organization_coordsys_id} <a href=
     * "http://www.epsg-registry.org/report.htm?type=selection&amp;entity=urn:ogc:def:crs:EPSG::4326&amp;reportDetail=long&amp;title=WGS%2084&amp;style=urn:uuid:report-style:default-with-code&amp;style_name=OGP%20Default%20With%20Code"
     * >4326</a> for
     * <a href="http://www.google.com/search?as_q=WGS-84">WGS-84</a>, a record
     * with an {@code srs_id} of -1, an organization of "NONE", an
     * {@code organization_coordsys_id} of -1, and definition "undefined" for
     * undefined Cartesian coordinate reference systems, and a record with an
     * {@code srs_id} of 0, an organization of "NONE", an {@code
     * organization_coordsys_id} of 0, and definition "undefined" for undefined
     * geographic coordinate reference systems.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-11" target=
     *      "_blank">Table Data Values - Requirement 11</a>
     *
     * @throws SQLException
     *             If an SQL query causes an error
     */
    @Test(description = "See OGC 12-128r12: Requirement 11")
    public void requiredSrsEntries() throws SQLException {
        try (final Statement statement = this.databaseConnection.createStatement();
                final ResultSet srsDefaultValue = statement.executeQuery(
                        "SELECT srs_id FROM gpkg_spatial_ref_sys WHERE organization_coordsys_id = 4326 AND (organization = 'EPSG' OR organization = 'epsg');")) {
            assertTrue(srsDefaultValue.next(), ErrorMessage.format(ErrorMessageKeys.NO_GEOGRAPHIC_SRS));
        }

        try (final Statement statement = this.databaseConnection.createStatement();
                final ResultSet srsDefaultValue = statement.executeQuery(
                        "SELECT srs_id FROM gpkg_spatial_ref_sys WHERE srs_id = -1 AND organization = 'NONE' AND organization_coordsys_id = -1 AND definition = 'undefined';")) {
            assertTrue(srsDefaultValue.next(), ErrorMessage.format(ErrorMessageKeys.NO_UNDEFINED_CARTESIAN_SRS));
        }

        try (final Statement statement = this.databaseConnection.createStatement();
                final ResultSet srsDefaultValue = statement.executeQuery(
                        "SELECT srs_id FROM gpkg_spatial_ref_sys WHERE srs_id = 0 AND organization = 'NONE' AND organization_coordsys_id =  0 AND definition = 'undefined';")) {
            assertTrue(srsDefaultValue.next(), ErrorMessage.format(ErrorMessageKeys.NO_UNDEFINED_GEOGRAPHIC_SRS));
        }
    }

    /**
     * The {@code gpkg_spatial_ref_sys} table in a GeoPackage SHALL contain
     * records to define all spatial reference systems used by features and
     * tiles in a GeoPackage.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-12" target=
     *      "_blank">Table Data Values - Requirement 12</a>
     *
     * @throws SQLException
     *             If an SQL query causes an error
     */
    @Test(description = "See OGC 12-128r12: Requirement 12")
    public void checkContentSrs() throws SQLException {
        final String query = "SELECT DISTINCT srs_id as srsContents " + "FROM gpkg_contents " + "WHERE srsContents "
                + "NOT IN (SELECT srs_id FROM gpkg_spatial_ref_sys);";

        try (final Statement statement = this.databaseConnection.createStatement();
                final ResultSet srsDefined = statement.executeQuery(query)) {
            final Collection<String> invalidSrsIds = new LinkedList<>();

            while (srsDefined.next()) {
                invalidSrsIds.add(srsDefined.getString("srsContents"));
            }

            assertTrue(invalidSrsIds.isEmpty(), ErrorMessage.format(ErrorMessageKeys.UNDEFINED_SRS,
                    invalidSrsIds.stream().map(Object::toString).collect(Collectors.joining(", "))));
        }
    }
}
