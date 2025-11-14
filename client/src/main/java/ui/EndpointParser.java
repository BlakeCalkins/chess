package ui;

import chess.ChessGame;
import com.google.gson.Gson;
import datamodel.AuthData;
import datamodel.GameData;
import datamodel.UserData;
import exception.ResponseException;
import server.Server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class EndpointParser {
    private static final Server server = new Server();
    private static ServerFacade facade;
    Map<String, String> usersAndAuths = new HashMap<>();
    Map<Integer, GameData> currentGames = new HashMap<>();

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

    public void listGames(String username) {
        String authToken = getAuth(username);
        obtainGames(authToken);
        List<String> gameLines = new ArrayList<>();
        for (GameData game : currentGames.values()) {
            String whitePlayer = (game.whiteUsername() == null) ? "None" : game.whiteUsername();
            String blackPlayer = (game.blackUsername() == null) ? "None" : game.blackUsername();
            String gameLine = String.format("%d. %s, White player: %s, Black Player: %s", game.gameID(), game.gameName(), whitePlayer, blackPlayer);
            gameLines.add(gameLine);
        }
        for (String line : gameLines) {
            System.out.println(line);
        }
    }

    public void joinGame(String playerColor, Integer gameID, String username) {
        String authToken = getAuth(username);
        ChessGame.TeamColor teamColor;
        try {
            if (playerColor.equalsIgnoreCase("WHITE")) {
                teamColor = ChessGame.TeamColor.WHITE;
            } else if (playerColor.equalsIgnoreCase("BLACK")) {
                teamColor = ChessGame.TeamColor.BLACK;
            } else {
                throw new ResponseException(ResponseException.Code.BadRequest, "Not a valid color.");
            }
            try {
                facade.joinGame(teamColor, gameID, authToken);
                obtainGames(authToken);
                ChessGame game = currentGames.get(gameID).game();
                ChessBoardDrawer drawer = new ChessBoardDrawer(game.getBoard(), teamColor);
                drawer.draw();
            } catch (ResponseException e) {
                System.out.println(e.parseMessage(e.getMessage()));
            }
        } catch (ResponseException e) {
            System.out.println(e.getMessage());
        }
    }

    private void obtainGames(String authToken) {
        try {
            Map<String, Object> root = facade.listGames(authToken);
            List<Map<String, Object>> games = (List<Map<String, Object>>) root.get("games");
            currentGames.clear();
            for (Map<String, Object> gameEntry : games) {
                GameData game = parseGame(gameEntry);
                currentGames.put(game.gameID(), game);
            }
        } catch (ResponseException e) {
            System.out.println(e.parseMessage(e.getMessage()));
        }
    }

    private GameData parseGame(Map<String, Object> gameEntry) {
        Gson gson = new Gson();
        String json = gson.toJson(gameEntry);
        return gson.fromJson(json, GameData.class);
    }

    private String getAuth(String username) {
        return usersAndAuths.get(username);
    }

    public void stopServer() {
        server.stop();
    }
}
