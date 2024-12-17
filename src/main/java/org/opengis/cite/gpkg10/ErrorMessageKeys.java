package org.opengis.cite.gpkg10;

/**
 * Defines keys used to access localized messages for assertion errors. The messages are
 * stored in Properties files that are encoded in ISO-8859-1 (Latin-1). For some languages
 * the {@code native2ascii} tool must be used to process the files and produce escaped
 * Unicode characters.
 */
public class ErrorMessageKeys {

	/** Constant <code>NOT_SCHEMA_VALID="NotSchemaValid"</code> */
	public static final String NOT_SCHEMA_VALID = "NotSchemaValid";

	/** Constant <code>EMPTY_STRING="EmptyString"</code> */
	public static final String EMPTY_STRING = "EmptyString";

	/** Constant <code>XPATH_RESULT="XPathResult"</code> */
	public static final String XPATH_RESULT = "XPathResult";

	/** Constant <code>NAMESPACE_NAME="NamespaceName"</code> */
	public static final String NAMESPACE_NAME = "NamespaceName";

	/** Constant <code>LOCAL_NAME="LocalName"</code> */
	public static final String LOCAL_NAME = "LocalName";

	/** Constant <code>XML_ERROR="XMLError"</code> */
	public static final String XML_ERROR = "XMLError";

	/** Constant <code>XPATH_ERROR="XPathError"</code> */
	public static final String XPATH_ERROR = "XPathError";

	/** Constant <code>MISSING_INFOSET_ITEM="MissingInfosetItem"</code> */
	public static final String MISSING_INFOSET_ITEM = "MissingInfosetItem";

	/** Constant <code>UNEXPECTED_STATUS="UnexpectedStatus"</code> */
	public static final String UNEXPECTED_STATUS = "UnexpectedStatus";

	/** Constant <code>UNEXPECTED_MEDIA_TYPE="UnexpectedMediaType"</code> */
	public static final String UNEXPECTED_MEDIA_TYPE = "UnexpectedMediaType";

	/** Constant <code>MISSING_ENTITY="MissingEntity"</code> */
	public static final String MISSING_ENTITY = "MissingEntity";

	/** Constant <code>INVALID_HEADER_STR="InvalidHeaderString"</code> */
	public static final String INVALID_HEADER_STR = "InvalidHeaderString";

	/** Constant <code>UNKNOWN_APP_ID="UnknownApplicationId"</code> */
	public static final String UNKNOWN_APP_ID = "UnknownApplicationId";

	/** Constant <code>INVALID_SUFFIX="InvalidSuffix"</code> */
	public static final String INVALID_SUFFIX = "InvalidSuffix";

	/** Constant <code>INVALID_DATA_TYPE="InvalidDataType"</code> */
	public static final String INVALID_DATA_TYPE = "InvalidDataType";

	/** Constant <code>PRAGMA_INTEGRITY_CHECK_NOT_OK="PragmaIntegrityCheckNotOk"</code> */
	public static final String PRAGMA_INTEGRITY_CHECK_NOT_OK = "PragmaIntegrityCheckNotOk";

	/** Constant <code>INVALID_FOREIGN_KEY="InvalidForeignKey"</code> */
	public static final String INVALID_FOREIGN_KEY = "InvalidForeignKey";

	/** Constant <code>NO_SQL_ACCESS="NoSqlAccess"</code> */
	public static final String NO_SQL_ACCESS = "NoSqlAccess";

	/** Constant <code>SQLITE_OMIT_OPTIONS="SqliteOmitOptions"</code> */
	public static final String SQLITE_OMIT_OPTIONS = "SqliteOmitOptions";

	/** Constant <code>BAD_SRS_TABLE_DEFINITION="BadSrsTableDefinition"</code> */
	public static final String BAD_SRS_TABLE_DEFINITION = "BadSrsTableDefinition";

	/** Constant <code>NO_GEOGRAPHIC_SRS="NoGeographicSrs"</code> */
	public static final String NO_GEOGRAPHIC_SRS = "NoGeographicSrs";

	/** Constant <code>NO_UNDEFINED_CARTESIAN_SRS="NoUndefinedCartesianSrs"</code> */
	public static final String NO_UNDEFINED_CARTESIAN_SRS = "NoUndefinedCartesianSrs";

	/** Constant <code>NO_UNDEFINED_GEOGRAPHIC_SRS="NoUndefinedGeographicSrs"</code> */
	public static final String NO_UNDEFINED_GEOGRAPHIC_SRS = "NoUndefinedGeographicSrs";

	/** Constant <code>UNDEFINED_SRS="UndefinedSrs"</code> */
	public static final String UNDEFINED_SRS = "UndefinedSrs";

