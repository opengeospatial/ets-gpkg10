package org.opengis.cite.gpkg10.core;

import org.opengis.cite.gpkg10.ColumnDefinition;
import org.opengis.cite.gpkg10.CommonFixture;
import org.opengis.cite.gpkg10.ErrorMessage;
import org.opengis.cite.gpkg10.ErrorMessageKeys;
import org.opengis.cite.gpkg10.ForeignKeyDefinition;
import org.opengis.cite.gpkg10.TableDefinition;
import org.opengis.cite.gpkg10.TableVerifier;
import org.opengis.cite.gpkg10.util.DatabaseUtility;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

/**
 * Defines test methods that apply to descriptive information about the contents
 * of a GeoPackage. The {@code gpkg_contents} table describes the
 * geospatial data contained in the file.
 *
 * <p style="margin-bottom: 0.5em">
 * <strong>Sources</strong>
 * </p>
 * <ul>
 * <li><a href="http://www.geopackage.org/spec/#_contents" target= "_blank">
 * GeoPackage Encoding Standard - Contents</a> (OGC 12-128r12)</li>
 * </ul>
 *
 * @author Luke Lambert
 */
public class DataContentsTests extends CommonFixture
{
    /**
     * The columns of tables in a GeoPackage SHALL only be declared using one
     * of the data types specified in table <a href=
     * "http://www.geopackage.org/spec/#table_column_data_types">GeoPackage
     * Data Types</a>.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-5" target=
     *      "_blank">File Contents - Requirement 5</a>
     *
     * @throws SQLException
     *             If an SQL query causes an error
     */
    @Test(description = "See OGC 12-128r12: Requirement 5")
    public void columnDataTypes() throws SQLException
    {
        try(final Statement statement = this.databaseConnection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT table_name FROM gpkg_contents;"))
        {
            while(resultSet.next())
            {
                final String tableName = resultSet.getString("table_name");

                if(DatabaseUtility.doesTableOrViewExist(this.databaseConnection, tableName))
                {
                    try(final Statement preparedStatement = this.databaseConnection.createStatement();
                        final ResultSet pragmaTableInfo   = preparedStatement.executeQuery(String.format("PRAGMA table_info('%s');", tableName)))
                    {
                        while(pragmaTableInfo.next())
                        {
                            final String dataType = pragmaTableInfo.getString("type");
                            final boolean correctDataType = ALLOWED_SQL_TYPES.contains(dataType.toUpperCase()) ||
                                                            TEXT_TYPE.matcher(dataType.toUpperCase()).matches() ||
                                                            BLOB_TYPE.matcher(dataType.toUpperCase()).matches();

                            assertTrue(correctDataType,
                                       ErrorMessage.format(ErrorMessageKeys.INVALID_DATA_TYPE,
                                                           dataType,
                                                           tableName));
                        }
                    }
                }
            }
        }
    }

    /**
     * A GeoPackage file SHALL include a {@code gpkg_contents} table per table
     * <a href="http://www.geopackage.org/spec/#gpkg_contents_cols">Contents
     * Table or View Definition</a> and <a href=
     * "http://www.geopackage.org/spec/#gpkg_contents_sql">gpkg_contents Table
     * Definition SQL</a>.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-13" target=
     *      "_blank">Table Definition - Requirement 13</a>
     *
     * @throws SQLException
     *             If an SQL query causes an error
     */
    @Test(description = "See OGC 12-128r12: Requirement 13")
    public void contentsTableDefinition() throws SQLException
    {
        try
        {
            final Map<String, ColumnDefinition> contentColumns = new HashMap<>();

            contentColumns.put("table_name",  new ColumnDefinition("TEXT",     true,  true,  true,  null));
            contentColumns.put("data_type",   new ColumnDefinition("TEXT",     true,  false, false, null));
            contentColumns.put("identifier",  new ColumnDefinition("TEXT",     false, false, true,  null));
            contentColumns.put("description", new ColumnDefinition("TEXT",     false, false, false, "''"));
            contentColumns.put("last_change", new ColumnDefinition("DATETIME", true,  false, false, "strftime('%Y-%m-%dT%H:%M:%fZ', 'now')"));
            contentColumns.put("min_x",       new ColumnDefinition("DOUBLE",   false, false, false, null));
            contentColumns.put("min_y",       new ColumnDefinition("DOUBLE",   false, false, false, null));
            contentColumns.put("max_x",       new ColumnDefinition("DOUBLE",   false, false, false, null));
            contentColumns.put("max_y",       new ColumnDefinition("DOUBLE",   false, false, false, null));
            contentColumns.put("srs_id",      new ColumnDefinition("INTEGER",  false, false, false, null));

            TableVerifier.verifyTable(this.databaseConnection,
                                      "gpkg_contents",
                                      contentColumns,
                                      new HashSet<>(Arrays.asList(new ForeignKeyDefinition("gpkg_spatial_ref_sys", "srs_id", "srs_id"))),
                                      Collections.emptyList());
        }
        catch(final AssertionError ex)
        {
            fail(ErrorMessage.format(ErrorMessageKeys.BAD_CONTENTS_TABLE_DEFINITION, ex.getMessage()));
        }
    }

    /**
     * The {@code table_name} column value in a {@code gpkg_contents} table row
     * SHALL contain the name of a SQLite table or view.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-14" target=
     *      "_blank">Table Data Values - Requirement 14</a>
     *
     * @throws SQLException
     *             If an SQL query causes an error
     */
    @Test(description = "See OGC 12-128r12: Requirement 14")
    public void contentsTablesExist() throws SQLException
    {
        final String query = "SELECT DISTINCT table_name " +
                             "FROM  gpkg_contents " +
                             "WHERE table_name NOT IN (SELECT name FROM sqlite_master);";

            try(final Statement statement   = this.databaseConnection.createStatement();
                final ResultSet resultSet = statement.executeQuery(query))
            {
                final Collection<String> invalidContentsTableNames = new LinkedList<>();

                while(resultSet.next())
                {
                    invalidContentsTableNames.add(resultSet.getString(1));
                }

                assertTrue(invalidContentsTableNames.isEmpty(),
                           ErrorMessage.format(ErrorMessageKeys.CONTENT_TABLE_DOES_NOT_EXIST,
                                               String.join(", ", invalidContentsTableNames)));
            }
    }

    /**
     * Values of the {@code gpkg_contents} table {@code last_change}
     * column SHALL be in <a
     * href="http://www.iso.org/iso/catalogue_detail?csnumber=40874">ISO 8601
     * </a> format containing a complete date plus UTC hours, minutes, seconds
     * and a decimal fraction of a second, with a 'Z' ('zulu') suffix
     * indicating UTC.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-15" target=
     *      "_blank">Table Data Values - Requirement 15</a>
     *
     * @throws SQLException
     *             If an SQL query causes an error
     */
    @Test(description = "See OGC 12-128r12: Requirement 15")
    public void timestampFormat() throws SQLException
    {
        try(final Statement statement = this.databaseConnection.createStatement();
            final ResultSet resultSet = statement.executeQuery("SELECT last_change, table_name FROM gpkg_contents;"))
        {
            while(resultSet.next())
            {
                final String lastChange = resultSet.getString("last_change");

                try
                {
                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.'SS'Z'").parse(lastChange);
                }
                catch(final ParseException ignore)
                {
                   fail(ErrorMessage.format(ErrorMessageKeys.BAD_CONTENTS_ENTRY_LAST_CHANGE_FORMAT,
                                            resultSet.getString("table_name")));
                }
            }
        }
    }

    /**
     * Values of the {@code gpkg_contents} table {@code srs_id} column SHALL
     * reference values in the {@code gpkg_spatial_ref_sys} table {@code
     * srs_id} column.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-16" target=
     *      "_blank">Table Data Values - Requirement 16</a>
     *
     * @throws SQLException
     *             If an SQL query causes an error
     */
    @Test(description = "See OGC 12-128r12: Requirement 16")
    public void srsIdReferencesSrsTable() throws SQLException
    {
        try(final Statement statement  = this.databaseConnection.createStatement();
            final ResultSet resultSet = statement.executeQuery("PRAGMA foreign_key_check('gpkg_contents');"))
        {
            assertTrue(!resultSet.next(),
                       ErrorMessage.format(ErrorMessageKeys.BAD_CONTENTS_TABLE_SRS_FOREIGN_KEY));
        }
    }


    private static final Pattern TEXT_TYPE = Pattern.compile("TEXT\\([0-9]+\\)");
    private static final Pattern BLOB_TYPE = Pattern.compile("BLOB\\([0-9]+\\)");

    private static final List<String> ALLOWED_SQL_TYPES = Arrays.asList("BOOLEAN", "TINYINT", "SMALLINT", "MEDIUMINT",
                                                                        "INT", "FLOAT", "DOUBLE", "REAL",
                                                                        "TEXT", "BLOB", "DATE", "DATETIME",
                                                                        "GEOMETRY", "POINT", "LINESTRING", "POLYGON",
                                                                        "MULTIPOINT", "MULTILINESTRING", "MULTIPOLYGON", "GEOMETRYCOLLECTION",
                                                                        "INTEGER");
}
