package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import datamodel.GameData;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MySQLGameDAO implements GameDataAccess {

    public MySQLGameDAO() throws DataAccessException {
        configureDatabase();
    }

    @Override
    public void clear() {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE games")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GameData> listGames() {
        return List.of();
    }

    @Override
    public Integer createGame(GameData gameData) {
        try (Connection conn = DatabaseManager.getConnection()) {
            var stmt = "INSERT INTO games (game_name, game) VALUES(?, ?)";
            try (var preparedStatement = conn.prepareStatement(stmt)) {
                preparedStatement.setString(1, gameData.gameName());
                var json = new Gson().toJson(new ChessGame());
                preparedStatement.setString(2, json);
                preparedStatement.executeUpdate();
                var resultSet = preparedStatement.getGeneratedKeys();
                var ID = 0;
                if (resultSet.next()) {
                    ID = resultSet.getInt(1);
                }

                return ID;
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
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
            CREATE TABLE IF NOT EXISTS  games (
              `game_id` INT NOT NULL AUTO_INCREMENT,
              `white_username` varchar(256) DEFAULT NULL,
              `black_username` varchar(256) DEFAULT NULL,
              `game_name` varchar(256) NOT NULL,
              `game` longtext NOT NULL,
              PRIMARY KEY (`game_id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

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
