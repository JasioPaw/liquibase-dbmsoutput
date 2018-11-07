package liquibase.ext.dbmsoutput;

import liquibase.database.Database;
import liquibase.database.core.OracleDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.ValidationErrors;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.AbstractSqlGenerator;

import javax.xml.bind.ValidationException;
import java.sql.*;

public class DbmsOutputGenerator extends AbstractSqlGenerator<DbmsOutputStatement> {

    public boolean supports(DbmsOutputStatement statement, Database database) {
        return database instanceof OracleDatabase;
    }

    public ValidationErrors validate(final DbmsOutputStatement statement,
                                     final Database database,
                                     final SqlGeneratorChain chain) {
        ValidationErrors validationErrors = new ValidationErrors();
        validationErrors.checkRequiredField(Constants.REQUIRED_FIELD_NAME, statement.getAction());
        return validationErrors;
    }

    private void validateParams(final DbmsOutputStatement statement,
                                final Database database) throws ValidationException {
        if (statement == null) {
            throw new ValidationException(Constants.VALID_ERROR_STMT_NULL);
        }

        if (database == null) {
            throw new ValidationException(Constants.VALID_ERROR_DB_NULL);
        }

        if (database.getConnection() == null) {
            throw new ValidationException(Constants.VALID_ERROR_CON_NULL);
        }
    }

    public Sql[] generateSql(final DbmsOutputStatement statement,
                             final Database database,
                             final SqlGeneratorChain chain) {
        try {
            validateParams(statement, database);
            if (Constants.ENABLE_ACTION.equals(statement.getAction())) {
                return enable(statement, database);
            } else if (Constants.SHOW_ACTION.equals(statement.getAction())) {
                return show(statement, database);
            }
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        return null;

    }

    private Sql[] enable(final DbmsOutputStatement statement,
                         final Database database) {

        final JdbcConnection connection = ((JdbcConnection) database.getConnection());
        try {
            PreparedStatement enableDbmsOutputStmt = connection.prepareStatement(Constants.ENABLE_DBMS_OUTPUT);
            enableDbmsOutputStmt.setInt(1, statement.getBufferSize());
            enableDbmsOutputStmt.executeUpdate();
        } catch (DatabaseException e) {
            statement.getLogger().warning(Constants.DEFAULT_ERROR_MESSAGE, e);
        } catch (SQLException e) {
            statement.getLogger().warning(Constants.DEFAULT_ERROR_MESSAGE, e);
        }

        return null;
    }

    private Sql[] show(final DbmsOutputStatement statement,
                       final Database database) {

        final JdbcConnection connection = ((JdbcConnection) database.getConnection());
        Array array = null;

        try {
            try {
                final CallableStatement call = connection.prepareCall(Constants.GET_LINES_DBMS_OUTPUT);
                call.setInt(2, statement.getNumLines());
                call.registerOutParameter(1, Types.ARRAY, Constants.CALL_OUT_TYPE_NAME);
                call.registerOutParameter(2, Types.INTEGER);
                call.execute();

                array = call.getArray(1);
                if (array != null) {
                    String[] arrLines = (String[]) array.getArray();
                    for (int i = 0; i < arrLines.length; i++) {
                        if (arrLines[i] != null) {
                            statement.getLogger().info(arrLines[i]);
                        }
                    }
                }
            } finally {
                if (array != null) {
                    try {
                        array.free();
                    } catch (AbstractMethodError err) {}
                }

                connection.createStatement().
                        executeUpdate(Constants.DISABLE_DBMS_OUTPUT);
            }
        } catch (SQLException e) {
            statement.getLogger().warning(Constants.DEFAULT_ERROR_MESSAGE, e);
        } catch (DatabaseException e) {
            statement.getLogger().warning(Constants.DEFAULT_ERROR_MESSAGE, e);
        }

        return null;
    }
}
