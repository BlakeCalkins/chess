package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import datamodel.GameData;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MySQLGameDAO extends MySQLDAO implements GameDataAccess {

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
        var games = new ArrayList<GameData>();
        try (Connection conn = DatabaseManager.getConnection()) {
            var stmt = "SELECT game_id, white_username, black_username, game_name, game FROM games";
            try (var preparedStatement = conn.prepareStatement(stmt)) {
                try (var rs = preparedStatement.executeQuery()) {
                    while (rs.next()) {
                        var gameId = rs.getInt("game_id");
                        var whiteUsername = rs.getString("white_username");
                        var blackUsername = rs.getString("black_username");
                        var gameName = rs.getString("game_name");
                        var json = rs.getString("game");
                        var game = new Gson().fromJson(json, ChessGame.class);

                        games.add(new GameData(gameId, whiteUsername, blackUsername, gameName, game));
                    }
                }
            }
            return games;
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer createGame(GameData gameData) {
        try (Connection conn = DatabaseManager.getConnection()) {
            var stmt = "INSERT INTO games (game_name, game) VALUES(?, ?)";
            try (var preparedStatement = conn.prepareStatement(stmt, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, gameData.gameName());
                var json = new Gson().toJson(new ChessGame());
                preparedStatement.setString(2, json);
                preparedStatement.executeUpdate();
                var resultSet = preparedStatement.getGeneratedKeys();
                var id = 0;
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }

                return id;
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameData getGame(Integer gameID) {
        try (Connection conn = DatabaseManager.getConnection()) {
            var stmt = "SELECT game_id, white_username, black_username, game_name, game FROM games WHERE game_id = ?";
            try (var preparedStatement = conn.prepareStatement(stmt)) {
                preparedStatement.setInt(1, gameID);
                try (var rs = preparedStatement.executeQuery()) {
                    if (rs.next()) {
                        var gameId = rs.getInt("game_id");
                        var whiteUsername = rs.getString("white_username");
                        var blackUsername = rs.getString("black_username");
                        var gameName = rs.getString("game_name");
                        var json = rs.getString("game");
                        var game = new Gson().fromJson(json, ChessGame.class);

                        return new GameData(gameId, whiteUsername, blackUsername, gameName, game);
                    } else {
                        return null;
                    }
                }
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void joinGame(Integer gameID, String username, String playerColor) {
        try (Connection conn = DatabaseManager.getConnection()) {
            String stmt;
            if (playerColor.equals("WHITE")) {
                stmt = "UPDATE games SET white_username = ? WHERE game_id = ?";
            } else {
                stmt = "UPDATE games SET black_username = ? WHERE game_id = ?";
            }
            try (var preparedStatement = conn.prepareStatement(stmt)) {
                preparedStatement.setString(1, username);
                preparedStatement.setInt(2, gameID);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException | DataAccessException e) {
            throw new RuntimeException(e);
        }
}

        private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              `game_id` INT NOT NULL AUTO_INCREMENT,
              `white_username` varchar(256) DEFAULT NULL,
              `black_username` varchar(256) DEFAULT NULL,
              `game_name` varchar(256) NOT NULL UNIQUE,
              `game` longtext NOT NULL,
              PRIMARY KEY (`game_id`)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    @Override
    public String[] getCreateStatements() {
        return createStatements;
    }

}
