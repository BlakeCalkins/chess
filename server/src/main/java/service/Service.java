package service;

import dataaccess.AuthDataAccess;
import dataaccess.DataAccessException;
import dataaccess.GameDataAccess;
import dataaccess.UserDataAccess;
import datamodel.*;

import java.util.List;
import java.util.UUID;

public class Service {
    private final UserDataAccess userDataAccess;
    private final AuthDataAccess authDataAccess;
    private final GameDataAccess gameDataAccess;

    public Service(UserDataAccess userDataAccess, AuthDataAccess authDataAccess, GameDataAccess gameDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
        this.gameDataAccess = gameDataAccess;
    }

    public void clear() throws DataAccessException {
        userDataAccess.clear();
        authDataAccess.clear();
        gameDataAccess.clear();
    }

    public AuthData register(UserData user) throws Exception{
        if ((user.email() == null) || (user.password() == null) || (user.username() == null)) {
            throw new BadRequestException("bad request");
        }
        if (userDataAccess.getUser(user.username()) != null) {
            throw new AlreadyTakenException("already exists");
        }
        userDataAccess.createUser(user);
        return new AuthData(user.username(), generateAuthToken(user.username()));
    }

    public AuthData login(UserData user) throws Exception {
        if (user.username() == null || user.password() == null) {
            throw new BadRequestException("bad request");
        }
        if (userDataAccess.getUser(user.username()) == null) {
            throw new UnauthorizedException("no user exists.");
        }
        if (!userDataAccess.validPassword(user)) {
            throw new UnauthorizedException("unauthorized");
        }

        return new AuthData(user.username(), generateAuthToken(user.username()));
    }

    public void logout(String authToken) throws Exception {
        if (!authDataAccess.verifyAuth(authToken)) {
            throw new UnauthorizedException("unauthorized");
        }
        authDataAccess.deleteAuth(authToken);
    }

    public List<GameData> listGames(String authToken) throws UnauthorizedException, DataAccessException {
        if (!authDataAccess.verifyAuth(authToken)) {
            throw new UnauthorizedException("unauthorized");
        }
        return gameDataAccess.listGames();
    }

    public int createGame(GameData gameData, String authToken) throws Exception {
        if (gameData.gameName() == null || gameData.gameName().isEmpty()) {
            throw new BadRequestException("bad request");
        }
        if (!authDataAccess.verifyAuth(authToken)) {
            throw new UnauthorizedException("unauthorized");
        }
        return gameDataAccess.createGame(gameData);
    }

    public void joinGame(String playerColor, Integer gameID, String authToken) throws Exception {
        if (!authDataAccess.verifyAuth(authToken)) {
            throw new UnauthorizedException("unauthorized");
        }
        if (playerColor == null || gameID == null || playerColor.isEmpty()) {
            throw new BadRequestException("bad request.");
        }
        if (!playerColor.equals("WHITE") && !playerColor.equals("BLACK")) {
            throw new BadRequestException("bad request");
        }
        if (gameDataAccess.getGame(gameID) == null) {
            throw new BadRequestException("you must create a game first");
        }
        if ((gameDataAccess.getGame(gameID).whiteUsername() != null && playerColor.equals("WHITE")) ||
                (gameDataAccess.getGame(gameID).blackUsername() != null && playerColor.equals("BLACK"))) {
            throw new AlreadyTakenException("already taken.");
        }
        String username = authDataAccess.getAuth(authToken).username();
        gameDataAccess.joinGame(gameID, username, playerColor);
    }

    public String generateAuthToken(String username) {
        String authToken = UUID.randomUUID().toString();
        this.authDataAccess.add(authToken, username);
        return authToken;
    }
}
