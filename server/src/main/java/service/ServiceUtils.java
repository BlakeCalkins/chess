package service;

import dataaccess.*;
import model.AuthData;
import model.GameData;
import model.UserData;
import java.util.UUID;

public final class ServiceUtils {

    private static final AuthDAO authDAO = new MemoryAuthDAO();
    private static final GameDAO gameDAO = new MemoryGameDAO();
    private static final UserDAO userDAO = new MemoryUserDAO();

    private ServiceUtils() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static UserData getUser(String username) throws DataAccessException {
        return userDAO.getUser(username);
    }

    public static void createUser(UserData data) throws DataAccessException {
        userDAO.createUser(data);
    }

    public static void createAuth(AuthData data) throws DataAccessException {
        authDAO.createAuth(data);
    }

    public static AuthData getAuth(String authToken) throws DataAccessException {
        return authDAO.getAuth(authToken);
    }

    public static void deleteAuth(String authToken) throws DataAccessException {
        authDAO.deleteAuth(authToken);
    }

    public static void createGame(GameData data) throws DataAccessException {
        gameDAO.createGame(data);
    }

    public static GameData getGame(int gameID) throws DataAccessException {
        return gameDAO.getGame(gameID);
    }

    public static void updateGame(GameData data) throws DataAccessException {
        gameDAO.updateGame(data);
    }

    public static int getHighestID() throws DataAccessException {
        return gameDAO.getHighestID();
    }

    public static Boolean verifyAuth(String authToken) throws DataAccessException {
        AuthData authData = ServiceUtils.getAuth(authToken);
        if (authData == null) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}

