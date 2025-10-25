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
        GameData newGame = new GameData(newID, "", "", gameData.gameName(), new ChessGame());
        games.put(gameData.gameID(), newGame);
        return newID;
    }
}
