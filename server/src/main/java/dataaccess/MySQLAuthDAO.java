package dataaccess;

import datamodel.AuthData;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySQLAuthDAO implements AuthDataAccess {

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
        var statement = "TRUNCATE auth";
        executeUpdate(statement);
    }

    @Override
    public Boolean verifyAuth(String authToken) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT EXISTS (SELECT 1 FROM auth WHERE auth_token = ?")) {
                preparedStatement.setString(1, authToken);
                ResultSet rs = preparedStatement.executeQuery();
                return rs.next() && rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private int executeUpdate(String statement, Object... params) throws DataAccessException {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.length; i++) {
                    Object param = params[i];
                    switch (param) {
                        case String p -> ps.setString(i + 1, p);
                        case Integer p -> ps.setInt(i + 1, p);
                        case null -> ps.setNull(i + 1, NULL);
                        default -> {
                        }
                    }
                }
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

                return 0;
            }
        } catch (SQLException | DataAccessException e) {
            throw new DataAccessException(String.format("unable to update database: %s, %s", statement, e.getMessage()));
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
