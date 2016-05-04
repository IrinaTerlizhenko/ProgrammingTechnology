package by.bsu.credit.database.dao;

import by.bsu.credit.database.WrapperConnection;
import by.bsu.credit.entity.Operation;
import by.bsu.credit.exception.DAOException;

import java.sql.Date;
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
public class OperationDAO extends AbstractDAO<Operation> {
    private static final String INSERT = "INSERT INTO operations(client_id,returned,money,due_date,interest,approved) VALUES(?,?,?)";

    private static final String SELECT_ALL = "SELECT id,client_id,returned,money,due_date,interest,approved FROM operations";
    private static final String SELECT_BY_ID = "SELECT client_id,returned,money,due_date,interest,approved FROM operations WHERE id=?";

    private static final String DELETE_BY_ID = "DELETE FROM operations WHERE id=?";

    private static final String UPDATE_BY_ID = "UPDATE operations SET client_id=?,returned=?,money=?,due_date=?,interest=?,approved=? WHERE id=?";

    public OperationDAO(WrapperConnection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Operation entity) throws DAOException {
        int res;
        try (PreparedStatement st = connection.prepareStatement(INSERT)) {
            st.setInt(1, entity.getClientId());
            st.setBoolean(2, entity.isReturned());
            st.setInt(3, entity.getMoney());
            st.setDate(4, entity.getDueDate());
            st.setInt(5, entity.getInterest());
            st.setBoolean(6, entity.isApproved());
            res = st.executeUpdate();
            updateId(res, entity);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return res > 0;
    }

    @Override
    public List<Operation> findAll() throws DAOException {
        List<Operation> allOperations = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(ID.column());
                int clientId = rs.getInt(CLIENT_ID.column());
                boolean returned = rs.getBoolean(RETURNED.column());
                int money = rs.getInt(MONEY.column());
                Date dueDate = rs.getDate(DUE_DATE.column());
                int interest = rs.getInt(INTEREST.column());
                boolean approved = rs.getBoolean(APPROVED.column());
                Operation operation = new Operation(id, clientId, returned, money, dueDate, interest, approved);
                allOperations.add(operation);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return allOperations;
    }

    @Override
    public Optional<Operation> findEntityById(int id) throws DAOException {
        Operation operation = null;
        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int clientId = rs.getInt(CLIENT_ID.column());
                boolean returned = rs.getBoolean(RETURNED.column());
                int money = rs.getInt(MONEY.column());
                Date dueDate = rs.getDate(DUE_DATE.column());
                int interest = rs.getInt(INTEREST.column());
                boolean approved = rs.getBoolean(APPROVED.column());
                operation = new Operation(id, clientId, returned, money, dueDate, interest, approved);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(operation);
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
    public Optional<Operation> update(Operation entity) throws DAOException {
        Optional<Operation> operation = findEntityById(entity.getId());
        try (PreparedStatement st = connection.prepareStatement(UPDATE_BY_ID)) {
            st.setInt(1, entity.getClientId());
            st.setBoolean(2, entity.isReturned());
            st.setInt(3, entity.getMoney());
            st.setDate(4, entity.getDueDate());
            st.setInt(5, entity.getInterest());
            st.setBoolean(6, entity.isApproved());
            st.setInt(7, entity.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return operation;
    }
}
