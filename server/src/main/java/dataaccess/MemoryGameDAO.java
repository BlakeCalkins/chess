package dataaccess;

import model.GameData;

import java.util.ArrayList;
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
    public List<GameData> listGames() throws DataAccessException {
        return new ArrayList<>(games.values());
    }

    @Override
    public void updateGame(GameData data) throws DataAccessException {
        games.put(data.gameID(), data);
    }

    public int getHighestID() throws DataAccessException {
        int maxKey = 0;
        for (Integer key : games.keySet()) {
            if (key > maxKey) {
                maxKey = key;  // Update maxKey if the current key is larger
            }
        }
        return maxKey;
    }
}
