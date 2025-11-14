package ui;

import datamodel.AuthData;
import datamodel.UserData;
import exception.ResponseException;
import server.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;


public class EndpointParser {
    private static final Server server = new Server();
    private static ServerFacade facade;
    Map<String, String> usersAndAuths = new HashMap<>();

    public EndpointParser() {
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(String.format("http://localhost:%d", port));
    }

    public void register(String username, String password, String email) {
        UserData data = new UserData(username, email, password);
        try {
            AuthData auth = facade.register(data);
            usersAndAuths.put(auth.username(), auth.authToken());
            System.out.printf("Successfully registered user %s", username);
            System.out.println();
        } catch (ResponseException e) {
            System.out.println(e.parseMessage(e.getMessage()));
        }
    }

    public boolean login(String username, String password) {
        UserData data = new UserData(username, null, password);
        try {
            AuthData auth = facade.login(data);
            usersAndAuths.put(auth.username(), auth.authToken());
            return true;
        } catch (ResponseException e) {
            System.out.println(e.parseMessage(e.getMessage()));
            return false;
        }
    }

    public void createGame(String gameName, String username) {
        String authToken = getAuth(username);
        try {
            facade.createGame(gameName, authToken);
        } catch (ResponseException e) {
            System.out.println(e.parseMessage(e.getMessage()));
        }
    }

    private String getAuth(String username) {
        return usersAndAuths.get(username);
    }

    public void stopServer() {
        server.stop();
    }
}
