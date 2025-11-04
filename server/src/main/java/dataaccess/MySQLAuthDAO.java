package dataaccess;

import datamodel.AuthData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

public class MySQLAuthDAO implements AuthDataAccess {
    @Override
    public HashMap<String, String> getAuthTokens() {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public Boolean verifyAuth(String authToken) {
        return null;
    }

    @Override
    public AuthData getAuth(String authToken) {
        return null;
    }

    @Override
    public void deleteAuth(String authToken) {

    }

    @Override
    public void add(String username, String authToken) {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `auth_token` CHAR(36) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`auth_token`),
              INDEX(username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    // not sure if works.
    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
