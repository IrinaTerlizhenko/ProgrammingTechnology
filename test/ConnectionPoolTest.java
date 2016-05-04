import by.bsu.credit.database.ConnectionPool;
import by.bsu.credit.database.WrapperConnection;
import org.junit.*;

import java.util.NoSuchElementException;

/**
 * Created by User on 02.05.2016.
 */
public class ConnectionPoolTest {
    private static ConnectionPool pool;
    private static WrapperConnection[] connections;

    @BeforeClass
    public static void init() {
        pool = ConnectionPool.getInstance();
    }

    @Before
    public void initConnections() {
        connections = new WrapperConnection[20];
    }

    @Test
    public void takeConnectionTestNormal() {
        for (int i = 0; i < connections.length; i++) {
            connections[i] = pool.takeConnection().get();
            Assert.assertNotNull(connections[i]);
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void takeConnectionTestExceptional() {
        for (int i = 0; i < connections.length; i++) {
            connections[i] = pool.takeConnection().get();
            Assert.assertNotNull(connections[i]);
        }
        pool.takeConnection().get();
    }

    @Test
    public void returnConnectionTestNormal() {
        WrapperConnection connection = pool.takeConnection().get();
        pool.returnConnection(connection);
        for (int i = 0; i < connections.length; i++) {
            connections[i] = pool.takeConnection().get();
        }
        Assert.assertEquals(connection, connections[connections.length-1]);
    }

    @Test(expected = IllegalStateException.class)
    public void returnConnectionTestExceptional() {
        WrapperConnection connection = pool.takeConnection().get();
        pool.returnConnection(connection);
        pool.returnConnection(connection);
    }

    @After
    public void returnConnections() {
        for (int i = 0; i < 20; i++) {
            if (connections[i] != null) {
                pool.returnConnection(connections[i]);
            }
        }
    }

    @AfterClass
    public static void destroy() {
        pool.closePool();
    }
}
