package dataaccess;

import model.GameData;

import java.util.Collection;

public interface GameDAO {
    void clearGames() throws DataAccessException;

    void createGame(GameData data) throws DataAccessException;

    GameData getGame(int gameID) throws DataAccessException;

    Collection<GameData> listGames() throws DataAccessException;

    void updateGame(GameData data) throws DataAccessException;

    int getHighestID() throws DataAccessException;
}