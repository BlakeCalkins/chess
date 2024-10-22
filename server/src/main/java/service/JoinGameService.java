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
        GameData gameData = ServiceUtils.getGame(request.gameID());
        ServiceUtils.updateGame(gameData);
        return new JoinGameResult("");
    }

}
