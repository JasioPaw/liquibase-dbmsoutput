package liquibase.ext.dbmsoutput;

public final class Constants {

    public static final String DEFAULT_LOG_PREFIX = "dbms_output";
    public static final String DEFAULT_ERROR_MESSAGE = "Error during processing";
    public static final String ENABLE_ACTION = "enable";
    public static final String SHOW_ACTION = "show";

    public static final String REQUIRED_FIELD_NAME = "action";

    public static final String ENABLE_DBMS_OUTPUT = "BEGIN DBMS_OUTPUT.ENABLE(buffer_size => ?); END;";
    public static final String DISABLE_DBMS_OUTPUT = "BEGIN DBMS_OUTPUT.DISABLE(); END;";
    public static final String GET_LINES_DBMS_OUTPUT = "BEGIN DBMS_OUTPUT.GET_LINES(lines => ?, numlines => ?); END;";
    public static final String CALL_OUT_TYPE_NAME = "DBMSOUTPUT_LINESARRAY";

    public static final String DEFAULT_LOG_OUT = "log";

    public static final String VALID_ERROR_STMT_NULL = "Statement is null";
    public static final String VALID_ERROR_DB_NULL = "Database is null";
    public static final String VALID_ERROR_CON_NULL = "Connection is null";

}
