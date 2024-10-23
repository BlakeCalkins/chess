package service;

import chess.ChessGame;
import dataaccess.DataAccessException;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class JoinGameTest {
    public static JoinGameService joinService;
    public static CreateGameService gameService;
    public static RegisterService registerService;

    @BeforeAll
    public static void setUp() {
        registerService = new RegisterService(new RegisterRequest("Blake", "pswd", "email"));
    }



    @Test
    public void testWhiteUser() throws DataAccessException {
        String authToken = registerService.registerUser().authToken();
        gameService = new CreateGameService(new CreateGameRequest(authToken, "funGame"));
        gameService.createGame();
        joinService = new JoinGameService(new JoinGameRequest(ChessGame.TeamColor.WHITE, 1, authToken));
        joinService.joinGame();
        GameData data = ServiceUtils.getGame(1);
        Assertions.assertEquals(data.whiteUsername(), "Blake");
    }

    @Test
    public void testBlackUser() throws DataAccessException {
        String authToken = registerService.registerUser().authToken();
        gameService = new CreateGameService(new CreateGameRequest(authToken, "funGame"));
        gameService.createGame();
        joinService = new JoinGameService(new JoinGameRequest(ChessGame.TeamColor.BLACK, 1, authToken));
        joinService.joinGame();
        GameData data = ServiceUtils.getGame(1);
        Assertions.assertEquals(data.blackUsername(), "Blake");
    }

    @Test
    public void testBadId() throws DataAccessException {
        String authToken = registerService.registerUser().authToken();
        gameService = new CreateGameService(new CreateGameRequest(authToken, "funGame"));
        gameService.createGame();
        joinService = new JoinGameService(new JoinGameRequest(ChessGame.TeamColor.BLACK, 6, authToken));
        joinService.joinGame();
        GameData data = ServiceUtils.getGame(1);
        Assertions.assertNotEquals(data.blackUsername(), "Blake");
    }
}
