package dataaccess;

import chess.ChessGame;
import datamodel.GameData;

import java.util.HashMap;
import java.util.List;

public class MemoryGameDataAccess implements GameDataAccess {
    private final HashMap<Integer, GameData> games = new HashMap<>();


    @Override
    public void clear() {
        games.clear();
    }

    @Override
    public List<GameData> listGames() {
        return List.of(games.values().toArray(new GameData[0]));
    }

    @Override
    public Integer createGame(GameData gameData) {
        int newID = games.keySet().stream()
                .max(Integer::compareTo)
                .orElse(0) + 1;
        GameData newGame = new GameData(newID, null, null, gameData.gameName(), new ChessGame());
        games.put(newID, newGame);
        return newID;
    }

    @Override
    public GameData getGame(Integer gameID) {
        return games.get(gameID);
    }

    @Override
    public void joinGame(Integer gameID, String username, String playerColor) {
        GameData oldGame = games.get(gameID);
        GameData newGame;
        if (playerColor.equals("WHITE")) {
            newGame = new GameData(gameID, username, oldGame.blackUsername(), oldGame.gameName(), oldGame.game());
        } else {
            newGame = new GameData(gameID, oldGame.whiteUsername(), username, oldGame.gameName(), oldGame.game());
        }
        System.out.println(newGame);
        games.put(gameID, newGame);
    }


}
