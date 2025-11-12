import datamodel.AuthData;
import datamodel.UserData;
import exception.ResponseException;
import org.junit.jupiter.api.*;
import server.Server;
import static org.junit.jupiter.api.Assertions.*;



public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;
    private static final UserData userData = new UserData("blake", "blake@yahoo.com", "myPswd");


    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(String.format("http://localhost:%d", port));
    }

    @AfterEach
    void clearDb() throws ResponseException {
        facade.clear();
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    void clear() throws ResponseException {
        facade.clear();
//        assertNull(facade.listGames());
    }

    @Test
    public void register() throws ResponseException {
        AuthData data = facade.register(userData);
        assertNotNull(data.username());
        assertEquals(36, data.authToken().length());
    }

    @Test
    public void registerAlreadyTaken() throws ResponseException {
        facade.register(userData);
        ResponseException ex = assertThrows(ResponseException.class, () -> facade.register(userData));
        assertEquals(ResponseException.Code.Forbidden, ex.code());
    }

    @Test
    public void login() throws ResponseException {
        facade.register(userData);
        AuthData data = facade.login(userData);
        assertNotNull(data.username());
        assertEquals(36, data.authToken().length());
    }

    @Test
    public void loginNoUser() {
        ResponseException ex = assertThrows(ResponseException.class, () -> facade.login(userData));
        assertEquals(ResponseException.Code.Unauthorized, ex.code());
    }

    @Test
    public void logout() throws ResponseException {
        facade.register(userData);
        AuthData data = facade.login(userData);
        assertDoesNotThrow(() -> facade.logout(data.authToken()));
    }

    @Test
    public void logoutBadAuth() throws ResponseException {
        facade.register(userData);
        facade.login(userData);
        ResponseException ex = assertThrows(ResponseException.class, () -> facade.logout(" badAuth"));
        assertEquals(ResponseException.Code.Unauthorized, ex.code());
    }

}
