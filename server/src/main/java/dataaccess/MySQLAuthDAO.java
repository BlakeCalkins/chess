package dataaccess;

import datamodel.AuthData;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MySQLAuthDAO extends MySQLDAO implements AuthDataAccess {

    public MySQLAuthDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public HashMap<String, String> getAuthTokens() {
        try (Connection conn = DatabaseManager.getConnection()){
            try (var preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM auth")) {
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next() && rs.getInt(1) == 0) {
                    return new HashMap<>();
                } else {
                    return new HashMap<>(Map.of("not", "empty"));
                }
            }

        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE auth")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean verifyAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT auth_token FROM auth WHERE auth_token = ?")) {
                preparedStatement.setString(1, authToken);
                ResultSet rs = preparedStatement.executeQuery();
                return rs.next() && rs.getString(1).equals(authToken);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthData getAuth(String authToken) {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT username FROM auth WHERE auth_token = ? LIMIT 1")) {
                preparedStatement.setString(1, authToken);
                ResultSet rs = preparedStatement.executeQuery();
                if (rs.next()) {
                    return new AuthData(rs.getString(1), authToken);
                } else {
                    return null;
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAuth(String authToken) {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM auth WHERE auth_token = ? LIMIT 1")) {
                preparedStatement.setString(1, authToken);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(String authToken, String username) {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO auth (auth_token, username) VALUES(?, ?)")) {
                preparedStatement.setString(1, authToken);
                preparedStatement.setString(2, username);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public String[] getCreateStatements() {
        return createStatements;
    }

}