	/** Constant <code>CONTENT_TABLE_DOES_NOT_EXIST="ContentTableDoesNotExist"</code> */
	public static final String CONTENT_TABLE_DOES_NOT_EXIST = "ContentTableDoesNotExist";

	/**
	 * Constant
	 * <code>BAD_CONTENTS_ENTRY_LAST_CHANGE_FORMAT="BadContentsEntryLastChangeFormat"</code>
	 */
	public static final String BAD_CONTENTS_ENTRY_LAST_CHANGE_FORMAT = "BadContentsEntryLastChangeFormat";

	/**
	 * Constant
	 * <code>BAD_CONTENTS_TABLE_SRS_FOREIGN_KEY="BadContentsTableSrsForeignKey"</code>
	 */
	public static final String BAD_CONTENTS_TABLE_SRS_FOREIGN_KEY = "BadContentsTableSrsForeignKey";

	/**
	 * Constant <code>BAD_CONTENTS_TABLE_DEFINITION="BadContentsTableDefinition"</code>
	 */
	public static final String BAD_CONTENTS_TABLE_DEFINITION = "BadContentsTableDefinition";

	/**
	 * Constant
	 * <code>TILES_TABLES_NOT_REFERENCED_IN_CONTENTS="TilesTablesNotReferencedInContents"</code>
	 */
	public static final String TILES_TABLES_NOT_REFERENCED_IN_CONTENTS = "TilesTablesNotReferencedInContents";

	/**
	 * Constant
	 * <code>VALUES_DO_NOT_VARY_BY_FACTOR_OF_TWO="ValuesDoNotVaryByFactorOfTwo"</code>
	 */
	public static final String VALUES_DO_NOT_VARY_BY_FACTOR_OF_TWO = "ValuesDoNotVaryByFactorOfTwo";

	/** Constant <code>INVALID_IMAGE_FORMAT="InvalidImageFormat"</code> */
	public static final String INVALID_IMAGE_FORMAT = "InvalidImageFormat";

	/**
	 * Constant
	 * <code>TILE_MATRIX_SET_TABLE_DOES_NOT_EXIST="TileMatrixSetTableDoesNotExist"</code>
	 */
	public static final String TILE_MATRIX_SET_TABLE_DOES_NOT_EXIST = "TileMatrixSetTableDoesNotExist";

	/**
	 * Constant
	 * <code>BAD_TILE_MATRIX_SET_TABLE_DEFINITION="BadTileMatrixSetTableDefinition"</code>
	 */
	public static final String BAD_TILE_MATRIX_SET_TABLE_DEFINITION = "BadTileMatrixSetTableDefinition";

	/**
	 * Constant
	 * <code>UNREFERENCED_TILE_MATRIX_SET_TABLE="UnreferencedTileMatrixSetTable"</code>
	 */
	public static final String UNREFERENCED_TILE_MATRIX_SET_TABLE = "UnreferencedTileMatrixSetTable";

	/**
	 * Constant
	 * <code>UNREFERENCED_TILES_CONTENT_TABLE_NAME="UnreferencedTilesContentTableName"</code>
	 */
	public static final String UNREFERENCED_TILES_CONTENT_TABLE_NAME = "UnreferencedTilesContentTableName";

	/** Constant <code>BAD_MATRIX_SET_SRS_REFERENCE="BadMatrixSetSrsReference"</code> */
	public static final String BAD_MATRIX_SET_SRS_REFERENCE = "BadMatrixSetSrsReference";

	/**
	 * Constant
	 * <code>TILE_MATRIX_TABLE_DOES_NOT_EXIST="TileMatrixTableDoesNotExist"</code>
	 */
	public static final String TILE_MATRIX_TABLE_DOES_NOT_EXIST = "TileMatrixTableDoesNotExist";

	/**
	 * Constant
	 * <code>BAD_TILE_MATRIX_TABLE_DEFINITION="BadTileMatrixTableDefinition"</code>
	 */
	public static final String BAD_TILE_MATRIX_TABLE_DEFINITION = "BadTileMatrixTableDefinition";

	/**
	 * Constant <code>BAD_MATRIX_CONTENTS_REFERENCES="BadMatrixContentsReferences"</code>
	 */
	public static final String BAD_MATRIX_CONTENTS_REFERENCES = "BadMatrixContentsReferences";

	/** Constant <code>MISSING_TILE_MATRIX_ENTRY="MissingTileMatrixEntry"</code> */
	public static final String MISSING_TILE_MATRIX_ENTRY = "MissingTileMatrixEntry";

	/** Constant <code>BAD_PIXEL_DIMENSIONS="BadPixelDimensions"</code> */
	public static final String BAD_PIXEL_DIMENSIONS = "BadPixelDimensions";

