package dataaccess;

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
}
