package by.bsu.credit.database.dao;

import by.bsu.credit.database.WrapperConnection;
import by.bsu.credit.entity.Account;
import by.bsu.credit.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.bsu.credit.database.Column.CLIENT_ID;
import static by.bsu.credit.database.Column.ID;
import static by.bsu.credit.database.Column.MONEY;

/**
 * Created by User on 03.05.2016.
 */
public class AccountDAO extends AbstractDAO<Account> {
    private static final String INSERT = "INSERT INTO accounts(client_id,money) VALUES(?,?)";

    private static final String SELECT_ALL = "SELECT id,client_id,money FROM accounts";
    private static final String SELECT_BY_ID = "SELECT client_id,money FROM accounts WHERE id=?";

    private static final String DELETE_BY_ID = "DELETE FROM accounts WHERE id=?";

    private static final String UPDATE_BY_ID = "UPDATE accounts SET client_id=?,money=? WHERE id=?";

    public AccountDAO(WrapperConnection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Account entity) throws DAOException {
        int res;
        try (PreparedStatement st = connection.prepareStatement(INSERT)) {
            st.setInt(1, entity.getClientId());
            st.setInt(2, entity.getMoney());
            res = st.executeUpdate();
            updateId(res, entity);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return res > 0;
    }

    @Override
    public List<Account> findAll() throws DAOException {
        List<Account> allAccounts = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(ID.column());
                int clientId = rs.getInt(CLIENT_ID.column());
                int money = rs.getInt(MONEY.column());
                Account account = new Account(id, clientId, money);
                allAccounts.add(account);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return allAccounts;
    }

    @Override
    public Optional<Account> findEntityById(int id) throws DAOException {
        Account account = null;
        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int clientId = rs.getInt(CLIENT_ID.column());
                int money = rs.getInt(MONEY.column());
                account = new Account(id, clientId, money);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(account);
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
    public Optional<Account> update(Account entity) throws DAOException {
        Optional<Account> request = findEntityById(entity.getId());
        try (PreparedStatement st = connection.prepareStatement(UPDATE_BY_ID)) {
            st.setInt(1, entity.getClientId());
            st.setInt(2, entity.getMoney());
            st.setInt(3, entity.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return request;
    }
}
