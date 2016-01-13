package org.opengis.cite.gpkg10.metadata;

import org.opengis.cite.gpkg10.ColumnDefinition;
import org.opengis.cite.gpkg10.CommonFixture;
import org.opengis.cite.gpkg10.ErrorMessage;
import org.opengis.cite.gpkg10.ErrorMessageKeys;
import org.opengis.cite.gpkg10.ForeignKeyDefinition;
import org.opengis.cite.gpkg10.TableVerifier;
import org.opengis.cite.gpkg10.util.DatabaseUtility;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.fail;

/**
 * Defines test methods that apply to descriptive information about a
 * GeoPackage's content as it pertains to the metadata extension.
 *
 * <p style="margin-bottom: 0.5em">
 * <strong>Sources</strong>
 * </p>
 * <ul>
 * <li><a href="http://www.geopackage.org/spec/#extension_metadata" target= "_blank">
 * GeoPackage Encoding Standard - F.8. Metadata</a> (OGC 12-128r12)</li>
 * </ul>
 *
 * @author Luke Lambert
 */
public class MetadataTests extends CommonFixture
{
    /**
     * Sets up variables used across methods
     *
     * @throws SQLException if there is a database error
     */
    @BeforeClass
    public void setUp() throws SQLException
    {
        this.hasMetadataTable          = DatabaseUtility.doesTableOrViewExist(this.databaseConnection, "gpkg_metadata");
        this.hasMetadataReferenceTable = DatabaseUtility.doesTableOrViewExist(this.databaseConnection, "gpkg_metadata_reference");

        this.metadataValues = new LinkedList<>();

        try(final Statement statement = this.databaseConnection.createStatement())
        {
            try(final ResultSet resultSet = statement.executeQuery("SELECT md_scope, id FROM gpkg_metadata;"))
            {
                while(resultSet.next())
                {
                    this.metadataValues.add(new MetadataTests.Metadata(resultSet.getInt("id"),
                                                                       resultSet.getString("md_scope")));
                }
            }
        }

        this.metadataReferenceValues = new LinkedList<>();

        try(final Statement statement = this.databaseConnection.createStatement())
        {
            try(final ResultSet resultSet = statement.executeQuery("SELECT reference_scope, table_name, column_name, row_id_value, timestamp, md_file_id, md_parent_id FROM gpkg_metadata_reference;"))
            {
                while(resultSet.next())
                {
                    this.metadataReferenceValues.add(new MetadataTests.MetadataReference(resultSet.getString("reference_scope"),
                                                                                         resultSet.getString("table_name"),
                                                                                         resultSet.getString("column_name"),
                                                                                         resultSet.getString("timestamp"),
                                                                                         resultSet.getInt   ("md_file_id"),      // Cannot be null
                                                                                         nullSafeGet(resultSet, "row_id_value"), // getInt() returns 0 if the value in the database was null
                                                                                         nullSafeGet(resultSet, "md_parent_id")));
                }
            }
        }
    }

    /**
     * A GeoPackage MAY contain a table named gpkg_metadata. If present it
     * SHALL be defined per clause 2.4.2.1.1 <a href=
     * "http://www.geopackage.org/spec/#metadata_table_table_definition">Table
     * Definition</a>, <a href=
     * "http://www.geopackage.org/spec/#gpkg_metadata_cols">Metadata Table
     * Definition</a> and <a href=
     * "http://www.geopackage.org/spec/#gpkg_metadata_sql">gpkg_metadata Table
     * Definition SQL</a>.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-93" target=
     *      "_blank">F.8. Metadata - Requirement 93</a>
     *
     * @throws SQLException
     *             If an SQL query causes an error
     */
    @Test(description = "See OGC 12-128r12: Requirement 93")
    public void metadataTableDefinition() throws SQLException
    {
        if(this.hasMetadataTable)
        {
            final Map<String, ColumnDefinition> metadataTableColumns = new HashMap<>();

            metadataTableColumns.put("id",              new ColumnDefinition("INTEGER", true, true,  false, null));
            metadataTableColumns.put("md_scope",        new ColumnDefinition("TEXT",    true, false, false, "'dataset'"));
            metadataTableColumns.put("md_standard_uri", new ColumnDefinition("TEXT",    true, false, false, null));
            metadataTableColumns.put("mime_type",       new ColumnDefinition("TEXT",    true, false, false, "'text/xml'"));
            metadataTableColumns.put("metadata",        new ColumnDefinition("TEXT",    true, false, false, "''"));

            try
            {
                TableVerifier.verifyTable(this.databaseConnection,
                                          "gpkg_metadata",
                                          metadataTableColumns,
                                          Collections.emptySet(),
                                          Collections.emptyList());
            }
            catch(final Throwable th)
            {
                fail(ErrorMessage.format(ErrorMessageKeys.BAD_METADATA_TABLE_DEFINITION, th.getMessage()));
            }
        }
    }

    /**
     * Each {@code md_scope} column value in a {@code gpkg_metadata} table or
     * updateable view SHALL be one of the name column values from <a href=
     * "http://www.geopackage.org/spec/#metadata_scopes">Metadata Scopes</a>.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-94" target=
     *      "_blank">F.8. Metadata - Requirement 94</a>
     *
     * @throws SQLException
     *             If an SQL query causes an error
     */
    @Test(description = "See OGC 12-128r12: Requirement 94")
    public void metadataScopeValues() throws SQLException
    {
        if(this.hasMetadataTable)
        {
            final List<String> invalidScopeValues = this.metadataValues.stream()
                                                        .filter(MetadataTests.Metadata::hasValidMdScope)
                                                        .map(MetadataTests.Metadata::getMdScope)
                                                        .collect(Collectors.toList());

            assertTrue(invalidScopeValues.isEmpty(),
                       ErrorMessage.format(ErrorMessageKeys.INVALID_METADATA_SCOPE,
                                           String.join(", ", invalidScopeValues)));
        }
    }

