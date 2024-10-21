package dataaccess;

import model.GameData;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class MemoryGameDAO implements GameDAO {

    final private HashMap<Integer, GameData> games = new HashMap<>();

    @Override
    public void clearGames() throws DataAccessException {
        games.clear();
    }

    @Override
    public void createGame(GameData data) throws DataAccessException {
        games.put(data.gameID(), data);
    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return games.get(gameID);
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return games.values();
    }

    @Override
    public void updateGame(GameData data) throws DataAccessException {
        games.put(data.gameID(), data);
    }
}
