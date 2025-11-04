package service;

import chess.ChessGame;
import dataaccess.*;
import datamodel.GameData;
import datamodel.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    static UserDataAccess userDAO = new MemoryUserDataAccess();
    static AuthDataAccess authDAO = new MemoryAuthDataAccess();
    static GameDataAccess gameDAO = new MemoryGameDataAccess();
    Service service = new Service(userDAO, authDAO, gameDAO);
    UserData user = new UserData("Blake", "blake@yahoo.com", "pswd");


//    static Stream<Arguments> daoProvider() {
//        return Stream.of(
//                Arguments.of(new MemoryUserDataAccess(), new MemoryAuthDataAccess(), new MemoryGameDataAccess()),
//                Arguments.of(new MySQLUserDAO(), new MySQLAuthDAO(), new MySQLGameDAO())
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("daoProvider")
//    void testServiceBehavior(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) {
//        Service service = new Service(userDAO, authDAO, gameDAO);
//        // test logic here
//    }
//
//    @ParameterizedTest
//    @MethodSource("authDAOProvider")
//    void testAuthBehavior(AuthDataAccess authDAO) {
//        Service service = new Service(userDAO, authDAO, gameDAO);
//        // test logic here
//    }
//
//    static Stream<AuthDataAccess> authDAOProvider() {
//        return Stream.of(
//                new MemoryAuthDataAccess(),
//                new MySQLAuthDAO()
//        );
//    }

    @AfterEach
    void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
        assertTrue(userDAO.getUsers().isEmpty());
        assertTrue(authDAO.getAuthTokens().isEmpty());
        assertTrue(gameDAO.listGames().isEmpty());
    }

    @Test
    void register() throws Exception {
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
        var user2 = new UserData("Blake", "blake@yahoo.com", "password");
        service.register(user);
        assertThrows(AlreadyTakenException.class, () -> service.register(user2));
    }

    @Test
    void login() throws Exception {
        service.register(user);
        var authData = service.login(new UserData("Blake", "", "pswd"));
        assertNotNull(authData);
    }

    @Test
    void loginNoUser() throws Exception {
        service.register(user);
        assertThrows(UnauthorizedException.class, () -> service.login(new UserData("", "", "pswd")));
    }

    @Test
    void loginWrongPassword () throws Exception {
        service.register(user);
        assertThrows(UnauthorizedException.class, () -> service.login(new UserData("Blake", "", "letmein")));
    }

    @Test
    void logout() throws Exception {
        var authData = service.register(user);
        service.logout(authData.authToken());
        assertFalse(authDAO.verifyAuth(authData.authToken()));
    }

    @Test
    void logoutBadAuth() throws Exception {
        service.register(user);
        assertThrows(UnauthorizedException.class, () -> service.logout("secretAuth69"));
    }

    @Test
    void createGame() throws Exception {
        var authData = service.register(user);
        assertEquals(1, service.createGame(new GameData(0, "", "", "myGame", new ChessGame()), authData.authToken()));
    }

    @Test
    void createNoName() throws Exception {
        var authData = service.register(user);
        var gameData = new GameData(0, "", "", "", new ChessGame());
        assertThrows(BadRequestException.class, () -> service.createGame(gameData, authData.authToken()));
    }

    @Test
    void listGames() throws Exception {
        var authData = service.register(user);
        service.createGame(new GameData(0, "", "", "myGame", new ChessGame()), authData.authToken());
        assertFalse(service.listGames(authData.authToken()).isEmpty());
    }

    @Test
    void listGamesBadAuth() throws Exception {
        service.register(user);
        assertThrows(UnauthorizedException.class, () -> service.listGames("secretAuth69"));
    }

    @Test
    void joinGame() throws Exception {
        var authData = service.register(user);
        service.createGame(new GameData(0, "", "", "myGame", new ChessGame()), authData.authToken());
        service.joinGame("WHITE", 1, authData.authToken());
        assertEquals("Blake", gameDAO.getGame(1).whiteUsername());
    }

    @Test
    void joinWithTakenColor() throws Exception {
        var authData = service.register(user);
        service.createGame(new GameData(0, "", "", "myGame", new ChessGame()), authData.authToken());
        service.joinGame("WHITE", 1, authData.authToken());
        assertThrows(AlreadyTakenException.class, () -> service.joinGame("WHITE", 1, authData.authToken()));
    }

}