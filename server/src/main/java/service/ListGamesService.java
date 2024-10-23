package service;

import dataaccess.DataAccessException;
import model.GameData;

import java.util.List;

public class ListGamesService {
    ListGamesRequest request;

    public ListGamesService(ListGamesRequest request) {
        this.request = request;
    }

    public ListGamesResult listGames() throws DataAccessException {
        if (!ServiceUtils.verifyAuth(request.authToken())) {
            return new ListGamesResult(null, "Not Authorized");
        }
        List<GameData> allGames = ServiceUtils.listGames();
        return new ListGamesResult(allGames, "");
    }
}
