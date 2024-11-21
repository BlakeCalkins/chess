package service;

import dataaccess.DataAccessException;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ListGamesTest {
    public static CreateGameService game1;
    public static CreateGameService game2;
    public static CreateGameService game3;
    public static RegisterService registerService = new RegisterService(new RegisterRequest("Blake", "pswd", "email"));
    static String authToken;

    static {
        try {
            authToken = registerService.registerUser().authToken();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static ListGamesService listGamesService;

    @BeforeAll
    public static void setUp() throws DataAccessException {
        game1 = new CreateGameService(new CreateGameRequest(authToken, "game1"));
        game2 = new CreateGameService(new CreateGameRequest(authToken, "game2"));
        game3 = new CreateGameService(new CreateGameRequest(authToken, "game3"));
        game1.createGame();
        game2.createGame();
        game3.createGame();
    }

    @Test
    public void testListSuccess() throws DataAccessException {
        listGamesService = new ListGamesService(new ListGamesRequest(authToken));
        List<GameData> games = listGamesService.listGames().games();
        Assertions.assertEquals(3, games.size());
    }
}