package by.bsu.credit.database.dao;

import by.bsu.credit.database.WrapperConnection;
import by.bsu.credit.entity.Client;
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
public class ClientDAO extends AbstractDAO<Client> {
    private static final String INSERT = "INSERT INTO clients(firstname,lastname,passport) VALUES(?,?,?)";

    private static final String SELECT_ALL = "SELECT id,firstname,lastname,passport FROM clients";
    private static final String SELECT_BY_ID = "SELECT firstname,lastname,passport FROM clients WHERE id=?";

    private static final String DELETE_BY_ID = "DELETE FROM clients WHERE id=?";

    private static final String UPDATE_BY_ID = "UPDATE clients SET firstname=?,lastname=?,passport=? WHERE id=?";

    public ClientDAO(WrapperConnection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Client entity) throws DAOException {
        int res;
        try (PreparedStatement st = connection.prepareStatement(INSERT)) {
            st.setString(1, entity.getFirstname());
            st.setString(2, entity.getLastname());
            st.setString(3, entity.getPassport());
            res = st.executeUpdate();
            updateId(res, entity);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return res > 0;
    }

    @Override
    public List<Client> findAll() throws DAOException {
        List<Client> allClients = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(ID.column());
                String firstname = rs.getString(FIRSTNAME.column());
                String lastname = rs.getString(LASTNAME.column());
                String passport = rs.getString(PASSPORT.column());
                Client client = new Client(id, firstname, lastname, passport);
                allClients.add(client);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return allClients;
    }

    @Override
    public Optional<Client> findEntityById(int id) throws DAOException {
        Client client = null;
        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String firstname = rs.getString(FIRSTNAME.column());
                String lastname = rs.getString(LASTNAME.column());
                String passport = rs.getString(PASSPORT.column());
                client = new Client(id, firstname, lastname, passport);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(client);
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
    public Optional<Client> update(Client entity) throws DAOException {
        Optional<Client> user = findEntityById(entity.getId());
        try (PreparedStatement st = connection.prepareStatement(UPDATE_BY_ID)) {
            st.setString(1, entity.getFirstname());
            st.setString(2, entity.getLastname());
            st.setString(3, entity.getPassport());
            st.setInt(4, entity.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }
}
