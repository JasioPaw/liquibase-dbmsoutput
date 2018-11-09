package liquibase.ext.dbmsoutput;

public final class Constants {

    public static final String DEFAULT_LOG_PREFIX = "dbms_output";
    public static final String DEFAULT_ERROR_MESSAGE = "Error during processing";
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";

    public static final String ENABLE_ACTION = "enable";
    public static final String SHOW_ACTION = "show";

    public static final String REQUIRED_FIELD_NAME = "action";

    public static final String ENABLE_DBMS_OUTPUT = "BEGIN DBMS_OUTPUT.ENABLE(buffer_size => ?); END;";
    public static final String DISABLE_DBMS_OUTPUT = "BEGIN DBMS_OUTPUT.DISABLE(); END;";
    public static final String GET_LINES_DBMS_OUTPUT = "DECLARE\n" +
        "  v_Data     DBMS_OUTPUT.CHARARR;\n" +
        "  v_NumLines NUMBER;\n" +
        "  v_blob     blob;\n" +
        "  PROCEDURE blob_return(original IN  BLOB, output   OUT BLOB)\n" +
        "  IS\n" +
        "    BEGIN\n" +
        "      output := original;\n" +
        "    END;\n" +
        "BEGIN\n" +
        "  dbms_lob.createtemporary(lob_loc => v_blob, cache => true, dur => dbms_lob.call);\n" +
        "  v_NumLines := ?;\n"+
        "  DBMS_OUTPUT.GET_LINES(lines => v_Data, numlines => v_NumLines);\n" +
        "  FOR v_Counter IN 1..v_NumLines LOOP\n" +
        "    dbms_lob.append(v_blob, UTL_RAW.CAST_TO_RAW('\n'));\n" +
        "    dbms_lob.append(v_blob, UTL_RAW.CAST_TO_RAW(v_Data(v_Counter)));\n" +
        "  END LOOP;\n" +
        "  blob_return(v_blob, ?);\n" +
        "END;";

    public static final String DEFAULT_LOG_OUT = "log";

    public static final String VALID_ERROR_STMT_NULL = "Statement is null";
    public static final String VALID_ERROR_DB_NULL = "Database is null";
    public static final String VALID_ERROR_CON_NULL = "Connection is null";

}
