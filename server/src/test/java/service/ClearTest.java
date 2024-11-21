package service;

import dataaccess.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClearTest {
    public static RegisterService registerService;
    public static CreateGameService createGameService;
    public static ClearService clearService;
    public static ListGamesService listGamesService;

    @Test
    public void testClearSuccess() throws DataAccessException {
        registerService = new RegisterService(new RegisterRequest("Blake", "pswd", "email"));
        String authToken = registerService.registerUser().authToken();
        createGameService = new CreateGameService(new CreateGameRequest(authToken, "game"));
        listGamesService = new ListGamesService(new ListGamesRequest(authToken));
        clearService = new ClearService();
        clearService.clear();
        Assertions.assertNull(listGamesService.listGames().games());
        Assertions.assertNull(ServiceUtils.getAuth(authToken));
        Assertions.assertNull(ServiceUtils.getUser("Blake"));

    }
}
