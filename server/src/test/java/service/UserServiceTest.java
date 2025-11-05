package service;

import chess.ChessGame;
import dataaccess.*;
import datamodel.GameData;
import datamodel.UserData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserDataAccess userDAO;
    private AuthDataAccess authDAO;
    private GameDataAccess gameDAO;
    UserData user = new UserData("Blake", "blake@yahoo.com", "pswd");


    static Stream<Arguments> daoProvider() throws DataAccessException {
        return Stream.of(
                Arguments.of(new MemoryUserDataAccess(), new MemoryAuthDataAccess(), new MemoryGameDataAccess()),
                Arguments.of(new MySQLUserDAO(), new MySQLAuthDAO(), new MySQLGameDAO())
        );
    }

    @AfterEach
    void clear() throws DataAccessException {
        userDAO.clear();
        authDAO.clear();
        gameDAO.clear();
        assertTrue(userDAO.getUsers().isEmpty());
        assertTrue(authDAO.getAuthTokens().isEmpty());
        assertTrue(gameDAO.listGames().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void register(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        var authData = service.register(user);
        assertNotNull(authData);
        assertEquals(user.username(), authData.username());
        assertFalse(authData.authToken().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void registerInvalidUsername(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        var user = new UserData(null, "j@j.com", "pswd");
        assertThrows(Exception.class, () -> service.register(user));
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void registerDuplicateUsername(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        var user2 = new UserData("Blake", "blake@yahoo.com", "password");
        service.register(user);
        assertThrows(AlreadyTakenException.class, () -> service.register(user2));
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void login(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        service.register(user);
        var authData = service.login(new UserData("Blake", "", "pswd"));
        assertNotNull(authData);
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void loginNoUser(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        service.register(user);
        assertThrows(UnauthorizedException.class, () -> service.login(new UserData("", "", "pswd")));
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void loginWrongPassword (UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        service.register(user);
        assertThrows(UnauthorizedException.class, () -> service.login(new UserData("Blake", "", "letmein")));
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void logout(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        var authData = service.register(user);
        service.logout(authData.authToken());
        assertFalse(authDAO.verifyAuth(authData.authToken()));
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void logoutBadAuth(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        service.register(user);
        assertThrows(UnauthorizedException.class, () -> service.logout("secretAuth69"));
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void createGame(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        var authData = service.register(user);
        assertEquals(1, service.createGame(new GameData(0, "", "", "myGame", new ChessGame()), authData.authToken()));
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void createNoName(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        var authData = service.register(user);
        var gameData = new GameData(0, "", "", "", new ChessGame());
        assertThrows(BadRequestException.class, () -> service.createGame(gameData, authData.authToken()));
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void listGames(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        var authData = service.register(user);
        service.createGame(new GameData(0, "", "", "myGame", new ChessGame()), authData.authToken());
        assertFalse(service.listGames(authData.authToken()).isEmpty());
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void listGamesBadAuth(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        service.register(user);
        assertThrows(UnauthorizedException.class, () -> service.listGames("secretAuth69"));
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void joinGame(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        var authData = service.register(user);
        service.createGame(new GameData(0, "", "", "myGame", new ChessGame()), authData.authToken());
        service.joinGame("WHITE", 1, authData.authToken());
        assertEquals("Blake", gameDAO.getGame(1).whiteUsername());
    }

    @ParameterizedTest
    @MethodSource("daoProvider")
    void joinWithTakenColor(UserDataAccess userDAO, AuthDataAccess authDAO, GameDataAccess gameDAO) throws Exception {
        this.userDAO = userDAO;
        this.authDAO = authDAO;
        this.gameDAO = gameDAO;
        Service service = new Service(userDAO, authDAO, gameDAO);
        var authData = service.register(user);
        service.createGame(new GameData(0, "", "", "myGame", new ChessGame()), authData.authToken());
        service.joinGame("WHITE", 1, authData.authToken());
        assertThrows(AlreadyTakenException.class, () -> service.joinGame("WHITE", 1, authData.authToken()));
    }

}