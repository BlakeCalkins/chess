package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.GameData;

public class CreateGameService {
    CreateGameRequest request;

    public CreateGameService(CreateGameRequest request) {
        this.request = request;
    }

    public CreateGameResult createGame() throws DataAccessException {
        if (!ServiceUtils.verifyAuth(request.authToken())) {
            return new CreateGameResult(null, "Not authorized");
        }
        GameData gameData = createGameData();
        ServiceUtils.createGame(gameData);
        return new CreateGameResult(gameData.gameID(), "");
    }

    public GameData createGameData() throws DataAccessException {
        int id = ServiceUtils.getHighestID();
        id++;
        return new GameData(id, "", "", request.gameName(), new ChessGame());
    }
}
