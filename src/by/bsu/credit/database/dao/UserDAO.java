package by.bsu.credit.database.dao;

import by.bsu.credit.database.WrapperConnection;
import by.bsu.credit.entity.User;
import by.bsu.credit.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.bsu.credit.database.Column.*;

/**
 * Created by User on 02.05.2016.
 */
public class UserDAO extends AbstractDAO<User> {
    private static final String INSERT = "INSERT INTO users(login,password,role) VALUES(?,?,?)";

    private static final String SELECT_ALL = "SELECT id,login,password,role FROM users";
    private static final String SELECT_BY_ID = "SELECT login,password,role FROM users WHERE id=?";

    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id=?";

    private static final String UPDATE_BY_ID = "UPDATE users SET login=?,password=?,role=? WHERE id=?";

    public UserDAO(WrapperConnection connection) {
        super(connection);
    }

    @Override
    public boolean insert(User entity) throws DAOException {
        int res;
        try (PreparedStatement st = connection.prepareStatement(INSERT)) {
            st.setString(1, entity.getLogin());
            st.setString(2, entity.getPassword());
            st.setInt(3, entity.getRole());
            res = st.executeUpdate();
            updateId(res, entity);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return res > 0;
    }

    @Override
    public List<User> findAll() throws DAOException {
        List<User> allUsers = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(ID.column());
                String login = rs.getString(LOGIN.column());
                String password = rs.getString(PASSWORD.column());
                int role = rs.getByte(ROLE.column());
                User user = new User(id, login, password, role);
                allUsers.add(user);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return allUsers;
    }

    @Override
    public Optional<User> findEntityById(int id) throws DAOException {
        User user = null;
        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String login = rs.getString(LOGIN.column());
                String password = rs.getString(PASSWORD.column());
                int role = rs.getByte(ROLE.column());
                user = new User(id, login, password, role);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean deleteEntityById(int id) throws DAOException {
        int res;
        try (PreparedStatement st = connection.prepareStatement(DELETE_BY_ID)) {
            st.setInt(1, id);
            res = st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return res > 0;
    }

    @Override
    public Optional<User> update(User entity) throws DAOException {
        Optional<User> user = findEntityById(entity.getId());
        try (PreparedStatement st = connection.prepareStatement(UPDATE_BY_ID)) {
            st.setString(1, entity.getLogin());
            st.setString(2, entity.getPassword());
            st.setInt(3, entity.getRole());
            st.setInt(4, entity.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }
}
