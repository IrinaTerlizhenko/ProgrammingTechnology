package by.bsu.credit.database.dao;

import by.bsu.credit.database.WrapperConnection;
import by.bsu.credit.entity.Request;
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
public class RequestDAO extends AbstractDAO<Request> {
    private static final String INSERT = "INSERT INTO requests(client_id,money) VALUES(?,?)";

    private static final String SELECT_ALL = "SELECT id,client_id,money FROM requests";
    private static final String SELECT_BY_ID = "SELECT client_id,money FROM requests WHERE id=?";

    private static final String DELETE_BY_ID = "DELETE FROM requests WHERE id=?";

    private static final String UPDATE_BY_ID = "UPDATE requests SET client_id=?,money=? WHERE id=?";

    public RequestDAO(WrapperConnection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Request entity) throws DAOException {
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
    public List<Request> findAll() throws DAOException {
        List<Request> allRequests = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(ID.column());
                int clientId = rs.getInt(CLIENT_ID.column());
                int money = rs.getInt(MONEY.column());
                Request request = new Request(id, clientId, money);
                allRequests.add(request);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return allRequests;
    }

    @Override
    public Optional<Request> findEntityById(int id) throws DAOException {
        Request request = null;
        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int clientId = rs.getInt(CLIENT_ID.column());
                int money = rs.getInt(MONEY.column());
                request = new Request(id, clientId, money);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(request);
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
    public Optional<Request> update(Request entity) throws DAOException {
        Optional<Request> request = findEntityById(entity.getId());
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
