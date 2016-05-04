package by.bsu.credit.database.dao;

import by.bsu.credit.database.WrapperConnection;
import by.bsu.credit.entity.Entity;
import by.bsu.credit.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static by.bsu.credit.database.Column.ID;

/**
 * Created by User on 02.05.2016.
 */
public abstract class AbstractDAO<T extends Entity> {
    protected WrapperConnection connection;
    private static final String LAST_ID = "SELECT LAST_INSERT_ID() AS id";

    public AbstractDAO(WrapperConnection connection) {
        this.connection = connection;
    }

    public abstract boolean insert(T entity) throws DAOException;

    public abstract List<T> findAll() throws DAOException;

    public abstract Optional<T> findEntityById(int id) throws DAOException;

    public abstract boolean deleteEntityById(int id) throws DAOException;

    public abstract Optional<T> update(T entity) throws DAOException;

    public void close(Statement st) {
        connection.closeStatement(st);
    }

    protected void updateId(int stringsAffected, Entity entity) throws SQLException {
        if (stringsAffected > 0) {
            try (PreparedStatement stId = connection.prepareStatement(LAST_ID)) {
                ResultSet rs = stId.executeQuery();
                if (rs.next()) {
                    entity.setId(rs.getInt(ID.column()));
                }
            }
        }
    }
}
