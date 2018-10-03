package liquibase.ext.dbmsoutput;

import liquibase.statement.AbstractSqlStatement;

public class DbmsOutputStatement extends AbstractSqlStatement {

    private String action;
    private Integer numLines;
    private Integer bufferSize;
    private DbmsOutLogger logger;

    private DbmsOutputStatement() {
    }

    public String getAction() {
        return action;
    }

    public Integer getNumLines() {
        return numLines;
    }

    public Integer getBufferSize() {
        return bufferSize;
    }

    public DbmsOutLogger getLogger() {
        return logger;
    }

    public static DbmsOutputStatement build(DbmsOutputChange change) {
        final DbmsOutputStatement statement = new DbmsOutputStatement();
        statement.action = change.getAction();
        statement.numLines = change.getNumLines();
        statement.bufferSize = change.getBufferSize();
        statement.logger = DbmsOutLogger.build(change.getOutType(), change.getLogPrefix());
        return statement;
    }
}
