package ui;

import datamodel.AuthData;
import datamodel.UserData;
import exception.ResponseException;
import server.Server;
import java.util.regex.*;


public class EndpointParser {
    private static final Server server = new Server();
    private static ServerFacade facade;
    String username;
    String authToken;

    public EndpointParser() {
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(String.format("http://localhost:%d", port));
    }

    public void register(String username, String password, String email) {
        UserData data = new UserData(username, password, email);
        try {
            AuthData auth = facade.register(data);
            username = auth.username();
            authToken = auth.authToken();
            System.out.printf("Successfully registered user %s", username);
            System.out.println();
        } catch (ResponseException e) {
            System.out.println(e.parseMessage(e.getMessage()));
        }
    }

    public void stopServer() {
        server.stop();
    }
}
