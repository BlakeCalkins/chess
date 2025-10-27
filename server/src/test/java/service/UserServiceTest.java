package service;

import dataaccess.*;
import datamodel.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    static UserDataAccess userDAO = new MemoryUserDataAccess();
    static AuthDataAccess authDAO = new MemoryAuthDataAccess();
    static GameDataAccess gameDAO = new MemoryGameDataAccess();
    Service service = new Service(userDAO, authDAO, gameDAO);


    @AfterEach
    void clear() {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
        assertTrue(userDAO.getUsers().isEmpty());
        assertTrue(authDAO.getAuthTokens().isEmpty());
        assertTrue(gameDAO.listGames().isEmpty());
    }

    @Test
    void register() throws Exception {
        var user = new UserData("joe", "j@j.com", "pswd");
        var authData = service.register(user);
        assertNotNull(authData);
        assertEquals(user.username(), authData.username());
        assertFalse(authData.authToken().isEmpty());
    }

    @Test
    void registerInvalidUsername() {
        var user = new UserData(null, "j@j.com", "pswd");
        assertThrows(Exception.class, () -> service.register(user));
    }

    @Test
    void registerDuplicateUsername() throws Exception {
        var user = new UserData("Blake", "blake@cool.com", "pswd");
        var user2 = new UserData("Blake", "blake@yahoo.com", "password");
        service.register(user);
        assertThrows(AlreadyTakenException.class, () -> service.register(user2));
    }

    @Test
    void login() throws Exception {
        var user = new UserData("joe", "j@j.com", "pswd");
        service.register(user);
        var authData = service.login(new UserData("joe", "", "pswd"));
        assertNotNull(authData);
    }

    @Test
    void loginNoUser() throws Exception {
        var user = new UserData("Blake", "blake@cool.com", "pswd");
        service.register(user);
        assertThrows(UnauthorizedException.class, () -> service.login(new UserData("", "", "pswd")));
    }

    @Test
    void loginWrongPassword () throws Exception {
        var user = new UserData("Blake", "blake@yahoo.com", "pswd");
        service.register(user);
        assertThrows(UnauthorizedException.class, () -> service.login(new UserData("Blake", "", "letmein")));
    }
}