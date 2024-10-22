package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.AuthData;
import model.GameData;

public class CreateGameService {
    CreateGameRequest request;
    int currID = 0;

    public CreateGameService(CreateGameRequest request) {
        this.request = request;
    }

    public CreateGameResult createGame() throws DataAccessException {
        AuthData authData = ServiceUtils.getAuth(request.authToken());
        if (authData == null) {
            return new CreateGameResult(null, "Not authorized");
        }
        GameData gameData = createGameData();
        ServiceUtils.createGame(gameData);
        return new CreateGameResult(gameData.gameID(), "");
    }

    public GameData createGameData() {
        currID++;
        return new GameData(currID, "", "", request.gameName(), new ChessGame());
    }
}
