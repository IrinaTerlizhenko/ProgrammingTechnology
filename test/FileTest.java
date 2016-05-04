import org.junit.Test;

import java.util.ResourceBundle;

/**
 * Created by User on 02.05.2016.
 */
public class FileTest {
    private static final String RESOURCE_DATABASE = "resources.by.bsu.credit.database";
    private static final String URL = "url";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String AUTORECONNECT = "autoReconnect";
    private static final String CHARENCODING = "characterEncoding";
    private static final String USEUNICODE = "useUnicode";
    private static final String POOLSIZE = "poolsize";

    @Test
    public void databasePropertiesTest() {
        ResourceBundle resource = ResourceBundle.getBundle(RESOURCE_DATABASE);
        String url = resource.getString(URL);
        String user = resource.getString(USER);
        String pass = resource.getString(PASSWORD);
        String autoReconnect = resource.getString(AUTORECONNECT);
        String charEncoding = resource.getString(CHARENCODING);
        String useUnicode = resource.getString(USEUNICODE);
        int poolSize = Integer.parseInt(resource.getString(POOLSIZE));
    }
}
