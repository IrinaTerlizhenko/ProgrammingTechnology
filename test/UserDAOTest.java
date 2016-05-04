import by.bsu.credit.database.ConnectionPool;
import by.bsu.credit.database.WrapperConnection;
import by.bsu.credit.database.dao.UserDAO;
import by.bsu.credit.entity.User;
import by.bsu.credit.exception.DAOException;
import org.junit.*;

import java.util.Optional;

/**
 * Created by User on 03.05.2016.
 */
public class UserDAOTest {
    private static ConnectionPool pool;

    @BeforeClass
    public static void init() {
        pool = ConnectionPool.getInstance();
    }

    @Test
    public void insertTestNormal() {
        Optional<WrapperConnection> optConnection = pool.takeConnection();
        WrapperConnection connection = optConnection.get();
        UserDAO userDAO = new UserDAO(connection);
        User user = new User(0, "insertTestNormal", "insertTestNormal", 1);
        boolean inserted;
        try {
            inserted = userDAO.insert(user);
            Assert.assertTrue(inserted);
            userDAO.deleteEntityById(user.getId());
        } catch (DAOException e) {
            Assert.fail();
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Test(expected = DAOException.class)
    public void insertTestExceptional() throws DAOException {
        Optional<WrapperConnection> optConnection = pool.takeConnection();
        WrapperConnection connection = optConnection.get();
        UserDAO userDAO = new UserDAO(connection);
        User user = new User(0, "insertTestExceptional", "insertTestExceptional", 1);
        try {
            userDAO.insert(user);
            userDAO.insert(user);
        } finally {
            userDAO.deleteEntityById(user.getId());
            pool.returnConnection(connection);
        }
    }

    @Test
    public void updateTestNormal() {
        Optional<WrapperConnection> optConnection = pool.takeConnection();
        WrapperConnection connection = optConnection.get();
        UserDAO userDAO = new UserDAO(connection);
        User user = new User(0, "updateTestNormal", "updateTestNormal", 1);
        boolean updated;
        try {
            userDAO.insert(user);
            User newUser = new User(user.getId(), "updateTestNormalNew", "updateTestNormalNew", 1);
            User oldUser = userDAO.update(newUser).get();
            Assert.assertEquals(user, oldUser);
            userDAO.deleteEntityById(user.getId());
        } catch (DAOException e) {
            Assert.fail();
        } finally {
            pool.returnConnection(connection);
        }
    }

    @Test
    public void deleteEntityByIdTestNormal() {
        Optional<WrapperConnection> optConnection = pool.takeConnection();
        WrapperConnection connection = optConnection.get();
        UserDAO userDAO = new UserDAO(connection);
        User user = new User(0, "deleteEntityByIdTestNormal", "deleteEntityByIdTestNormal", 1);
        boolean deleted;
        try {
            userDAO.insert(user);
            deleted = userDAO.deleteEntityById(user.getId());
            Assert.assertTrue(deleted);
        } catch (DAOException e) {
            Assert.fail();
        } finally {
            pool.returnConnection(connection);
        }
    }

    @AfterClass
    public static void destroy() {
        pool.closePool();
    }
}
