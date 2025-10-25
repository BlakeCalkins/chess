package dataaccess;

import datamodel.GameData;

import java.util.List;

public interface GameDataAccess {
    void clear();
    List<GameData> listGames();
}
