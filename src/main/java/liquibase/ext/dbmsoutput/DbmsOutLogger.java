package liquibase.ext.dbmsoutput;

import liquibase.logging.LogFactory;
import liquibase.logging.Logger;

public class DbmsOutLogger {

    private final String prefix;
    private Logger logger;

    private DbmsOutLogger(final String prefix) {
        this.prefix = prefix;
    }

    public void warning(String message, Throwable th) {
        if (logger != null) {
            logger.warning(message, th);
            return;
        }
        System.out.printf("%s: %s\n", message, th.toString());
    }

    public void info(String message) {
        if (logger != null) {
            logger.info(message);
            return;
        }
        System.out.printf("%s: %s\n", prefix, message);
    }

    public static DbmsOutLogger build(String type, String prefix) {
        final DbmsOutLogger outLogger = new DbmsOutLogger(prefix);
        if (Constants.DEFAULT_LOG_OUT.equals(type)) {
            outLogger.logger = LogFactory.getInstance().getLog(prefix);
        }
        return outLogger;
    }
}
