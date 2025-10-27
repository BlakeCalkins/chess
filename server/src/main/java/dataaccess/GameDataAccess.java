package dataaccess;

import datamodel.GameData;

import java.util.List;

public interface GameDataAccess {
    void clear();
    List<GameData> listGames();
    Integer createGame(GameData gameData);
    GameData getGame(Integer gameID);
    void joinGame(Integer gameID, String username, String playerColor);
}
