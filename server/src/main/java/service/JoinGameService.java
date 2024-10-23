package service;

import dataaccess.DataAccessException;
import model.GameData;

public class JoinGameService {
    JoinGameRequest request;

    public JoinGameService(JoinGameRequest request) {
        this.request = request;
    }

    public JoinGameResult joinGame() throws DataAccessException {
        if (!ServiceUtils.verifyAuth(request.authToken())) {
            return new JoinGameResult("Not authorized.");
        }
        String username = ServiceUtils.getAuth(request.authToken()).username();
        GameData gameData = ServiceUtils.getGame(request.gameID());
        if (gameData == null) {
            return new JoinGameResult("No game with that ID.");
        }
        GameData newData = gameData;
        switch (request.color()) {
            case WHITE -> newData = new GameData(gameData.gameID(), username, gameData.blackUsername(), gameData.gameName(), gameData.game());
            case BLACK -> newData = new GameData(gameData.gameID(), gameData.whiteUsername(), username, gameData.gameName(), gameData.game());
        }
        ServiceUtils.updateGame(newData);
        return new JoinGameResult("");
    }

}