    /**
     * A GeoPackage that contains a {@code gpkg_metadata} table SHALL contain a
     * {@code gpkg_metadata_reference} table per clause 2.4.3.1.1 <a href=
     * "http://www.geopackage.org/spec/#metadata_reference_table_table_definition"
     * >Table Definition</a>, <a href=
     * "http://www.geopackage.org/spec/#gpkg_metadata_reference_cols">Metadata
     * Reference Table Definition (Table Name: gpkg_metadata_reference)</a> and
     * <a href="http://www.geopackage.org/spec/#gpkg_metadata_reference_sql">
     * gpkg_metadata_reference Table Definition SQL</a>.
     *
     * @see <a href="http://www.geopackage.org/spec/#_requirement-95" target=
     *      "_blank">F.8. Metadata - Requirement 95</a>
     *
     * @throws SQLException
     *             If an SQL query causes an error
     */
    @Test(description = "See OGC 12-128r12: Requirement 95")
    public void metadataReferencesTableDefinition() throws SQLException
    {
        if(this.hasMetadataTable)
        {
            assertTrue(this.hasMetadataReferenceTable,
                       ErrorMessageKeys.MISSING_METADATA_REFERENCE_TABLE);

            final Map<String, ColumnDefinition> metadataReferenceTableColumns = new HashMap<>();

            metadataReferenceTableColumns.put("reference_scope", new ColumnDefinition("TEXT",     true,  false, false, null));
            metadataReferenceTableColumns.put("table_name",      new ColumnDefinition("TEXT",     false, false, false, null));
            metadataReferenceTableColumns.put("column_name",     new ColumnDefinition("TEXT",     false, false, false, null));
            metadataReferenceTableColumns.put("row_id_value",    new ColumnDefinition("INTEGER",  false, false, false, null));
            metadataReferenceTableColumns.put("timestamp",       new ColumnDefinition("DATETIME", true,  false, false, "strftime('%Y-%m-%dT%H:%M:%fZ', 'now')"));
            metadataReferenceTableColumns.put("md_file_id",      new ColumnDefinition("INTEGER",  true,  false, false, null));
            metadataReferenceTableColumns.put("md_parent_id",    new ColumnDefinition("INTEGER",  false, false, false, null));

            try
            {
                TableVerifier.verifyTable(this.databaseConnection,
                                          "gpkg_metadata_reference",
                                          metadataReferenceTableColumns,
                                          new HashSet<>(Arrays.asList(new ForeignKeyDefinition("gpkg_metadata", "md_parent_id", "id"),
                                                                      new ForeignKeyDefinition("gpkg_metadata", "md_file_id",   "id"))),
                                          Collections.emptyList());
            }
            catch(final Throwable th)
            {
                fail(ErrorMessage.format(ErrorMessageKeys.BAD_METADATA_REFERENCE_TABLE_DEFINITION, th.getMessage()));
            }
        }
    }

    private static Integer nullSafeGet(final ResultSet resultSet, final String columnLabel) throws SQLException
    {
        final Integer value = resultSet.getInt(columnLabel);

        return resultSet.wasNull() ? null
                                   : value;
    }

    private static final class Metadata
    {
        Metadata(final int id,
                 final String mdScope)
        {
            this.id      = id;
            this.mdScope = mdScope;
        }

        public int getId()
        {
            return this.id;
        }

        public String getMdScope()
        {
            return this.mdScope;
        }

        public boolean hasValidMdScope()
        {
            return validScopes.contains(this.mdScope.toLowerCase());
        }

        private final int    id;
        private final String mdScope;

        private static final Collection<String> validScopes = Arrays.asList("undefined",
                                                                            "fieldsession",
                                                                            "collectionsession",
                                                                            "series",
                                                                            "dataset",
                                                                            "featuretype",
                                                                            "feature",
                                                                            "attributetype",
                                                                            "attribute",
                                                                            "tile",
                                                                            "model",
                                                                            "catalog",
                                                                            "schema",
                                                                            "taxonomy",
                                                                            "software",
                                                                            "service",
                                                                            "collectionhardware",
                                                                            "nongeographicdataset",
                                                                            "dimensiongroup");
    }

    private static final class MetadataReference
    {
        MetadataReference(final String referenceScope,
                          final String tableName,
                          final String columnName,
                          final String timestamp,
                          final int mdFileId,
                          final Integer rowIdValue,
                          final Integer mdParentId)
        {
            this.referenceScope = referenceScope;
            this.tableName      = tableName;
            this.columnName     = columnName;
            this.rowIdValue     = rowIdValue;
            this.timestamp      = timestamp;
            this.mdFileId       = mdFileId;
            this.mdParentId     = mdParentId;
        }

        public String getReferenceScope()
        {
            return this.referenceScope;
        }

        public String getTableName()
        {
            return this.tableName;
        }

        public String getColumnName()
        {
            return this.columnName;
        }

        public Integer getRowIdValue()
        {
            return this.rowIdValue;
        }

        public String getTimestamp()
        {
            return this.timestamp;
        }

        public int getMdFileId()
        {
            return this.mdFileId;
        }

        public Integer getMdParentId()
        {
            return this.mdParentId;
        }

        private final String  referenceScope;
        private final String  tableName;
        private final String  columnName;
        private final Integer rowIdValue;
        private final String  timestamp;
        private final int     mdFileId;
        private final Integer mdParentId;
    }

    private boolean                               hasMetadataTable;
    private boolean                               hasMetadataReferenceTable;
    private List<MetadataTests.Metadata>          metadataValues;
    private List<MetadataTests.MetadataReference> metadataReferenceValues;
}
