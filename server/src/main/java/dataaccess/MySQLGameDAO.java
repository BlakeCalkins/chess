package dataaccess;

import datamodel.GameData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MySQLGameDAO implements GameDataAccess {
    @Override
    public void clear() {

    }

    @Override
    public List<GameData> listGames() {
        return List.of();
    }

    @Override
    public Integer createGame(GameData gameData) {
        return 0;
    }

    @Override
    public GameData getGame(Integer gameID) {
        return null;
    }

    @Override
    public void joinGame(Integer gameID, String username, String playerColor) {

    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auth (
              `gameID` INT NOT NULL AUTO_INCREMENT,
              `white_username` varchar(256) DEFAULT NULL,
              `black_username` varchar(256) DEFAULT NULL,
              `game_name` varchar(256) NOT NULL,
              `game` longtext NOT NULL,
              PRIMARY KEY (`gameID`),
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
