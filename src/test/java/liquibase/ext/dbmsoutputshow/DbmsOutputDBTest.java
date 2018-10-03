package liquibase.ext.dbmsoutputshow;

import liquibase.ext.testing.BaseTestCase;
import org.junit.Before;
import org.junit.Test;

public class DbmsOutputDBTest extends BaseTestCase {

    @Before
    public void setUp() throws Exception {
        changeLogFile = "liquibase/ext/dbmsoutputshow/changelog.test.xml";
        connectToDB();
//        cleanDB();
    }

    @Test
    public void testCompare() throws Exception {
        if (connection == null) {
            return;
        }
        liquiBase.update((String) null);
    }
}