	/** Constant <code>NEGATIVE_ZOOM_LEVEL="NegativeZoomLevel"</code> */
	public static final String NEGATIVE_ZOOM_LEVEL = "NegativeZoomLevel";

	/** Constant <code>NON_POSITIVE_MATRIX_WIDTH="NonPositiveMatrixWidth"</code> */
	public static final String NON_POSITIVE_MATRIX_WIDTH = "NonPositiveMatrixWidth";

	/** Constant <code>NON_POSITIVE_MATRIX_HEIGHT="NonPositiveMatrixHeight"</code> */
	public static final String NON_POSITIVE_MATRIX_HEIGHT = "NonPositiveMatrixHeight";

	/** Constant <code>NON_POSITIVE_TILE_WIDTH="NonPositiveTileWidth"</code> */
	public static final String NON_POSITIVE_TILE_WIDTH = "NonPositiveTileWidth";

	/** Constant <code>NON_POSITIVE_TILE_HEIGHT="NonPositiveTileHeight"</code> */
	public static final String NON_POSITIVE_TILE_HEIGHT = "NonPositiveTileHeight";

	/** Constant <code>NON_POSITIVE_PIXEL_X_SIZE="NonPositivePixelXSize"</code> */
	public static final String NON_POSITIVE_PIXEL_X_SIZE = "NonPositivePixelXSize";

	/** Constant <code>NON_POSITIVE_PIXEL_Y_SIZE="NonPositivePixelYSize"</code> */
	public static final String NON_POSITIVE_PIXEL_Y_SIZE = "NonPositivePixelYSize";

	/** Constant <code>PIXEL_SIZE_NOT_DECREASING="PixelSizeNotDecreasing"</code> */
	public static final String PIXEL_SIZE_NOT_DECREASING = "PixelSizeNotDecreasing";

	/**
	 * Constant
	 * <code>BAD_TILE_PYRAMID_USER_DATA_TABLE_DEFINITION="BadTilePyramidUserDataTableDefinition"</code>
	 */
	public static final String BAD_TILE_PYRAMID_USER_DATA_TABLE_DEFINITION = "BadTilePyramidUserDataTableDefinition";

	/** Constant <code>UNDEFINED_ZOOM_LEVEL="UndefinedZoomLevel"</code> */
	public static final String UNDEFINED_ZOOM_LEVEL = "UndefinedZoomLevel";

	/** Constant <code>TILE_COLUMN_OUT_OF_RANGE="TileColumnOutOfRange"</code> */
	public static final String TILE_COLUMN_OUT_OF_RANGE = "TileColumnOutOfRange";

	/** Constant <code>TILE_ROW_OUT_OF_RANGE="TileRowOutOfRange"</code> */
	public static final String TILE_ROW_OUT_OF_RANGE = "TileRowOutOfRange";

	/**
	 * Constant <code>BAD_METADATA_TABLE_DEFINITION="BadMetadataTableDefinition"</code>
	 */
	public static final String BAD_METADATA_TABLE_DEFINITION = "BadMetadataTableDefinition";

	/** Constant <code>INVALID_METADATA_SCOPE="InvalidMetadataScope"</code> */
	public static final String INVALID_METADATA_SCOPE = "InvalidMetadataScope";

	/**
	 * Constant
	 * <code>MISSING_METADATA_REFERENCE_TABLE="MissingMetadataReferenceTable"</code>
	 */
	public static final String MISSING_METADATA_REFERENCE_TABLE = "MissingMetadataReferenceTable";

	/**
	 * Constant
	 * <code>BAD_METADATA_REFERENCE_TABLE_DEFINITION="BadMetadataReferenceTableDefinition"</code>
	 */
	public static final String BAD_METADATA_REFERENCE_TABLE_DEFINITION = "BadMetadataReferenceTableDefinition";

	/**
	 * Constant
	 * <code>INVALID_METADATA_REFERENCE_SCOPE="InvalidMetadataReferenceScope"</code>
	 */
	public static final String INVALID_METADATA_REFERENCE_SCOPE = "InvalidMetadataReferenceScope";

	/**
	 * Constant
	 * <code>BAD_METADATA_REFERENCE_SCOPE_COLUMN_NAME_AGREEMENT="BadMetadataReferenceScopeColumnNameAgre"{trunked}</code>
	 */
	public static final String BAD_METADATA_REFERENCE_SCOPE_COLUMN_NAME_AGREEMENT = "BadMetadataReferenceScopeColumnNameAgreement";

	/**
	 * Constant
	 * <code>INVALID_METADATA_REFERENCE_TABLE="InvalidMetadataReferenceTable"</code>
	 */
	public static final String INVALID_METADATA_REFERENCE_TABLE = "InvalidMetadataReferenceTable";

}
