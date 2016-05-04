package by.bsu.credit.database;

import java.sql.*;
import java.util.Properties;

/**
 * Created by User on 02.05.2016.
 */
public class WrapperConnection {
    //private static Logger log = LogManager.getLogger(WrapperConnection.class);
    private Connection connection;

    WrapperConnection(Properties prop, String url) {
        try {
            connection = DriverManager.getConnection(url, prop);
        } catch (SQLException e) {
            // todo log.error("Connection not obtained.", e);
        }
    }

    public void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                // todo log.error("Statement is null.", e);
            }
        }
    }

    void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                // todo log.error("Wrong connection.", e);
            }
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        if (connection != null) {
            PreparedStatement statement = connection.prepareStatement(sql);
            if (statement != null) {
                return statement;
            }
        }
        throw new SQLException("Connection or statement is null.");
    }
}
