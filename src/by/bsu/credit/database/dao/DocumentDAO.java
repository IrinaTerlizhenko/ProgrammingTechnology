package by.bsu.credit.database.dao;

import by.bsu.credit.database.WrapperConnection;
import by.bsu.credit.entity.Document;
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
public class DocumentDAO extends AbstractDAO<Document> {
    private static final String INSERT = "INSERT INTO documents(login,password,role) VALUES(?,?,?)";

    private static final String SELECT_ALL = "SELECT id,request_id,document FROM documents";
    private static final String SELECT_BY_ID = "SELECT request_id,document FROM documents WHERE id=?";

    private static final String DELETE_BY_ID = "DELETE FROM documents WHERE id=?";

    private static final String UPDATE_BY_ID = "UPDATE documents SET request_id=?,document=? WHERE id=?";

    public DocumentDAO(WrapperConnection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Document entity) throws DAOException {
        int res;
        try (PreparedStatement st = connection.prepareStatement(INSERT)) {
            st.setInt(1, entity.getRequestId());
            st.setString(2, entity.getDocument());
            res = st.executeUpdate();
            updateId(res, entity);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return res > 0;
    }

    @Override
    public List<Document> findAll() throws DAOException {
        List<Document> allDocuments = new ArrayList<>();
        try (PreparedStatement st = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(ID.column());
                int requestId = rs.getInt(REQUEST_ID.column());
                String doc = rs.getString(DOCUMENT.column());
                Document document = new Document(id, requestId, doc);
                allDocuments.add(document);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return allDocuments;
    }

    @Override
    public Optional<Document> findEntityById(int id) throws DAOException {
        Document document = null;
        try (PreparedStatement st = connection.prepareStatement(SELECT_BY_ID)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int requestId = rs.getInt(REQUEST_ID.column());
                String doc = rs.getString(DOCUMENT.column());
                document = new Document(id, requestId, doc);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return Optional.ofNullable(document);
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
    public Optional<Document> update(Document entity) throws DAOException {
        Optional<Document> document = findEntityById(entity.getId());
        try (PreparedStatement st = connection.prepareStatement(UPDATE_BY_ID)) {
            st.setInt(1, entity.getRequestId());
            st.setString(2, entity.getDocument());
            st.setInt(3, entity.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return document;
    }
}
