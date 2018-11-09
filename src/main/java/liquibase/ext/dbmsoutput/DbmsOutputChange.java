package liquibase.ext.dbmsoutput;

import liquibase.change.AbstractChange;
import liquibase.change.ChangeMetaData;
import liquibase.change.DatabaseChange;
import liquibase.database.Database;
import liquibase.statement.SqlStatement;

import java.text.MessageFormat;

@DatabaseChange(name = "dbmsOutput", description = "Oracle DBMS_OUTPUT Reader", priority = ChangeMetaData.PRIORITY_DEFAULT)
public class DbmsOutputChange extends AbstractChange {

    private String action;
    private Integer numLines;
    private Integer bufferSize;
    private String outType;
    private String logPrefix;
    private String charsetName;

    @Override
    public String getConfirmationMessage() {
        return MessageFormat.format("End action: {0}", getAction());
    }

    @Override
    public SqlStatement[] generateStatements(Database database) {
        return new SqlStatement[]{DbmsOutputStatement.build(this)};
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getNumLines() {
        // The default number of lines, taken from the buffer.
        return numLines == null ? 1000 : numLines;
    }

    public void setNumLines(Integer numLines) {
        this.numLines = numLines;
    }

    public Integer getBufferSize() {
        // The default buffer size is 20000 bytes.
        // The minimum size is 2000 bytes and the maximum is unlimited
        return bufferSize == null ? 20000 : bufferSize;
    }

    public void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }

    public String getOutType() {
        return outType == null ? Constants.DEFAULT_LOG_OUT : outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    public String getLogPrefix() {
        return logPrefix == null ? Constants.DEFAULT_LOG_PREFIX : logPrefix;
    }

    public void setLogPrefix(String logPrefix) {
        this.logPrefix = logPrefix;
    }


    public String getCharsetName() {
        return charsetName == null ? Constants.DEFAULT_CHARSET_NAME: charsetName;
    }

    public void setCharsetName(String charsetName) {
        this.charsetName = charsetName;
    }
}
