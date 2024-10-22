package service;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CreateGameTest {

    @Test
    public void testCreateGame() throws DataAccessException {
        RegisterService registerService = new RegisterService(new RegisterRequest("Blake", "psswrd", "blake@fake.com"));
        String authToken = registerService.registerUser().authToken();
        CreateGameService gameService = new CreateGameService(new CreateGameRequest(authToken, "firstGame"));
        CreateGameResult result = gameService.createGame();
        Assertions.assertEquals(1, result.gameID());
    }
}
